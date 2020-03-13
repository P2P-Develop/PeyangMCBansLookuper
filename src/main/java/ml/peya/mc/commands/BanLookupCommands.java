package ml.peya.mc.commands;

import ml.peya.mc.*;
import ml.peya.mc.netty.Border;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponent;

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
            Thread thread = new Thread(() -> {
                if(Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey))
                {
                    if (Border.get("https://api.mcbans.com/v3").startsWith("<html><head><title>500 Internal Server Error"))
                    {
                        Player.sendMessage(ChatBuilder.error(I18n.format("command.error.servernotf")), iCommandSender);
                        return;
                    }
                    BanLookupParserPlus lpp = Mcbans.banlookup(iCommandSender.getName(), PeyangMcBansLookuper.apikey, banIdsf, Mcbans.isEnableApiKey(PeyangMcBansLookuper.apikey));

                    BanLookupParserPlus.STATUS result = lpp.RESULT;
                    if (result == BanLookupParserPlus.STATUS.OK)
                    {
                        String admin = lpp.admin;
                        String type = lpp.type;
                        if (type.equals("global"))
                        {
                            type = ColorEnum.fromString("red") + I18n.format("ban.type.global");

                        }
                        else if(type.equals("local"))
                        {
                            type = ColorEnum.fromString("green") + I18n.format("ban.type.local");
                        }
                        else if(type.equals("temp"))
                        {
                            type = ColorEnum.fromString("light_purple") + I18n.format("ban.type.local");
                        }
                        Player.sendMessage(ChatBuilder.getInPrefix(), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + "#" + banIdsf), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reason"), PeyangMcBansLookuper.secondColor + lpp.reason.replace("ENDS", I18n.format("command.success.banlp.end"))), iCommandSender);
                        IChatComponent banning = new ChatComponentText(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.admin"), PeyangMcBansLookuper.secondColor + admin));
                        ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute PlayerLookup Command.")));
                        mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/lp " + admin));
                        banning.setChatStyle(mouse);

                        iCommandSender.addChatMessage(banning);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.server"), PeyangMcBansLookuper.secondColor + lpp.server), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.date"), PeyangMcBansLookuper.secondColor + lpp.date), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.type"), PeyangMcBansLookuper.secondColor + type), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reploss"), PeyangMcBansLookuper.secondColor + lpp.reploss), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""), iCommandSender);
                        Player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer.executiontime"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.executionTime)), iCommandSender);

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
