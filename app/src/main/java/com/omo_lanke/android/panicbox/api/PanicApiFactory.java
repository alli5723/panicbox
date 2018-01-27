package com.omo_lanke.android.panicbox.api;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by omo_lanke on 31/12/2017.
 */

public class PanicApiFactory {
    public static <S> S createService(Class<S> service) {

        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint("http://104.236.11.111:4000/")
//                .setEndpoint("http://104.131.59.3:4000/")
                .setLogLevel(RestAdapter.LogLevel.FULL);

        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
            }
        });

        RestAdapter adapter = builder.build();
        return adapter.create(service);
    }
}
