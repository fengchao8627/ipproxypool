package com.wenchao.ipproxypool.domain;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

public class RequestUtil {
    static   OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(5000, TimeUnit.MILLISECONDS).build();
    public static String request (String url) throws IOException{
      Request request  =  new Request.Builder().url(url).build();
     try (Response response = client.newCall(request).execute()){
        if (!response.isSuccessful()){
           throw new IOException("can't  visited " + url);
        }
        return response.body().string() ;
     }
   }

    public static String request (String url,Proxy proxy) throws IOException{
    OkHttpClient client =  new OkHttpClient().newBuilder().proxy(proxy).connectTimeout(3000, TimeUnit.MILLISECONDS).build();
        Request request  =  new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()){

            }
            return response.body().string() ;
        }
    }
    public static String requestWithHTTPS (String url,Proxy proxy) throws IOException{

        OkHttpClient client =  new OkHttpClient().newBuilder().proxy(proxy).connectTimeout(3000, TimeUnit.MILLISECONDS).build();
        Request request  =  new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()){
            if (!response.isSuccessful()){
                throw new IOException("can't  visited " + url);
            }
            return response.body().string() ;
        }
    }

}
