package org.zust.interfaceapi.utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
