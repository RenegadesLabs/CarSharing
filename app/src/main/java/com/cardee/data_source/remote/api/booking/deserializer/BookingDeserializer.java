package com.cardee.data_source.remote.api.booking.deserializer;

import android.util.Log;

import com.cardee.data_source.remote.api.ErrorResponseBody;
import com.cardee.data_source.remote.api.booking.response.BookingEntity;
import com.cardee.data_source.remote.api.booking.response.BookingResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;


public class BookingDeserializer implements JsonDeserializer<BookingResponse> {

    private static final String TAG = BookingDeserializer.class.getSimpleName();

    private Gson gson;
    private Type bookingListType;

    public BookingDeserializer() {
        gson = new Gson();
        bookingListType = new TypeToken<List<BookingEntity>>() {
        }.getType();
    }

    @Override
    public BookingResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement successElement = jsonObject.get("success");
        JsonElement messageElement = jsonObject.get("message");
        JsonElement codeElement = jsonObject.get("code");

        BookingResponse response = new BookingResponse();
        response.setSuccess(successElement == null ? null : successElement.getAsBoolean());
        response.setMessage(messageElement == null ? null : messageElement.getAsString());
        response.setResponseCode(codeElement == null ? null : codeElement.getAsInt());

        JsonElement dataElement = jsonObject.get("data");
        try {
            JsonArray bookingArray = dataElement.getAsJsonArray();
            List<BookingEntity> bookings = gson.fromJson(bookingArray, bookingListType);
            response.setBookings(bookings);
            return response;
        } catch (IllegalStateException ex) {
            Log.e(TAG, ex.getMessage());
        }
        try {
            JsonObject errorObject = dataElement.getAsJsonObject();
            ErrorResponseBody errors = gson.fromJson(errorObject, ErrorResponseBody.class);
            response.setErrors(errors);
        } catch (IllegalStateException ex) {
            Log.e(TAG, ex.getMessage());
        }
        return response;
    }
}
