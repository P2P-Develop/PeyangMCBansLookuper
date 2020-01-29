package ml.peya.mc.commands;

import ml.peya.mc.*;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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

                    boolean isenableaapikey = Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey);

                    if(isenableaapikey)
                    {
                        LookupPerserPlus lpp = Mcbans.lookup(sendername, PeyangMcBansLookuper.apikey, targetname, isenableaapikey);

                        LookupPerserPlus.STATUS result = lpp.RESULT;
                        if (result == LookupPerserPlus.STATUS.OK)
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
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "Target", EnumChatFormatting.BLUE + mcid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "TotalBas", EnumChatFormatting.BLUE + String.valueOf(totalbans)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "Reputation", EnumChatFormatting.BLUE + String.valueOf(reputation) + "/10.0"), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "LocalBans", localflag ? "" : EnumChatFormatting.BLUE + "N/A"), iCommandSender);
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
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "GlobalBans", globalflag ? "\n" : EnumChatFormatting.BLUE + "N/A"), iCommandSender);
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
                            Player.sendMessage(ChatBuilder.getPrefix(0, EnumChatFormatting.AQUA + "Developer", "\n"), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + "ProcessID", EnumChatFormatting.DARK_AQUA + String.valueOf(processid)), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + "UniversallyUniqueID", EnumChatFormatting.DARK_AQUA + uuid), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, EnumChatFormatting.BLUE + "DateTime", EnumChatFormatting.DARK_AQUA + String.valueOf(datetime)), iCommandSender);
                        }
                        else
                        {
                            switch (result)
                            {
                                case I_AM_A_TEAPOT:
                                    Player.sendMessage(ChatBuilder.error("E: I'am a teapot."), iCommandSender);
                                    break;
                                case PLAYERNOTFOUND:
                                    Player.sendMessage(ChatBuilder.error("E: Player Not Found."), iCommandSender);
                                    break;
                                case UNKNOWNERROR:
                                    Player.sendMessage(ChatBuilder.error("E: Unknown Error."), iCommandSender);
                                    break;
                                case UNKNOWNHOST:
                                    Player.sendMessage(ChatBuilder.error("E: Mcbans Server Is Not Working."), iCommandSender);
                                    break;
                            }
                        }
                    }
                    else

                    {
                        Player.sendMessage(ChatBuilder.error("E: ApiKey has Not Found."), iCommandSender);
                    }
                }

            };
            thread.start();
        }
        else
        {
            Player.sendMessage(ChatBuilder.error("E: usage: " + getCommandUsage(iCommandSender)), iCommandSender);
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
