package ml.peya.mc.Player;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class Player implements ICommandSender
{
    ICommandSender player;
    public Player(ICommandSender sender)
    {
        this.player = sender;
    }

    public void sendMessage(String context)
    {
        this.sendMessage(new TextComponentString(context));
    }

    @Override
    public String getName()
    {
        return player.getName();
    }

    public void sendMessage(ITextComponent context)
    {
        player.sendMessage(context);
    }

    @Override
    public boolean canUseCommand(int i, String s)
    {
        return player.canUseCommand(i, s);
    }

    @Override
    public World getEntityWorld()
    {
        return player.getEntityWorld();
    }

    @Override
    public MinecraftServer getServer()
    {
        return player.getServer();
    }
}
