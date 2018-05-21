package rest;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arjun on 21-05-2018.
 */

public class RestClient {

    public static final String BASE_URL = "https://billcool.000webhostapp.com/samcom/";

    public static AuthenticationApiServices getSauthenticationApiServices() {
        return sauthenticationApiServices;
    }

    public static void setSauthenticationApiServices(AuthenticationApiServices sauthenticationApiServices) {
        RestClient.sauthenticationApiServices = sauthenticationApiServices;
    }

    private static AuthenticationApiServices sauthenticationApiServices;

    public static void init(Context mContext) {

        setupRestClient();

    }

    private static void setupRestClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().build();
                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        if ( sauthenticationApiServices == null) {
            sauthenticationApiServices = retrofit.create(AuthenticationApiServices.class);
        }
    }
    public static AuthenticationApiServices getAuthenticationApiServices() {
        return sauthenticationApiServices;
    }
}
