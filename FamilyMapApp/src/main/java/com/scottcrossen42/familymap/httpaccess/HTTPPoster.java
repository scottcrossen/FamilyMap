package com.scottcrossen42.familymap.httpaccess;

/**
 * Created by slxn42 on 11/11/16.
 */
public interface HTTPPoster {
    public void txData(String result);
    public void HTTPError(Exception error);
}
