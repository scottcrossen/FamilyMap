package com.scottcrossen42.familymap.model;

import java.io.Serializable;

/**
 * Created by rodham on 2/29/2016.
 */
public class ServerSession implements Serializable {

    private String name;
    private String host;
    private String port;
    private String auth;

    public String getUserName() {
        return name;
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String password) {
        this.auth = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

}
