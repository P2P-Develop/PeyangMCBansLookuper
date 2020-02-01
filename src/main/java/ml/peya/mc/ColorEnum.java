package ml.peya.mc;

import net.minecraft.util.EnumChatFormatting;

public class ColorEnum
{
    public static EnumChatFormatting fromString (String colorString)
    {
        if (colorString.equals("aqua"))
            return EnumChatFormatting.AQUA;
        else if (colorString.equals("black"))
            return EnumChatFormatting.BLACK;
        else if (colorString.equals("blue"))
            return EnumChatFormatting.BLUE;
        else if (colorString.equals("dark_aqua"))
            return EnumChatFormatting.DARK_AQUA;
        else if (colorString.equals("dark_blue"))
            return EnumChatFormatting.DARK_BLUE;
        else if (colorString.equals("dark_gray"))
            return EnumChatFormatting.DARK_GRAY;
        else if (colorString.equals("dark_green"))
            return EnumChatFormatting.DARK_GREEN;
        else if (colorString.equals("dark_purple"))
            return EnumChatFormatting.DARK_PURPLE;
        else if (colorString.equals("dark_red"))
            return EnumChatFormatting.DARK_RED;
        else if (colorString.equals("gold"))
            return EnumChatFormatting.GOLD;
        else if (colorString.equals("gray"))
            return EnumChatFormatting.GRAY;
        else if (colorString.equals("green"))
            return EnumChatFormatting.GREEN;
        else if (colorString.equals("light_purple"))
            return EnumChatFormatting.LIGHT_PURPLE;
        else if (colorString.equals("red"))
            return EnumChatFormatting.RED;
        else if (colorString.equals("yellow"))
            return EnumChatFormatting.YELLOW;
        else
            return EnumChatFormatting.WHITE;








    }
}
