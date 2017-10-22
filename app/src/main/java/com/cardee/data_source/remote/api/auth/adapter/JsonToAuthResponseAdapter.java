package com.cardee.data_source.remote.api.auth.adapter;

import android.util.Log;

import com.cardee.data_source.remote.api.auth.response.BaseAuthResponse;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;


public class JsonToAuthResponseAdapter extends TypeAdapter<BaseAuthResponse.BaseAuthResponseBody> {
    @Override
    public void write(JsonWriter out, BaseAuthResponse.BaseAuthResponseBody value) throws IOException {

    }

    @Override
    public BaseAuthResponse.BaseAuthResponseBody read(JsonReader in) throws IOException {
        Log.e("TEMP_ADAPTER_STRING", in.toString());
        BaseAuthResponse.BaseAuthResponseBody body = new BaseAuthResponse.BaseAuthResponseBody();
        return body;
    }
}
