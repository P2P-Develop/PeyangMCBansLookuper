package ml.peya.mc;

import com.google.gson.Gson;
import ml.peya.mc.netty.BodyElement;
import ml.peya.mc.netty.Border;

import java.util.ArrayList;

public class Mcbans
{
    public static LookupParserPlus lookup(String mcid, String apikey, String lookupPlayer, boolean requireAPIKEY)
    {
        if (requireAPIKEY)
        {
            ArrayList<BodyElement> be = new ArrayList<BodyElement>();
            be.add(new BodyElement("exec", "playerLookup"));
            be.add(new BodyElement("admin", mcid));
            be.add(new BodyElement("player", lookupPlayer));
            String ret = Border.post(String.format("http://api.mcbans.com/v3/%s", apikey), "application/x-www-form-urlencoded", be);
            if (ret.startsWith("{\"result\":\"w\""))
            {
                LookupParserPlus lp = new LookupParserPlus();
                lp.RESULT = LookupParserPlus.STATUS.PLAYERNOTFOUND;
                return lp;
            }

            Gson gson = new Gson();
            LookupParser lp = gson.fromJson(ret, LookupParser.class);
            LookupParserPlus lpp = new LookupParserPlus();
            lpp.total = lp.total;
            lpp.reputation = lp.reputation;
            lpp.local = (ArrayList<String>) lp.local;
            lpp.global = (ArrayList<String>) lp.global;
            lpp.other = (ArrayList<String>) lp.other;
            lpp.pid = Integer.parseInt(lp.pid);
            lpp.uuid = lp.uuid;
            lpp.player = lp.player;
            lpp.executionTime = lp.executionTime;

            lpp.RESULT = LookupParserPlus.STATUS.OK;
            return lpp;

        }
        else
        {
            LookupParserPlus lp = new LookupParserPlus();
            lp.RESULT = LookupParserPlus.STATUS.APIKEYNOTFOUND;
            return lp;
        }
    }

    public static boolean isEnableApiKey (String apikey)
    {
        return Border.get(String.format("http://api.mcbans.com/v3/%s", apikey)).equals("{\"error\": \"v3: You need to specify a function!\"}");
    }
}

