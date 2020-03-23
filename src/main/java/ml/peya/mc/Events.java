package ml.peya.mc;

import ml.peya.mc.Player.Facing;
import net.minecraft.client.Minecraft;
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
        Minecraft mc = Minecraft.getMinecraft();
        if (!PeyangMcBansLookuper.lookupKeys.isPressed() || !Facing.isFacingPlayer(mc))
            return;
        String name = Facing.getFacingPlayerName(mc);
        if (!name.equals(""))
            ClientCommandHandler.instance.executeCommand(mc.thePlayer, "/lookup " + name);
    }
}
