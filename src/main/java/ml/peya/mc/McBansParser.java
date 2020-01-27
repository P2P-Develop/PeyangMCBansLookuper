package ml.peya.mc;

import java.util.ArrayList;

public class McBansParser
{
    public int id;
    public String ip;
    public String reason;
    public static McBansParser parse(String bans)
    {
        String[] lname = bans.split(" ");

        String id = lname[0];
        String ip = lname[1];
        ArrayList<String> reasons = new ArrayList<String>();
        for (int i = 0; i < lname.length; i++)
        {
            if (i == 0 || i == 1 || i == 2)
            {
                continue;
            }

            reasons.add(lname[i]);
        }
        McBansParser mcBansParser = new McBansParser();
        mcBansParser.id = Integer.parseInt(id.substring(1));
        mcBansParser.ip = ip;
        mcBansParser.reason = String.join(", ", reasons);
        return mcBansParser;
    }
}
