package com.primecoder.webpage.spider.core.download;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UTFDataFormatException;

/**
 * Created by primecoder on 2017/5/6.
 * E-mail : aprimecoder@gmail.com
 */
public class HttpClientDownload {


    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientDownload.class);

    public String download(String url) {

        HttpClient httpClient = new DefaultHttpClient();

        if (url == null) {
            return null;
        }

        HttpGet httpGet = new HttpGet(url);

        httpGet.setHeader("Cookie","__gads=ID=8b7984e53b82151f:T=1484577342:S=ALNI_MbIReNd92JuLvC-A54Zdhs3M39y5g; UM_distinctid=15b2e6e9b4b3fd-07c99bf36915e4-5b123112-1fa400-15b2e6e9b4c3c7; pgv_pvi=7930592256; _gat=1; CONTAINERID=1c1901578b3d862a78d2fb7f9df73f2d6ae1786b2dc9af1c756743e95753ca1e; .CNBlogsCookie=22F4D3859331E8203B446CF77AFC0DD06FB82A3901B3FFE2F3BB2CF8E2C40988CABC1EC3E30938FF80716287DBD2BA29C6BA7658D248C39A9F5AA04DA9394BE877D418E5F7975B354B13CCED0E4CEBD310CD796B; .Cnblogs.AspNetCore.Cookies=CfDJ8PhlBN8IFxtHhqIV3s0LCDl7xoqaDg-FjwhfYZb2NTvmwGEuoAExr_gF3jiZBxGZ4qmgdrpxL3ERC_shHIIxsVGuy13tC-9jsCKZjsho_KSPn8rZbqMDhHfYmToeJBdRiECqM1Sc6nfX2dW9n9R9Mb1LafVtmaIXZv9RlCuUFJuc2m_5-2D3gyIo1pDuHhcE4yx-btXRwQkO3F6BSssC9fkFCY7ktCLboQAof2pnqh4cfLPEVBS5Jc9CMeuP-eawDpeWLL_Ekx6JtKLM-_n6VOmwb1DVStnF6e1xcjQMu0a75J6m9TSCv6-xWuvjA35UbA; _ga=GA1.2.1600030032.1488602042; _gid=GA1.2.1409494518.1499267447");

        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(httpGet);

            LOGGER.info("download url:{} success",url);

            return EntityUtils.toString(httpResponse.getEntity());

        } catch(UTFDataFormatException e){

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch(IOException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());

        } catch (IllegalStateException e) {

            LOGGER.error("download url:{} error,msg:{}",url,e.getMessage());
        }

        return null;

    }
}
