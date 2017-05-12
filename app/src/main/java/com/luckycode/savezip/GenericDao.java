package com.luckycode.savezip;
import android.content.Context;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mdagostino on 10/6/16.
 */

public class GenericDao {

    private Context context;

    private Retrofit retrofit;

    public GenericDao(Context context) {
        this.context = context;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder builder = original.newBuilder()
//                                                  .addHeader("language", getContext().getString(R.string.languague))
                        .method(original.method(), original.body());


                Request request = builder.build();

                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        retrofit = new Retrofit.Builder()
                .baseUrl("http://tictapps-test.s3.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public Context getContext() {
        return context;
    }
}
