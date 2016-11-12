package com.scottcrossen42.familymap.httpaccess;

/**
 * Created by slxn42 on 11/11/16.
 */
public interface HTTPGetter {
    public void rxData(String data);
    public void HTTPError(Exception error);
}
