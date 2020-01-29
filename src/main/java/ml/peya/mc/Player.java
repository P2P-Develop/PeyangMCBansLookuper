package ml.peya.mc;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class Player
{
    public static void sendMessage(String context, ICommandSender iCommandSender)
    {
        iCommandSender.addChatMessage(new ChatComponentText(context));
    }
}
