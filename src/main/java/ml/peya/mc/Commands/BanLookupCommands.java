package ml.peya.mc.Commands;

import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.PeyangMcBansLookuper;
import ml.peya.mc.Player.Player;
import ml.peya.mc.Request.BanLookupRequest;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.ITextComponent;

import java.util.ArrayList;
import java.util.List;

public class BanLookupCommands extends CommandBase
{
    private ArrayList<String> aliases = new ArrayList<>();

    public BanLookupCommands()
    {
        aliases.add("banlookup");
        aliases.add("lb");
    }

    @Override
    public String getName()
    {
        return "banlookup";
    }

    @Override
    public void execute(MinecraftServer server, final ICommandSender iCommandSender, final String[] args)
    {
        Player player = new Player(iCommandSender);
        if (args.length != 1)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getUsage(iCommandSender)));
            return;
        }
        String id = args[0];
        if (id.startsWith("#"))
            id = id.substring(2);

        int banIds;
        try
        {
            banIds = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getUsage(iCommandSender)));
            return;
        }

        final int banId = banIds;
        Thread thread = new Thread(() -> {
            ArrayList<ITextComponent> response = BanLookupRequest.lookup(player.getName(), banId, PeyangMcBansLookuper.apikey);
            for (ITextComponent msg: response)
                player.sendMessage(msg);
        });
        thread.start();
    }

    @Override
    public String getUsage(ICommandSender iCommandSender)
    {
        return "/banlookup <banid>";
    }

    @Override
    public List<String> getAliases()
    {
        return aliases;
    }

    @Override
    public int compareTo(ICommand o)
    {
        return 0;
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}
