package ml.peya.mc;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class Facing
{
    public static boolean isFacingPlayer(Minecraft mc)
    {
        if (mc.objectMouseOver.entityHit == null)
        {
            return false;
        }

        return mc.objectMouseOver.entityHit instanceof EntityPlayer;
    }

    public static String getFacingPlayerName(Minecraft mc)
    {
        if (isFacingPlayer(mc))
        {
            EntityPlayer entityPlayer = (EntityPlayer) mc.objectMouseOver.entityHit;
            return entityPlayer.getDisplayNameString();
        }
        else
        {
            return "";
        }
    }
}
