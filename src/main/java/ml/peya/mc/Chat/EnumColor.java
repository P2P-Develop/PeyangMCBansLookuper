package ml.peya.mc.Chat;

import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class EnumColor
{
    public static TextFormatting fromString (String colorString)
    {
        switch (colorString)
        {
            case "aqua":
                return TextFormatting.AQUA;
            case "black":
                return TextFormatting.BLACK;
            case "blue":
                return TextFormatting.BLUE;
            case "dark_aqua":
                return TextFormatting.DARK_AQUA;
            case "dark_blue":
                return TextFormatting.DARK_BLUE;
            case "dark_gray":
                return TextFormatting.DARK_GRAY;
            case "dark_green":
                return TextFormatting.DARK_GREEN;
            case "dark_purple":
                return TextFormatting.DARK_PURPLE;
            case "dark_red":
                return TextFormatting.DARK_RED;
            case "gold":
                return TextFormatting.GOLD;
            case "gray":
                return TextFormatting.GRAY;
            case "green":
                return TextFormatting.GREEN;
            case "light_purple":
                return TextFormatting.LIGHT_PURPLE;
            case "red":
                return TextFormatting.RED;
            case "yellow":
                return TextFormatting.YELLOW;
            default:
                return TextFormatting.WHITE;
        }

    }

    public static ArrayList<TextFormatting> fromString(String[] colorString)
    {
        ArrayList<TextFormatting> color = new ArrayList<>();
        for(String colors : colorString)
            color.add(fromString(colors));

        return color;
    }
}
