package com.lifeline.fyp.fyp.retrofit;

import com.lifeline.fyp.fyp.retrofit.responses.UploadObject;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Mujtaba_khalid on 4/9/2018.
 */

public interface UploadImageInterface {

    @Multipart
    @POST("uploadpicture")
    Call<UploadObject> uploadFile(@Part MultipartBody.Part file, @Part("name") RequestBody name);
}
