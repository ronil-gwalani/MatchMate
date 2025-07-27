package org.ronil.matchmate.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun providesRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}


fun getRestApis(retrofit: Retrofit): RestApis {
    return retrofit.create(RestApis::class.java)
}

fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    val okhttpClientBuilder = OkHttpClient.Builder()
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

    okhttpClientBuilder.addInterceptor(Interceptor { chain ->
        val original: Request = chain.request()
        val request: Request = original.newBuilder()
//                .header("Authorization", "Bearer ${"IConstants.JwtKeys.token_key"}")
            .build()
        chain.proceed(request)
    })
    val timeOutSec = 45
    okhttpClientBuilder.connectTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
    okhttpClientBuilder.readTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
    okhttpClientBuilder.writeTimeout(timeOutSec.toLong(), TimeUnit.SECONDS)
    okhttpClientBuilder.addInterceptor(loggingInterceptor)
    return okhttpClientBuilder.build()
}