package app.com.ieeedtu.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samarthgupta on 08/08/17.
 */


public class Person {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("mobile")
    @Expose
    private long mobile;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



}

