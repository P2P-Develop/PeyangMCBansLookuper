package ml.peya.mc;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Player
{
    public static void sendMessage(String context, ICommandSender iCommandSender)
    {
        ChatComponentText ccp = new ChatComponentText(context);
        iCommandSender.addChatMessage(ccp);
    }
}
