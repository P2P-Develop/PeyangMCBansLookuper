package ml.peya.mc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BanLookupParser
{

    @SerializedName("player")
    @Expose
    public String player;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("admin")
    @Expose
    public String admin;
    @SerializedName("server")
    @Expose
    public String server;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("reploss")
    @Expose
    public String reploss;
    @SerializedName("executionTime")
    @Expose
    public Double executionTime;
}