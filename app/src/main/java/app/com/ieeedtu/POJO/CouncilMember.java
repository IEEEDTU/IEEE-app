package app.com.ieeedtu.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by samarthgupta on 08/08/17.
 */

public class CouncilMember {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("member")
    @Expose
    private Person member;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("fbProfileLink")
    @Expose
    private String fbProfileLink;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getMember() {
        return member;
    }

    public void setMember(Person member) {
        this.member = member;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFbProfileLink() {
        return fbProfileLink;
    }

    public void setFbProfileLink(String fbProfileLink) {
        this.fbProfileLink = fbProfileLink;
    }
}
