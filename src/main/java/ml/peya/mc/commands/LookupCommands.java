package ml.peya.mc.commands;

import ml.peya.mc.*;
import ml.peya.mc.netty.Border;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
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
                        if (Border.get("https://api.mcbans.com/v3").equals(""))
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
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.target"), EnumChatFormatting.BLUE + mcid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.totalbans"), EnumChatFormatting.BLUE + String.valueOf(totalbans)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.reputation"), EnumChatFormatting.BLUE + String.valueOf(reputation) + "/10.0"), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.localbans"), localflag ? "" : EnumChatFormatting.BLUE + "N/A"), iCommandSender);
                            if (localflag)
                            {
                                for (String local : localbans)
                                {
                                    McBansParser mcBansParser = McBansParser.parse(local);
                                    int id = mcBansParser.id;
                                    String ip = mcBansParser.ip;
                                    String reason = mcBansParser.reason;
                                    IChatComponent banning = new ChatComponentText(EnumChatFormatting.RED + "#" + id);
                                    ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcbans.com/ban/" + id));
                                    banning.setChatStyle(style);
                                    IChatComponent comps = new ChatComponentText(" " + EnumChatFormatting.LIGHT_PURPLE + ip + "   .:.   " + EnumChatFormatting.WHITE + reason);
                                    banning.appendSibling(comps);
                                    iCommandSender.addChatMessage(banning);
                                    //Player.sendMessage(ChatBuilder.getSectionInBans(1, EnumChatFormatting.RED + "#" + id, EnumChatFormatting.LIGHT_PURPLE + ip, EnumChatFormatting.WHITE + reason), iCommandSender);
                                }
                            }
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.globalbans"), globalflag ? "" : EnumChatFormatting.BLUE + "N/A"), iCommandSender);
                            if (globalflag)
                            {
                                for (String global : globalbans)
                                {
                                    McBansParser mcBansParser = McBansParser.parse(global);
                                    int id = mcBansParser.id;
                                    String ip = mcBansParser.ip;
                                    String reason = mcBansParser.reason;
                                    IChatComponent banning = new ChatComponentText(EnumChatFormatting.RED + "#" + id);
                                    ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "http://mcbans.com/ban/" + id));
                                    banning.setChatStyle(style);
                                    IChatComponent comps = new ChatComponentText(" " + EnumChatFormatting.LIGHT_PURPLE + ip + "   .:.   " + EnumChatFormatting.WHITE + reason);
                                    banning.appendSibling(comps);
                                    iCommandSender.addChatMessage(banning);
                                    //Player.sendMessage(ChatBuilder.getSectionInBans(1, EnumChatFormatting.RED + "#" + id, EnumChatFormatting.LIGHT_PURPLE + ip, EnumChatFormatting.WHITE + reason), iCommandSender);

                                }
                            }
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + I18n.format("command.success.developer"), ""), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + I18n.format("command.success.developer.processid"), EnumChatFormatting.DARK_AQUA + String.valueOf(processid)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + I18n.format("command.success.developer.uuid"), EnumChatFormatting.DARK_AQUA + uuid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + I18n.format("command.success.developer.datetime"), EnumChatFormatting.DARK_AQUA + String.valueOf(datetime)), iCommandSender);
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

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender iCommandSender)
    {
        return true;
    }


    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "/lookup <PlayerName>";
    }

    @Override
    public List<String> getCommandAliases()
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
