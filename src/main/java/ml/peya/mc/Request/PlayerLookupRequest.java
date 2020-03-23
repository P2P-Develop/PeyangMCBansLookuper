package ml.peya.mc.Request;

import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.Chat.EnumColor;
import ml.peya.mc.McBans;
import ml.peya.mc.Netty.Border;
import ml.peya.mc.Parser.LookupParserPlus;
import ml.peya.mc.PeyangMcBansLookuper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;

public class PlayerLookupRequest extends AbstractMethodError
{
    public static ArrayList<ITextComponent> lookup(String sender, String playerName, String apiKey)
    {
        boolean isEnableApiKey = !McBans.isEnableApiKey(apiKey);
        ArrayList<ITextComponent> response = new ArrayList<>();
        if(isEnableApiKey)
        {
            response.add(ChatBuilder.error(I18n.format("command.error.apinotf")));
            return response;
        }

        if (Border.get("https://api.mcbans.com/v3").startsWith("<html><head><title>500 Internal Server Error"))
        {
            response.add(ChatBuilder.error(I18n.format("command.error.servernotf")));
            return response;
        }

        LookupParserPlus lpp = McBans.lookup(sender, apiKey, playerName, true);

        LookupParserPlus.STATUS result = lpp.RESULT;
        if (result != LookupParserPlus.STATUS.OK)
        {
            switch (result)
            {
                case I_AM_A_TEAPOT:
                    response.add(ChatBuilder.error(I18n.format("command.error.j.teapot")));
                    break;
                case PLAYERNOTFOUND:
                    response.add(ChatBuilder.error(I18n.format("command.error.notfoundplayer")));
                    break;
                case UNKNOWNERROR:
                    response.add(ChatBuilder.error(I18n.format("command.error.unknown")));
                    break;
            }
        }
        response.add(ChatBuilder.getInPrefix());
        response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + lpp.player));
        response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.totalbans"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.total)));
        response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.reputation"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.reputation) + "/10.0"));
        response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.localbans"), !lpp.local.isEmpty() ? "" : PeyangMcBansLookuper.secondColor + "N/A"));

        for (String local : lpp.local)
            response.add(ChatBuilder.getBanSectionComponent(local));

        for (String global : lpp.global)
            response.add(ChatBuilder.getBanSectionComponent(global));
        response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""));
        response.add(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.processid"), EnumColor.fromString("dark_aqua") + String.valueOf(lpp.pid)));
        response.add(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.playerlp.uuid"), EnumColor.fromString("dark_aqua") + lpp.uuid));
        response.add(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.executiontime"), EnumColor.fromString("dark_aqua") + String.valueOf(lpp.executionTime)));
        return response;
    }
}
