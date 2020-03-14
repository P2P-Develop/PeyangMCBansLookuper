package ml.peya.mc.Player;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import scala.reflect.internal.Trees;

public class Player
{
    ICommandSender player;
    public Player(ICommandSender sender)
    {
        this.player = sender;
    }

    public void sendMessage(String context)
    {
        player.addChatMessage(new ChatComponentText(context));
    }
}
