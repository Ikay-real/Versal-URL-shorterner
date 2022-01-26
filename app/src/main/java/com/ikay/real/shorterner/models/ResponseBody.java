package com.ikay.real.shorterner.models;

import com.google.gson.annotations.SerializedName;

public class ResponseBody {
    @SerializedName("status_code")
    private String StatusCode;

    public String getHostName() {
        return HostName;
    }

    public void setHostName(String hostName) {
        HostName = hostName;
    }

    @SerializedName("hostname")
    private String HostName;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    @SerializedName("data")
    private String Data;
}
