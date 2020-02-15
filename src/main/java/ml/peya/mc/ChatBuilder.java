package ml.peya.mc;


public class ChatBuilder
{
    public static String error(String message)
    {
        return getInPrefix() + "\n" +
                ColorEnum.fromString("red") + "     " + message;
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
        return ColorEnum.fromString("aqua") + "-------" + ColorEnum.fromString("blue") + "PeyangMCBansLookuper" + ColorEnum.fromString("aqua") + "-------";
    }

}
