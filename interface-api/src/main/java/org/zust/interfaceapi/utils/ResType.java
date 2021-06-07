package org.zust.interfaceapi.utils;

import lombok.Data;

@Data
public class ResType<T> {
    private int status;
    private T data;
    public ResType() {}

    public ResType(int status) {
        this.status = status;
    }
    public ResType(int status, T data) {
        this.status = status;
        this.data = data;
    }

}
