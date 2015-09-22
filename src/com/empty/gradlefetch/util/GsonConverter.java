package com.empty.gradlefetch.util;

import com.google.gson.TypeAdapter;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import okio.Buffer;
import retrofit.Converter;

/**
 * Copied form Jack github
 * @author Jack Wharton
 *         Date: 31.08.15
 */
public class GsonConverter<T> implements Converter<T> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final TypeAdapter<T> typeAdapter;

    public GsonConverter(TypeAdapter<T> typeAdapter) {
        this.typeAdapter = typeAdapter;
    }

    @Override
    public T fromBody(ResponseBody body) throws IOException {
        Reader in = body.charStream();
        try {
            return typeAdapter.fromJson(in);
        } finally {
            try {
                in.close();
            } catch (IOException ignored) {}
        }
    }

    @Override
    public RequestBody toBody(T value) {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        try {
            typeAdapter.toJson(writer, value);
            writer.flush();
        } catch (IOException e) {
            throw new AssertionError(e);
        }

        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}
