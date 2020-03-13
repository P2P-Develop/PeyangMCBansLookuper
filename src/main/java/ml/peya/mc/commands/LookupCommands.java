package ml.peya.mc.commands;

import ml.peya.mc.*;
import ml.peya.mc.netty.Border;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
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
        if (args.length == 1)
        {
            Thread thread = new Thread(() -> {
                if(Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey))
                {
                    if (Border.get("https://api.mcbans.com/v3").startsWith("<html><head><title>500 Internal Server Error"))
                    {
                        Player.sendMessage(ChatBuilder.error(I18n.format("command.error.servernotf")), iCommandSender);
                        return;
                    }
                    LookupParserPlus lpp = Mcbans.lookup(iCommandSender.getName(), PeyangMcBansLookuper.apikey, args[0], Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey));

                    LookupParserPlus.STATUS result = lpp.RESULT;
                    if (result == LookupParserPlus.STATUS.OK)
                    {
                        Player.sendMessage(ChatBuilder.getInPrefix(), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + lpp.player), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.totalbans"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.total)), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.reputation"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.reputation) + "/10.0"), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.localbans"), !lpp.local.isEmpty() ? "" : PeyangMcBansLookuper.secondColor + "N/A"), iCommandSender);
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
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.processid"), ColorEnum.fromString("dark_aqua") + String.valueOf(lpp.pid)), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.playerlp.uuid"), ColorEnum.fromString("dark_aqua") + lpp.uuid), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.executiontime"), ColorEnum.fromString("dark_aqua") + String.valueOf(lpp.executionTime)), iCommandSender);
                    }
                    else
                    {
                        switch (result)
                        {
                            case I_AM_A_TEAPOT:
                                Player.sendMessage(ChatBuilder.error(I18n.format("command.error.j.teapot")), iCommandSender);
                                break;
                            case PLAYERNOTFOUND:
                                Player.sendMessage(ChatBuilder.error(I18n.format("command.error.notfoundplayer")), iCommandSender);
                                break;
                            case UNKNOWNERROR:
                                Player.sendMessage(ChatBuilder.error(I18n.format("command.error.unknown")), iCommandSender);
                                break;
                        }
                    }
                }
                else

                {
                    Player.sendMessage(ChatBuilder.error(I18n.format("command.error.apinotf")), iCommandSender);
                }
            });
            thread.start();
        }
        else
        {
            Player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)), iCommandSender);
        }
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

}
