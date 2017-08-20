package org.viktorot.izicheck.net;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;


public class PageAdapter implements Converter<ResponseBody, Document> {

    static final Converter.Factory FACTORY = new Factory() {
        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            if (type == Document.class) return new PageAdapter();
            return null;
        }
    };

    @Override
    public Document convert(@NonNull ResponseBody responseBody) throws IOException {
        return Jsoup.parse(responseBody.string());
    }
}
