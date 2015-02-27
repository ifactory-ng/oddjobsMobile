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
import java.util.concurrent.Callable;

/**
 * Created by smile on 11/8/14.
 */
public class GetData implements Callable<JSONObject> {
    //private HttpGet get;
    private String URI;
    //HttpClient httpclient;
    //JSONArray rs;

    public GetData(String URI) {

        this.URI = URI;
    }

    @Override
    public JSONObject call() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet get = new HttpGet(URI);

        HttpResponse response = httpclient.execute(get);
        String result = EntityUtils.toString(response.getEntity());
        //JSONArray rs = new JSONArray(result);
        JSONObject rss = new JSONObject(result);
        return rss;
    }
}