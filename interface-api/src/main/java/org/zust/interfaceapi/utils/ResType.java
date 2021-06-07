package org.zust.interfaceapi.utils;

import lombok.Data;

@Data
public class ResType {
    private int status;
    private Object data;
    public ResType() {

    }
    public ResType(int status) {
        this.status = status;
    }
    public ResType(int status, Object data) {
        this.status = status;
        this.data = data;
    }
}
