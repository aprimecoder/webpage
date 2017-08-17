package com.primecoder.webpage.spider.core.util;

/**
 * Created by primecoder on 2017/8/1.
 * E-mail : aprimecoder@gmail.com
 */
public class CookieMgr {

    private static CookieMgr instance = null;

    private CookieMgr() {

    }

    public static synchronized CookieMgr getInstance() {

        if (null == instance) {
            instance = new CookieMgr();
        }

        return instance;
    }

    public String getCookie() {

        String cookie = "__gads=ID=8b7984e53b82151f:T=1484577342:S=ALNI_MbIReNd92JuLvC-A54Zdhs3M39y5g; CNZZDATA5882415=cnzz_eid%3D1782159015-1488108553-null%26ntime%3D1488108553; a5993_times=1; CNZZDATA3793314=cnzz_eid%3D1902268352-1488683460-http%253A%252F%252Fzzk.cnblogs.com%252F%26ntime%3D1488685886; UM_distinctid=15b2e6e9b4b3fd-07c99bf36915e4-5b123112-1fa400-15b2e6e9b4c3c7; bdshare_firstime=1491132655334; CNZZDATA1260937055=1207466641-1484577341-%7C1492783730; pgv_pvi=7930592256; CNZZDATA4819750=cnzz_eid%3D1846374137-1498967355-null%26ntime%3D1498967355; CNZZDATA1121896=cnzz_eid%3D1122819493-1499342243-http%253A%252F%252Fwww.cnblogs.com%252F%26ntime%3D1499342243; CNZZDATA943648=cnzz_eid%3D232478988-1499345165-http%253A%252F%252Fwww.cnblogs.com%252F%26ntime%3D1499345165; AJSTAT_ok_times=4; CNZZDATA286258=cnzz_eid%3D418701352-1499956454-http%253A%252F%252Fwww.cnblogs.com%252F%26ntime%3D1499956454; __utma=226521935.1600030032.1488602042.1499899554.1500976606.3; __utmz=226521935.1500976606.3.2.utmcsr=google|utmccn=(organic)|utmcmd=organic|utmctr=(not%20provided); wP_h=f546ae4bc6d1fa89b4e52a31e7861d2faf6acaae; .CNBlogsCookie=24FE0EF50A468864FD810FD46B5A7A745420C8406AF4586A7A5AF7EEE7EEE04BE90A928261CEB6AA6CA3D6C88115FCFFEBEEE50F725F313F2A01447D6ECCD2B82A63E1E84EF696B6C14D13531C4A42CB8C0D0ED8; .Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDmkOOkvZsG17y9kOZJ-LZEDJxK8zQcx3p8zjATlE4Cae9ETBla2UdsTvLzvLkKwJnswL3LHctk3YUshwNjkSk6oPFexVfq6QdBZuHunw4FuVLJD_krH7AZBowF1ka6iuVTeRpmNAy92_yhJWpf_j8E6S4M3M36Nmc163S5iGW-_lNHYohnHtwoSmQzQBk02S5qRRA01nBZTzEbI7UK4kINuaOepI6jI8PShQUpMsMfQOrpi9DkHb9tj6kvAF7Mk_PiNjLXDt1X79U8YNF6-nZ2YSTNbH7m8EZSzofCEQplHWA; _gat=1; _ga=GA1.2.1600030032.1488602042; _gid=GA1.2.2061344101.1501597205";

        return cookie;
    }
}