package ml.peya.mc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LookupParser
{

    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("reputation")
    @Expose
    public int reputation;
    @SerializedName("local")
    @Expose
    public List<String> local;
    @SerializedName("global")
    @Expose
    public List<String> global;
    @SerializedName("other")
    @Expose
    public List<String> other;
    @SerializedName("pid")
    @Expose
    public String pid;
    @SerializedName("uuid")
    @Expose
    public String uuid;
    @SerializedName("player")
    @Expose
    public String player;
    @SerializedName("executionTime")
    @Expose
    public double executionTime;

}