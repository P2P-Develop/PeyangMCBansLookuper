package ml.peya.mc.commands;

import com.sun.org.apache.bcel.internal.generic.I2D;
import ml.peya.mc.*;
import ml.peya.mc.netty.BodyElement;
import ml.peya.mc.netty.Border;
import net.minecraft.client.resources.I18n;
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

public class BanLookupCommands extends CommandBase
{
    //意味のない変数があると思う。
    //それは,McbansAPIの負担を減らすためである。
    //2度以上、同じ内容でAPIサーバに接続するのであれば
    //変数化してほしい。

    private ArrayList<String> aliases = new ArrayList<String>();

    public BanLookupCommands()
    {
        aliases.add("banlookup");
        aliases.add("lb");
    }

    @Override
    public String getCommandName()
    {
        return "banlookup";
    }

    @Override
    public void processCommand(final ICommandSender iCommandSender, final String[] args)
    {
        if (args.length == 1)
        {
            String id = args[0];
            if (id.startsWith("#"))
            {
                id = id.substring(2);
            }

            int banIds = 0;
            try
            {
                banIds = Integer.parseInt(id);
            }
            catch (Exception e)
            {
                Player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)), iCommandSender);
            }

            final int banIdsf = banIds;
            Thread thread = new Thread()
            {
                public void run()
                {
                    String sendername = iCommandSender.getName();
                    boolean isenableapikey = Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey);
                    if(isenableapikey)
                    {
                        String isServernotFound = Border.get("https://api.mcbans.com/v3");
                        if (isServernotFound.startsWith("<html><head><title>500 Internal Server Error"))
                        {
                            Player.sendMessage(ChatBuilder.error(I18n.format("command.error.servernotf")), iCommandSender);
                            return;
                        }
                        BanLookupParserPlus lpp = Mcbans.banlookup(sendername, PeyangMcBansLookuper.apikey, banIdsf, isenableapikey);

                        BanLookupParserPlus.STATUS result = lpp.RESULT;
                        if (result == BanLookupParserPlus.STATUS.OK)
                        {
                            String player = lpp.player;
                            String reason = lpp.reason;
                            String admin = lpp.admin;
                            String server = lpp.server;
                            String date = lpp.date;
                            String type = lpp.type;
                            String reploss = lpp.reploss;
                            double executionTime = lpp.executionTime;
                            if (type.equals("global"))
                            {
                                type = EnumChatFormatting.RED + I18n.format("ban.type.global");

                            }
                            else if(type.equals("local"))
                            {
                                type = EnumChatFormatting.GREEN + I18n.format("ban.type.local");
                            }
                            else if(type.equals("temp"))
                            {
                                type = EnumChatFormatting.LIGHT_PURPLE + I18n.format("ban.type.local");
                            }
                            Player.sendMessage(ChatBuilder.getInPrefix(), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + "#" + banIdsf), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reason"), PeyangMcBansLookuper.secondColor + reason.replace("ENDS", I18n.format("command.success.banlp.end"))), iCommandSender);
                            IChatComponent banning = new ChatComponentText(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.admin"), PeyangMcBansLookuper.secondColor + admin));
                            ChatStyle style = new ChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/lp " + admin));
                            banning.setChatStyle(style);
                            iCommandSender.addChatMessage(banning);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.server"), PeyangMcBansLookuper.secondColor + server), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.date"), PeyangMcBansLookuper.secondColor + date), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.type"), PeyangMcBansLookuper.secondColor + type), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reploss"), PeyangMcBansLookuper.secondColor + reploss), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""), iCommandSender);
                            Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer.executiontime"), PeyangMcBansLookuper.secondColor + String.valueOf(executionTime)), iCommandSender);

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
        return "/banlookup <banid>";
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
