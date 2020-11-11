package com.example.zulfikaranshari.rentku.network;

import com.example.zulfikaranshari.rentku.model.ActionSMS;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by tanwir on 27/07/2017.
 */
public interface DataService {

    @GET("smsapi.php")
    Call<List<ActionSMS>> SendAction(
            @Query("userkey") String userkey,
            @Query("passkey") String passkey,
            @Query("nohp") String nohp,
            @Query("pesan") String pesan
    );
}