package com.scottcrossen42.familymap.httpaccess;


public interface HTTPRequester {
    void handleResponse(String response_body);
    void handleException(Exception e);
}
