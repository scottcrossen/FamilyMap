package com.scottcrossen42.familymap.httpaccess;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Scott Leland Crossen
 * @link http://scottcrossen42.com
 * Created on 12/1/16.
 */
public class GetRequest extends AsyncTask<Void, Void, String> {

    private IHTTPGetter who_called_me;
    private String handle;
    private Exception error;

    public GetRequest(IHTTPGetter _who_called_me, String _handle)
    {
        super();
        who_called_me = _who_called_me;
        handle = _handle;
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            ServerSession server_info = ServerSession.getInstance();
            URL url = server_info.getURL(handle);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Set the authorization token if necessary
            if (server_info.getAuth() != null)
                connection.addRequestProperty("Authorization", server_info.getAuth());
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Get response body input stream
                InputStream responseBody = connection.getInputStream();
                // Read response body bytes
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = responseBody.read(buffer)) != -1)
                    baos.write(buffer, 0, length);
                // Convert response body bytes to a string
                return baos.toString();
            } else throw new BadConnectionException(connection.getResponseCode());
        } catch (Exception e) {
            error = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (error == null) who_called_me.rxData(result);
        else who_called_me.HTTPError(error);

    }
}