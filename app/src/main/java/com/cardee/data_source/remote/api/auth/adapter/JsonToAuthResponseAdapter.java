package com.cardee.data_source.remote.api.auth.adapter;

import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;


public class JsonToAuthResponseAdapter extends TypeAdapter<BaseAuthResponse.BaseAuthResponseBody> {
    @Override
    public void write(JsonWriter out, BaseAuthResponse.BaseAuthResponseBody value) throws IOException {

    }

    @Override
    public BaseAuthResponse.BaseAuthResponseBody read(JsonReader in) throws IOException {
        String bodyStr = in.nextString();
        BaseAuthResponse.BaseAuthResponseBody body = new BaseAuthResponse.BaseAuthResponseBody();
        if (bodyStr.equals(""))
            return body;

        if (bodyStr.startsWith("Token ")) {
            body.setToken(bodyStr.split(" ")[1]);
        } else {
            Gson gson = new Gson();
            Map<String, String[]> errors =
                    gson.fromJson(in, new TypeToken<Map<String, String[]>>() {
                    }.getType());
            body.setErrors(errors);
        }
        return body;
    }
}
