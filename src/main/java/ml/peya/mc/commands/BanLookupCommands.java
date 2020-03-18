package ml.peya.mc.commands;

import ml.peya.mc.*;
import ml.peya.mc.Chat.ChatBuilder;
import ml.peya.mc.Chat.ColorEnum;
import ml.peya.mc.Parser.BanLookupParserPlus;
import ml.peya.mc.Player.Player;
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
        Player player = new Player(iCommandSender);
        if (args.length != 1)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)));
            return;
        }
        String id = args[0];
        if (id.startsWith("#"))
            id = id.substring(2);

        int banIds = 0;
        try
        {
            banIds = Integer.parseInt(id);
        }
        catch (Exception e)
        {
            player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage") + getCommandUsage(iCommandSender)));
            return;
        }

        final int banIdsf = banIds;
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
            BanLookupParserPlus lpp = McBans.banlookup(iCommandSender.getName(), PeyangMcBansLookuper.apikey, banIdsf, McBans.isEnableApiKey(PeyangMcBansLookuper.apikey));

            BanLookupParserPlus.STATUS result = lpp.RESULT;
            if (result == BanLookupParserPlus.STATUS.OK)
            {
                String admin = lpp.admin;
                String type = lpp.type;
                switch (type)
                {
                    case "global":
                        type = ColorEnum.fromString("red") + I18n.format("ban.type.global");
                        break;
                    case "local":
                        type = ColorEnum.fromString("green") + I18n.format("ban.type.local");
                        break;
                    case "temp":
                        type = ColorEnum.fromString("light_purple") + I18n.format("ban.type.local");
                        break;
                }
                player.sendMessage(ChatBuilder.getInPrefix());
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.target"), PeyangMcBansLookuper.secondColor + "#" + banIdsf));
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reason"), PeyangMcBansLookuper.secondColor + lpp.reason.replace("ENDS", I18n.format("command.success.banlp.end"))));
                IChatComponent banning = new ChatComponentText(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.admin"), PeyangMcBansLookuper.secondColor + admin));
                ChatStyle mouse = new ChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to execute PlayerLookup Command.")));
                mouse.setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/lp " + admin));
                banning.setChatStyle(mouse);
                iCommandSender.addChatMessage(banning);
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.server"), PeyangMcBansLookuper.secondColor + lpp.server));
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.date"), PeyangMcBansLookuper.secondColor + lpp.date));
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.type"), PeyangMcBansLookuper.secondColor + type));
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.banlp.reploss"), PeyangMcBansLookuper.secondColor + lpp.reploss));
                player.sendMessage(ChatBuilder.getPrefix(0, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer"), ""));
                player.sendMessage(ChatBuilder.getPrefix(1, PeyangMcBansLookuper.firstColor + I18n.format("command.success.developer.executiontime"), PeyangMcBansLookuper.secondColor + String.valueOf(lpp.executionTime)));

            }

        });
        thread.start();
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
