package ml.peya.mc;

import net.minecraft.util.EnumChatFormatting;

public class ChatBuilder
{
    public static String error(String message)
    {
        return getInPrefix() + "\n" +
                EnumChatFormatting.RED + "     " + message;
    }

    public static String getSectionInBans(int tabCount, String id, String server, String reason)
    {
        StringBuilder tab = new StringBuilder();
        for (int i = 0; tabCount > i; i++)
        {
            tab.append("    ");
        }
        return String.format("%s" + EnumChatFormatting.BLUE + "%s " + EnumChatFormatting.DARK_AQUA + "%s " + EnumChatFormatting.WHITE + ".:.    " + EnumChatFormatting.DARK_BLUE + "%s", tab, id, server, reason);
    }

    public static String getPrefix(int tabCount,  String prefix, Object value)
    {
        StringBuilder tab = new StringBuilder();
        for (int i = 0; tabCount > i; i++)
        {
            tab.append("    ");
        }
        return tab + prefix + ": " + value;
    }

    public static String getInPrefix()
    {
        return EnumChatFormatting.AQUA + "-------" + EnumChatFormatting.BLUE + "PeyangMCBansLookuper" + EnumChatFormatting.AQUA + "-------";
    }

}
