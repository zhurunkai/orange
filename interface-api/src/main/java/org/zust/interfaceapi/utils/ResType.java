package org.zust.interfaceapi.utils;

import lombok.Data;

@Data
public class ResType {
    private int status;
    private String msg;
    private Object data;
    public ResType() {

    }
    public ResType(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }
    public ResType(int status,String msg,Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
}
