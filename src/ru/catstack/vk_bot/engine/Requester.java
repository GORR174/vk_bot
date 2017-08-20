package ru.catstack.vk_bot.engine;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class Requester {

    OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(30, TimeUnit.SECONDS).build();

    public String sendRequest(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();

            return response.body().string();

        } catch (SocketTimeoutException ex) {
            Logger.log("Socket Timeout");
        }

        return null;
    }

}
