package ml.peya.mc.Commands;

import ml.peya.mc.*;
import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.Player.Player;
import ml.peya.mc.Request.PlayerLookupRequest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.*;

import java.util.ArrayList;
import java.util.List;

public class LookupCommands extends CommandBase
{

    private ArrayList<String> aliases = new ArrayList<>();

    public LookupCommands()
    {
        aliases.add("lookup");
        aliases.add("lp");
    }

    @Override
    public String getName()
    {
        return "lookup";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender iCommandSender, String[] args)
    {
        Player player = new Player(iCommandSender);
        if (args.length != 1)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getUsage(iCommandSender)));
            return;
        }
        final String targetPlayerName = args[0];
        final String senderPlayerName = player.getName();
        Thread thread = new Thread(() -> {
            ArrayList<ITextComponent> msgList = PlayerLookupRequest.lookup(senderPlayerName, targetPlayerName, PeyangMcBansLookuper.apikey);
            for (ITextComponent msg: msgList)
                player.sendMessage(msg);
        });
        thread.start();
    }



    @Override
    public String getUsage(ICommandSender iCommandSender)
    {
        return "/lookup <playername>";
    }



    @Override
    public List<String> getAliases()
    {
        return aliases;
    }


    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender iCommandSender, String[] args, BlockPos pos)
    {
        List<EntityPlayer> players = Minecraft.getMinecraft().world.playerEntities;
        ArrayList<String> playerNames = new ArrayList<>();
        for (EntityPlayer player: players)
            playerNames.add(player.getDisplayNameString());
        return playerNames;
    }
}
