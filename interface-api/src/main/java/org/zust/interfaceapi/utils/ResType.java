package org.zust.interfaceapi.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class ResType<T> implements Serializable {
    private int status;
    private int code;
    private T data;
    public ResType() {}

    public ResType(int status, int code) {
        this.status = status;
        this.code = code;
    }

    public ResType(T data) {
        this.status = 200;
        this.data = data;
    }

    public int getStatus() {
       return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
