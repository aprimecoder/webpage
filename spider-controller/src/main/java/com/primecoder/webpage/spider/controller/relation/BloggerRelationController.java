package com.primecoder.webpage.spider.controller.relation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.primecoder.webpage.spider.core.dao.BloggerDao;
import com.primecoder.webpage.spider.core.dao.RelationDao;
import com.primecoder.webpage.spider.core.download.HttpClientPost;
import com.primecoder.webpage.spider.core.service.BloggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerRelationController {


    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerRelationController.class);


    private static BloggerRelationController instance = null;


    private static final HttpClientPost HTTP_CLIENT_POST = HttpClientPost.getInstance();

    private static final BloggerService BLOGGER_SERVICE = BloggerService.getInstance();

    private static final RelationDao RELATION_DAO = RelationDao.getInstance();

    public static final BloggerDao BLOGGER_DAO = BloggerDao.getInstance();


    private BloggerRelationController() {

    }

    public static synchronized BloggerRelationController getInstance() {

        if (null == instance) {
            instance = new BloggerRelationController();
        }

        return instance;
    }

    public String start(String bloggerId) throws InterruptedException {

        getRelationById(bloggerId);

        return bloggerId;
    }

    private void getRelationById(String bloggerId) {

        getFollowee(bloggerId);

        getFollower(bloggerId);

        BLOGGER_DAO.bloggerHandled(bloggerId);

        LOGGER.info("handled bloggerId : {}",bloggerId);
    }

    private void updateRelation(String followeeId,String followerId) {

        boolean followerRelationExist = RELATION_DAO.exist(followeeId,followerId);
        if (!followerRelationExist) {

            boolean followeeRelationExist = RELATION_DAO.exist(followerId,followeeId);
            if (followeeRelationExist) {
                RELATION_DAO.update(followerId,followeeId,true);
            } else {

                RELATION_DAO.insert(followeeId,followerId,false);
            }
        }
    }

    private void getFollower(String bloggerId) {

        List<String> followers = new ArrayList<>();

        getFollowList(bloggerId,followers,false);

        for (String followName : followers) {
            String followerId = BLOGGER_SERVICE.getBloggerIdByName(followName);

            if (followerId == null) {

                return;
            }

            updateRelation(bloggerId,followerId);
        }

        LOGGER.info("bloggerId : {} get follower success!",bloggerId);
    }

    private void getFollowee(String bloggerId) {

        List<String> followes = new ArrayList<>();

        getFollowList(bloggerId,followes,true);

        for (String followName : followes) {
            String followeeId = BLOGGER_SERVICE.getBloggerIdByName(followName);

            if (null == followeeId) {
                continue;
            }

            updateRelation(followeeId,bloggerId);
        }

        LOGGER.info("bloggerId : {} get followee success!",bloggerId);
    }

    private void getFollowList(String bloggerId,List<String> follows,boolean isFollowes) {

        JSONObject pageOneObj = post(bloggerId,1,isFollowes);
        getRelationByObj(pageOneObj,follows);

        String pageInfo = pageOneObj.getString("Pager");

        int pageMax = parserPageInfo(pageInfo);
        if (pageMax > 1) {
            for (int i = 2;i < pageMax;i++) {

                getRelationByObj(post(bloggerId,i,isFollowes),follows);
            }
        }
    }

    private JSONObject post(String bloggerId,int page,boolean isFollowes) {

        JSONObject obj = new JSONObject();
        obj.put("uid",bloggerId);
        obj.put("groupId","00000000-0000-0000-0000-000000000000");
        obj.put("page",page);
        obj.put("isFollowes",isFollowes);

        JSONObject pageOneObj = HTTP_CLIENT_POST.postBloggerRelation(obj);

        LOGGER.info("post bloggerId : {},page : {} success!",bloggerId,page);

        return pageOneObj;
    }


    private void getRelationByObj(JSONObject pageObj,List<String> followes) {

        JSONArray userArr = pageObj.getJSONArray("Users");
        for (int i=0;i<userArr.size();i++) {
            JSONObject user = userArr.getJSONObject(i);
            String bloggerName = user.getString("Alias");
            followes.add(bloggerName);
        }
    }

    private int parserPageInfo(String pageInfo) {

        if ("".equals(pageInfo)) {

            return -1;
        }

        int ellipsis = pageInfo.indexOf("···");
        if (ellipsis > 0) {

            String temp = pageInfo.substring(pageInfo.indexOf("···"));
            int tempIndex = temp.indexOf(">");
            String str = temp.substring(tempIndex);
            int index = str.indexOf("<");
            String result = str.substring(1,index);

            return Integer.valueOf(result);
        } else {

            int hrefIndex = pageInfo.lastIndexOf("href");
            String temp = pageInfo.substring(0,hrefIndex - 7);
            int largeIndex = temp.lastIndexOf(">");
            String result = pageInfo.substring(largeIndex + 1,hrefIndex - 7);

            return Integer.valueOf(result);
        }




    }
}
