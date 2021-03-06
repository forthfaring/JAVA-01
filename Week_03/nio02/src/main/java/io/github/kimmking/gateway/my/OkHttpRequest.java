package io.github.kimmking.gateway.my;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @program: JAVA-01
 * @description:
 * @author: wfy
 * @create: 2021-01-21 23:24
 */
public class OkHttpRequest {

    public static void main(String[] args) throws Exception {
        System.out.println(run("https://www.baidu.com"));
    }

    static String run(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    static Response getResponse(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            return response;
        }
    }
}
