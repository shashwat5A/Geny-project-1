package com.example.bounce.Utility;

import com.example.bounce.Model.OtpResponse;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LoginResponseDeserializer implements JsonDeserializer<OtpResponse> {
    @Override
    public OtpResponse deserialize(JsonElement json, Type typeOfT
            , JsonDeserializationContext context) throws JsonParseException {
        return null;
    }

}