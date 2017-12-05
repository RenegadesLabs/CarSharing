package com.cardee.data_source.remote.api.util;


import android.util.Log;

import com.cardee.data_source.remote.api.profile.response.NoDataResponse;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ResponseDeserializer implements JsonDeserializer<NoDataResponse> {

    @Override
    public NoDataResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonElement dataElement = json.getAsJsonObject().get("data");
        if (dataElement.isJsonObject()) {
            Gson gson = new Gson();
            return gson.fromJson(json, NoDataResponse.class);
        } else if (dataElement.getAsString().equals("")) {
            JsonElement messageElement = json.getAsJsonObject().get("message");
            JsonElement codeElement = json.getAsJsonObject().get("code");
            JsonElement successElement = json.getAsJsonObject().get("success");

            String message = messageElement.getAsString();
            int code = codeElement.getAsInt();
            boolean success = successElement.getAsBoolean();

            NoDataResponse response = new NoDataResponse();
            response.setMessage(message);
            response.setResponseCode(code);
            response.setSuccess(success);
            return response;
        } else {
            Log.d("ResultsDeserializerJson", dataElement.toString());
            throw new JsonParseException("Unsupported type of data element");
        }
    }
}
