package com.ikay.real.shorterner.retrofitutilities;

import com.ikay.real.shorterner.models.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("http://cast-iron-labor.000webhostapp.com/shortener_ikay_real.php")
    Call<ResponseBody>sendLinkRequest(@Field("link_request") String link_request);

}
