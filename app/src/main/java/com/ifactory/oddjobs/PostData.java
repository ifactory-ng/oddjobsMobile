package com.ifactory.oddjobs;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.jar.Attributes;

/**
 * Created by smilecs on 2/21/15.
 */
public class PostData implements Callable<String> {
    List<NameValuePair> myList;
    String URI;
    public PostData(List<NameValuePair> data, String URI){
        this.myList = data;
        this.URI = URI;
    }
    @Override
    public String call() throws Exception {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(URI);
        //httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("Accept-Encoding", "application/json");
        httpPost.setHeader("Accept-Language", "en-US");

        httpPost.setEntity(new UrlEncodedFormEntity(myList));
            Log.d("lst", myList.toString());
//        httpPost.setEntity(new UrlEncodedFormEntity(myList));
        HttpResponse res = httpclient.execute(httpPost);
        String result = EntityUtils.toString(res.getEntity());
        Log.d("result", result);

            return null;
    }
}
