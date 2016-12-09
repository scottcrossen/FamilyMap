package com.scottcrossen42.familymap.httpaccess;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public interface IHTTPPoster {

    public void txData(String result);

    // What happens when an Error gets received?
    public void HTTPError(Exception error);
}
