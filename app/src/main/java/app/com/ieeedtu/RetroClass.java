package app.com.ieeedtu;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by samarthgupta on 08/08/17.
 */

public class RetroClass {
    static Retrofit.Builder builder = new Retrofit.Builder().baseUrl("http://android.ieeedtu.com/").addConverterFactory(GsonConverterFactory.create());
    static Retrofit retrofit = builder.build();
    public static DataInterface client = retrofit.create(DataInterface.class);

}
