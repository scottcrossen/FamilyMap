package com.scottcrossen42.familymap.httpaccess;

import java.net.URL;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class ServerSession{

    //Mainly just the variable set for the connection.
    private static ServerSession session = new ServerSession();
    private String name;
    private String host;
    private String port;
    private String auth;

    public static ServerSession getInstance() { return session; }

    public void clearLogin() {
        host = null;
        port = null;
        auth = null;
    }

    public URL getURL(String handle) throws java.net.MalformedURLException {
        return new URL("http://" + host + ":" + port + handle);
    }
    public String getUserName() { return name; }
    public void setUserName(String name) { this.name = name; }
    public String getAuth() { return auth; }
    public void setAuth(String password) { this.auth = password; }
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }
    public String getPort() { return port; }
    public void setPort(String port) { this.port = port; }
    public boolean isLoggedOn() { return (host != null && port != null && auth != null); }
}
