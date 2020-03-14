package ml.peya.mc.commands;

import ml.peya.mc.*;
import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.Chat.ColorEnum;
import ml.peya.mc.Parser.LookupParserPlus;
import ml.peya.mc.Parser.McBansParser;
import ml.peya.mc.Player.Player;
import ml.peya.mc.netty.Border;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.*;

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
    public String getCommandName()
    {
        return "lookup";
    }

    @Override
    public void processCommand(final ICommandSender iCommandSender, final String[] args)
    {
        Player player = new Player(iCommandSender);
        if (args.length != 1)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)));
            return;
        }
        Thread thread = new Thread(() -> {
            if(!McBans.isEnableApiKey(PeyangMcBansLookuper.apikey))
            {
                player.sendMessage(ChatBuilder.error(I18n.format("command.error.apinotf")));
                return;
            }

            if (Border.get("https://api.mcbans.com/v3").startsWith("<html><head><title>500 Internal Server Error"))
            {
                player.sendMessage(ChatBuilder.error(I18n.format("command.error.servernotf")));
                return;
            }

            LookupParserPlus lpp = McBans.lookup(iCommandSender.getName(), PeyangMcBansLookuper.apikey, args[0], McBans.isEnableApiKey(PeyangMcBansLookuper.apikey));

            LookupParserPlus.STATUS result = lpp.RESULT;
            if (result != LookupParserPlus.STATUS.OK)
            {
                switch (result)
                {
                    case I_AM_A_TEAPOT:
                        player.sendMessage(ChatBuilder.error(I18n.format("command.error.j.teapot")));
                        break;
                    case PLAYERNOTFOUND:
                        player.sendMessage(ChatBuilder.error(I18n.format("command.error.notfoundplayer")));
                        break;
                    case UNKNOWNERROR:
                        player.sendMessage(ChatBuilder.error(I18n.format("command.error.unknown")));
                        break;
                }
            }
            player.sendMessage(ChatBuilder.getInPrefix());
            player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + lpp.player));
            player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.totalbans"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.total)));
            player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.reputation"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.reputation) + "/10.0"));
            player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.localbans"), !lpp.local.isEmpty() ? "" : PeyangMcBansLookuper.secondColor + "N/A"));
            if (!lpp.local.isEmpty())
            {
                for (String local : lpp.local)
                {
                    McBansParser mcBansParser = McBansParser.parse(local);
                    int id = mcBansParser.id;
                    IChatComponent banning = new ChatComponentText(PeyangMcBansLookuper.banColor.get(0) + "#" + id);
                    ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute BanLookup Command.")));
                    mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/banlookup " + id));
                    banning.setChatStyle(mouse);
                    banning.appendSibling(new ChatComponentText(" " + PeyangMcBansLookuper.banColor.get(1) + mcBansParser.ip + PeyangMcBansLookuper.banColor.get(2) + "    .:.    " + PeyangMcBansLookuper.banColor.get(3) + mcBansParser.reason));
                    iCommandSender.addChatMessage(banning);
                }
            }
            if (!lpp.global.isEmpty())
            {
                for (String global : lpp.global)
                {
                    McBansParser mcBansParser = McBansParser.parse(global);
                    int id = mcBansParser.id;
                    IChatComponent banning = new ChatComponentText(PeyangMcBansLookuper.banColor.get(0) + "#" + id);
                    ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute BanLookup Command.")));
                    mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/banlookup " + id));
                    banning.setChatStyle(mouse);
                    banning.appendSibling(new ChatComponentText(" " + PeyangMcBansLookuper.banColor.get(1) + mcBansParser.ip + PeyangMcBansLookuper.banColor.get(2) + "    .:.    " + PeyangMcBansLookuper.banColor.get(3) + mcBansParser.reason));
                    iCommandSender.addChatMessage(banning);

                }
            }
            player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""));
            player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.processid"), ColorEnum.fromString("dark_aqua") + String.valueOf(lpp.pid)));
            player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.playerlp.uuid"), ColorEnum.fromString("dark_aqua") + lpp.uuid));
            player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.executiontime"), ColorEnum.fromString("dark_aqua") + String.valueOf(lpp.executionTime)));
        });
        thread.start();
    }



    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/lookup <playername>";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return aliases;
    }


    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender iCommandSender, String[] p_addTabCompletionOptions_2_, BlockPos p_addTabCompletionOptions_3_)
    {
        List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
        ArrayList<String> playerNames = new ArrayList<>();
        for (EntityPlayer player: players)
            playerNames.add(player.getDisplayNameString());
        return playerNames;
    }
}
