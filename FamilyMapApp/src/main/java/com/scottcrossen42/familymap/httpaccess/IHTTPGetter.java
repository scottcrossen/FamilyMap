package com.scottcrossen42.familymap.httpaccess;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public interface IHTTPGetter {

    public void rxData(String data);

    public void HTTPError(Exception error);
}
