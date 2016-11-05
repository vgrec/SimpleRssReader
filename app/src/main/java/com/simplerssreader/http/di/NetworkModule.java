package com.simplerssreader.http.di;

import com.simplerssreader.http.PcWorldRssService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public class NetworkModule {

    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(PcWorldRssService.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    PcWorldRssService providePcWorldRssService(Retrofit retrofit) {
        return retrofit.create(PcWorldRssService.class);
    }

}
