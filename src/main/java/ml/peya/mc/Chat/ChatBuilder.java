package ml.peya.mc.Chat;

import ml.peya.mc.Parser.McBansParser;
import ml.peya.mc.PeyangMcBansLookuper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;


public class ChatBuilder
{
    public static ITextComponent error(String message)
    {
        String response = getInPrefix().getFormattedText() +
                "\n" +
                TextFormatting.RED +
                "     " +
                message;
        return new TextComponentString(response);
    }

    public static ITextComponent getPrefix(int tabCount,  String prefix, Object value)
    {
        StringBuilder tab = new StringBuilder();
        for (int i = 0; tabCount > i; i++)
            tab.append("    ");
        tab.append(prefix);
        tab.append(": ");
        tab.append(value);
        return new TextComponentString(tab.toString());
    }

    public static ITextComponent getInPrefix()
    {
        String response = TextFormatting.AQUA +
                "-------" +
                TextFormatting.BLUE +
                "PeyangMcBansLookuper" +
                TextFormatting.AQUA +
                "-------";
        return new TextComponentString(response);
    }


    public static ITextComponent getBanSectionComponent(String banSectionString)
    {
        McBansParser mcBansParser = McBansParser.parse(banSectionString);
        int id = mcBansParser.id;
        ITextComponent banning = new TextComponentString(PeyangMcBansLookuper.banColor.get(0) + "#" + id);
        Style style = new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("Click to execute BanLookup Command.")));
        style.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/banlookup " + id));
        banning.setStyle(style);
        banning.appendSibling(new TextComponentString(" " + PeyangMcBansLookuper.banColor.get(1) + mcBansParser.ip + PeyangMcBansLookuper.banColor.get(2) + "    .:.    " + PeyangMcBansLookuper.banColor.get(3) + mcBansParser.reason));
        return banning;
    }
}
