package com.primecoder.webpage.download.core;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;

/**
 * Created by primecoder on 2017/1/16.
 */
public class Download {

    public boolean download(String url, String path) {

        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);

            writeStrToFile(path, EntityUtils.toString(httpResponse.getEntity()));

            System.out.println("download url:" + url + " success, and save to:" + path);

            return true;
        } catch(UTFDataFormatException e){
            System.err.println(e.getMessage());

            return false;
        } catch(IOException e) {
            System.err.println(e.getMessage());

            return false;
        }

    }

    private void writeStrToFile(String filepath, String str) throws IOException {
        File f = new File(filepath);
        if (!f.exists()) {
            f.createNewFile();

        }
        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(f);

            byte[] contentInBytes = str.getBytes();
            fos.write(contentInBytes);
            fos.flush();
            fos.close();

        } catch (UTFDataFormatException e){

            System.err.println(e.getMessage());
        } catch (FileNotFoundException e) {

            System.err.println(e.getMessage());
        } finally {

            if (null != fos) {
                fos.close();
            }

        }
    }
}
