package com.scottcrossen42.familymap.httpaccess;


import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class HTTPRequest extends AsyncTask<Void, Void, String> {

    public static final String SERVER_URL = "pandora.byu.edu";

    private String handle;
    private HTTPRequester caller;
    private String method;
    private String request_body = null;

    public HTTPRequest(String _method, String _handle, HTTPRequester _caller)
    {
        super();
        method = _method;
        caller = _caller;
        handle = _handle;
    }

    public HTTPRequest(String _method, String _handle, HTTPRequester _caller, String _request_body)
    {
        super();
        method = _method;
        caller = _caller;
        handle = _handle;
        request_body = _request_body;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        try {
            //TODO: lower timeout from 3 minutes to something like 30 seconds
            URL url = new URL(SERVER_URL + handle);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod(method);
            if (request_body != null) {
                connection.setDoOutput(true);
            }

            /*TODO: set the authorization token if necessary
            if (//something)
            {
                connection.addRequestProperty("Authorization", ServerInfo.getAuth());
            }*/

            connection.connect();

            if (request_body != null) {
                // Write post data to request body
                OutputStream requestBody = connection.getOutputStream();
                requestBody.write(request_body.getBytes());
                requestBody.close();
            }

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                // Get response body input stream
                InputStream responseBody = connection.getInputStream();

                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1) {
                    baos.write(buffer, 0, length);
                }

                // Convert response body bytes to a string
                return baos.toString();
            }
            else
            {
                throw new BadConnectionException(connection.getResponseCode());
            }
        }
        catch (Exception e) {
            error = e;
            return null;
        }
    }

    private Exception error;

    @Override
    protected void onPostExecute(String result)
    {
        if (error == null)
        {
            caller.handleResponse(result);
        }
        else
        {
            caller.handleException(error);
        }
    }
}