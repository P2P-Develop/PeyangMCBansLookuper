package ml.peya.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Events
{
    @SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
    @SideOnly(Side.CLIENT)
    public void onKeyEvent(InputEvent.KeyInputEvent e)
    {
        if (PeyangMcBansLookuper.lookupKeys.isPressed())
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (Facing.isFacingPlayer(mc))
            {
                String name = Facing.getFacingPlayerName(mc);
                if (!name.equals(""))
                {
                    ClientCommandHandler.instance.executeCommand(mc.thePlayer, "/lookup " + name);
                }
            }
        }
    }
}
