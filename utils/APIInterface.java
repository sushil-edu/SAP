package in.kestone.sap.utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import in.kestone.sap.holder.Detail;
import in.kestone.sap.holder.Scan;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {

    //the signin call
    @POST("Api/login")
    Call<List<LoginResult>> userLogin(@Body LoginResult loginResult);

    @POST("Api/QR")
    Call<JSONObject> upload(@Body Detail scan);
}
