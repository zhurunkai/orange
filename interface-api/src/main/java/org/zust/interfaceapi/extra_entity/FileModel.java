package org.zust.interfaceapi.extra_entity;

import java.io.File;

/**
 * @author lxy
 * @date 2020-09-04-17:02
 * @function
 **/
public class FileModel {

    private File file;
    private String md5;

    public FileModel(File file, String md5) {
        this.file = file;
        this.md5 = md5;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
