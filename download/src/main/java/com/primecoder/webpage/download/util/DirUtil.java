package com.primecoder.webpage.download.util;

import java.io.File;

/**
 *
 */
public class DirUtil {

    public void mkdir(String path) {

        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
