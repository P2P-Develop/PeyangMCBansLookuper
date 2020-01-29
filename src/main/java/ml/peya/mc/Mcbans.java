package ml.peya.mc;

import com.google.gson.Gson;
import ml.peya.mc.netty.BodyElement;
import ml.peya.mc.netty.Border;

import java.util.ArrayList;

public class Mcbans
{
    public static LookupPerserPlus lookup(String mcid, String apikey, String lookupPlayer, boolean requireAPIKEY)
    {
        if (requireAPIKEY)
        {
            ArrayList<BodyElement> be = new ArrayList<BodyElement>();
            be.add(new BodyElement("exec", "playerLookup"));
            be.add(new BodyElement("admin", mcid));
            be.add(new BodyElement("player", lookupPlayer));
            if (Border.post(String.format("http://api.mcbans.com/v3/%s", apikey),
                    "application/x-www-form-urlencoded",
                    be).startsWith("{\"result\":\"w\""))
            {
                LookupPerserPlus lp = new LookupPerserPlus();
                lp.RESULT = LookupPerserPlus.STATUS.PLAYERNOTFOUND;
                return lp;
            }

            Gson gson = new Gson();
            LookupPerser lp = gson.fromJson(ret, LookupPerser.class);
            LookupPerserPlus lpp = new LookupPerserPlus();
            lpp.total = lp.total;
            lpp.reputation = lp.reputation;
            lpp.local = (ArrayList<String>) lp.local;
            lpp.global = (ArrayList<String>) lp.global;
            lpp.other = (ArrayList<String>) lp.other;
            lpp.pid = Integer.parseInt(lp.pid);
            lpp.uuid = lp.uuid;
            lpp.player = lp.player;
            lpp.executionTime = lp.executionTime;

            lpp.RESULT = LookupPerserPlus.STATUS.OK;
            return lpp;

        }
        else
        {
            LookupPerserPlus lp = new LookupPerserPlus();
            lp.RESULT = LookupPerserPlus.STATUS.APIKEYNOTFOUND;
            return lp;
        }
    }

    public static boolean isEnableApiKey (String apikey)
    {
        return Border.get(String.format("http://api.mcbans.com/v3/%s", apikey)).equals("{\"error\": \"v3: You need to specify a function!\"}");
    }
}

