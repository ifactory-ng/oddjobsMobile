package com.ifactory.oddjobs;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.List;

/**
 * Created by smile on 11/8/14.
 */
public class Jobject implements Runnable{
    private HttpGet get;
    private String URI;
    HttpClient httpclient;
    JSONArray rs;

    public Jobject(String URI) {

        this.URI = URI;
    }

    @Override
    public void run() {
        httpclient = new DefaultHttpClient();
        get = new HttpGet(URI);
        try {
            HttpResponse response = httpclient.execute(get);
            String result = EntityUtils.toString(response.getEntity());
            rs = new JSONArray(result);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONArray obj() {
        run();
        return rs;
    }
}