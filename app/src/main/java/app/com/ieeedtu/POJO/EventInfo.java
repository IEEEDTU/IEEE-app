package app.com.ieeedtu.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samarthgupta on 08/08/17.
 */

public class EventInfo {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("coordinators")
    @Expose
    private List<Person> coordinators = null;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("fromDateTime")
    @Expose
    private String fromDateTime;
    @SerializedName("toDateTime")
    @Expose
    private String toDateTime;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Person> getCoordinators() {
        return coordinators;
    }

    public void setCoordinators(List<Person> coordinators) {
        this.coordinators = coordinators;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(String fromDateTime) {
        this.fromDateTime = fromDateTime;
    }

    public String getToDateTime() {
        return toDateTime;
    }

    public void setToDateTime(String toDateTime) {
        this.toDateTime = toDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}