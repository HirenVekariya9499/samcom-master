package rest;

import com.sourcey.materiallogindemo.BaseResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Arjun on 21-05-2018.
 */

public interface    AuthenticationApiServices {

    @POST("register.php")
    @FormUrlEncoded
    Call<BaseResponse> GetRegister(@Field("name") String name,
                                   @Field("user_pass") String user_pass,
                                   @Field("user_name") String user_name);


    @POST("login.php")
    @FormUrlEncoded
    Call<BaseResponse> GetLogin(@Field("name") String name,
                                   @Field("user_pass") String user_pass);

    Call<BaseResponse> GetRegister(String s, String s1);
}