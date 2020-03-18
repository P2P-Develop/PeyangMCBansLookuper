package ml.peya.mc.Parser;

public class BanLookupParserPlus
{
    public BanLookupParserPlus.STATUS RESULT;
    public String player;
    public String reason;
    public String admin;
    public String server;
    public String date;
    public String type;
    public String reploss;
    public Double executionTime;
    public enum STATUS
    {
        OK,
        BANNOTFOUND,
        APIKEYNOTFOUND,
        UNKNOWNERROR,
        I_AM_A_TEAPOT,
        UNKNOWNHOST
    };
}