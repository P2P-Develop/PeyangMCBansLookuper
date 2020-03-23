package ml.peya.mc.Request;

import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.Chat.EnumColor;
import ml.peya.mc.McBans;
import ml.peya.mc.Netty.Border;
import ml.peya.mc.Parser.BanLookupParserPlus;
import ml.peya.mc.PeyangMcBansLookuper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import java.util.ArrayList;

public abstract class BanLookupRequest
{
    public static ArrayList<ITextComponent> lookup(String sender, int banId, String apiKey)
    {
        ArrayList<ITextComponent> response = new ArrayList<>();
        if(!McBans.isEnableApiKey(apiKey))
        {
            response.add(ChatBuilder.error(I18n.format("command.error.apinotf")));
            return response;
        }
        if (Border.get("https://api.mcbans.com/v3").startsWith("<html><head><title>500 Internal Server Error"))
        {
            response.add(ChatBuilder.error(I18n.format("command.error.servernotf")));
            return response;
        }
        BanLookupParserPlus lpp = McBans.banlookup(sender, apiKey, banId, true);

        BanLookupParserPlus.STATUS result = lpp.RESULT;
        switch (result)
        {
            case OK:
                String admin = lpp.admin;
                String type = lpp.type;
                switch (type)
                {
                    case "global":
                        type = EnumColor.fromString("red") + I18n.format("ban.type.global");
                        break;
                    case "local":
                        type = EnumColor.fromString("green") + I18n.format("ban.type.local");
                        break;
                    case "temp":
                        type = EnumColor.fromString("light_purple") + I18n.format("ban.type.local");
                        break;
                }
                response.add(ChatBuilder.getInPrefix());
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + "#" + banId));
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reason"), PeyangMcBansLookuper.secondColor + lpp.reason.replace("ENDS", I18n.format("command.success.banlp.end"))));
                ITextComponent banning = new TextComponentString(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.admin"), PeyangMcBansLookuper.secondColor + admin).getFormattedText());
                Style mouse = new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to execute PlayerLookup Command.")));
                mouse.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/lp " + admin));
                banning.setStyle(mouse);
                response.add(banning);
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.server"), PeyangMcBansLookuper.secondColor + lpp.server));
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.date"), PeyangMcBansLookuper.secondColor + lpp.date));
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.type"), PeyangMcBansLookuper.secondColor + type));
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reploss"), PeyangMcBansLookuper.secondColor + lpp.reploss));
                response.add(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""));
                response.add(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer.executiontime"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.executionTime)));
                break;
            case BANNOTFOUND:
                response.add(ChatBuilder.error(I18n.format("command.error.notfoundban")));
                break;

        }
        return response;
    }
}
