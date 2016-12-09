package com.scottcrossen42.familymap.httpaccess;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public interface IHTTPGetter {

    // What happens when the post gets received?
    public void rxData(String data);

    // What happens when an Error gets received?
    public void HTTPError(Exception error);
}
