package ml.peya.mc;

import net.minecraft.util.EnumChatFormatting;

public class ChatBuilder
{
    public static String error(String message)
    {
        return getInPrefix() + "\n" +
                EnumChatFormatting.RED + "     " + message;
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
