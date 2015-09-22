package com.empty.gradlefetch.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit.Converter;

/**
 * Copied from Jack github
 * @author Jack Wharton
 *         Date: 31.08.15
 */
public class GsonConverterFactory implements Converter.Factory {

    private final Gson gson;

    public GsonConverterFactory(Gson gson) {
        if(gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    private static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    @Override
    public Converter<?> get(Type type) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonConverter<>(adapter);
    }
}
