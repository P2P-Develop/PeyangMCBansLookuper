package ml.peya.mc.Chat;

import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;

public class ColorEnum
{
    public static EnumChatFormatting fromString (String colorString)
    {
        switch (colorString)
        {
            case "aqua":
                return EnumChatFormatting.AQUA;
            case "black":
                return EnumChatFormatting.BLACK;
            case "blue":
                return EnumChatFormatting.BLUE;
            case "dark_aqua":
                return EnumChatFormatting.DARK_AQUA;
            case "dark_blue":
                return EnumChatFormatting.DARK_BLUE;
            case "dark_gray":
                return EnumChatFormatting.DARK_GRAY;
            case "dark_green":
                return EnumChatFormatting.DARK_GREEN;
            case "dark_purple":
                return EnumChatFormatting.DARK_PURPLE;
            case "dark_red":
                return EnumChatFormatting.DARK_RED;
            case "gold":
                return EnumChatFormatting.GOLD;
            case "gray":
                return EnumChatFormatting.GRAY;
            case "green":
                return EnumChatFormatting.GREEN;
            case "light_purple":
                return EnumChatFormatting.LIGHT_PURPLE;
            case "red":
                return EnumChatFormatting.RED;
            case "yellow":
                return EnumChatFormatting.YELLOW;
            default:
                return EnumChatFormatting.WHITE;
        }

    }

    public static ArrayList<EnumChatFormatting> fromString(String[] colorString)
    {
        ArrayList<EnumChatFormatting> color = new ArrayList<>();
        for(String colors : colorString)
            color.add(fromString(colors));

        return color;
    }
}
