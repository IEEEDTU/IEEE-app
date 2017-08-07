package app.com.ieeedtu;

import java.util.List;

import app.com.ieeedtu.POJO.CouncilMember;
import app.com.ieeedtu.POJO.NewsInfo;
import app.com.ieeedtu.POJO.SigInfo;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by samarthgupta on 08/08/17.
 */

public interface DataInterface {

    @GET("/news/")
    Call<List<NewsInfo>> getNews();

    @GET("/sigs/")
    Call<List<SigInfo>> getSigs();

    @GET("/council/")
    Call<List<CouncilMember>> getCouncil();
}
