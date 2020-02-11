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

    private ArrayList<String> aliases = new ArrayList<String>();

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
            Thread thread = new Thread()
            {
                public void run()
                {
                    String sendername = iCommandSender.getName();
                    String targetname = args[0];
                    boolean isenableapikey = Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey);
                    if(isenableapikey)
                    {
                        String isServernotFound = Border.get("https://api.mcbans.com/v3");
                        boolean isServernf = isServernotFound.startsWith("<html><head><title>500 Internal Server Error");
                        if (isServernf)
                        {
                            Player.sendMessage(ChatBuilder.error(I18n.format("command.error.servernotf")), iCommandSender);
                            return;
                        }
                        LookupParserPlus lpp = Mcbans.lookup(sendername, PeyangMcBansLookuper.apikey, targetname, isenableapikey);

                        LookupParserPlus.STATUS result = lpp.RESULT;
                        if (result == LookupParserPlus.STATUS.OK)
                        {
                            int totalbans = lpp.total;
                            double reputation = lpp.reputation;
                            ArrayList<String> localbans = lpp.local;
                            boolean localflag = !localbans.isEmpty();
                            ArrayList<String> globalbans = lpp.global;
                            boolean globalflag = !globalbans.isEmpty();
                            int processid = lpp.pid;
                            String uuid = lpp.uuid;
                            String mcid = lpp.player;
                            double datetime = lpp.executionTime;
                            Player.sendMessage(ChatBuilder.getInPrefix(), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + mcid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.totalbans"), PeyangMcBansLookuper.secondColor + String.valueOf(totalbans)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.reputation"), PeyangMcBansLookuper.secondColor + String.valueOf(reputation) + "/10.0"), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.playerlp.localbans"), localflag ? "" : PeyangMcBansLookuper.secondColor + "N/A"), iCommandSender);
                            if (localflag)
                            {
                                for (String local : localbans)
                                {
                                    McBansParser mcBansParser = McBansParser.parse(local);
                                    int id = mcBansParser.id;
                                    String ip = mcBansParser.ip;
                                    String reason = mcBansParser.reason;
                                    IChatComponent banning = new ChatComponentText(PeyangMcBansLookuper.banColor.get(0) + "#" + id);
                                    ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute BanLookup Command.")));
                                    mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/banlookup " + id));
                                    banning.setChatStyle(mouse);
                                    IChatComponent comps = new ChatComponentText(" " + PeyangMcBansLookuper.banColor.get(1) + ip + PeyangMcBansLookuper.banColor.get(2) + "    .:.    " + PeyangMcBansLookuper.banColor.get(3) + reason);
                                    banning.appendSibling(comps);
                                    iCommandSender.addChatMessage(banning);
                                }
                            }
                            {
                                for (String global : globalbans)
                                {
                                    McBansParser mcBansParser = McBansParser.parse(global);
                                    int id = mcBansParser.id;
                                    String ip = mcBansParser.ip;
                                    String reason = mcBansParser.reason;
                                    IChatComponent banning = new ChatComponentText(PeyangMcBansLookuper.banColor.get(0) + "#" + id);
                                    ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute BanLookup Command.")));
                                    mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/banlookup " + id));
                                    banning.setChatStyle(mouse);
                                    IChatComponent comps = new ChatComponentText(" " + PeyangMcBansLookuper.banColor.get(1) + ip + PeyangMcBansLookuper.banColor.get(2) + "    .:.    " + PeyangMcBansLookuper.banColor.get(3) + reason);
                                    banning.appendSibling(comps);
                                    iCommandSender.addChatMessage(banning);

                                }
                            }
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.processid"), EnumChatFormatting.DARK_AQUA + String.valueOf(processid)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.playerlp.uuid"), EnumChatFormatting.DARK_AQUA + uuid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.secondColor + I18n.format("command.success.developer.executiontime"), EnumChatFormatting.DARK_AQUA + String.valueOf(datetime)), iCommandSender);
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
                }

            };
            thread.start();
        }
        else
        {
            Player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)), iCommandSender);
        }
    }

    /*@Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        return true;
    }*/


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

    /*@Override
    public int compareTo(ICommand o)
    {
        return 0;
    }*/

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

}
