package ml.peya.mc.commands;

import ml.peya.mc.*;
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

public class CoreCommands extends CommandBase
{

    private ArrayList<String> aliases = new ArrayList<String>();

    public CoreCommands()
    {
        aliases.add("lookupcommand");
        aliases.add("lmp");
    }

    @Override
    public String getCommandName()
    {
        return "lookupcommand";
    }

    @Override
    public void processCommand(final ICommandSender iCommandSender, final String[] args)
    {
        if (args.length <= 1)
        {

        }
        else
        {
            Player.sendMessage(ChatBuilder.error(I18n.format("command.error.usage" + getCommandUsage(iCommandSender))), iCommandSender);
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
        return "/lookupercommand(lmp) <Execution>";
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
