package ml.peya.mc;

import ml.peya.mc.commands.LookupCommands;
import ml.peya.mc.exception.BadApikeyException;
import ml.peya.mc.exception.BadSideException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.logging.Logger;

@Mod(modid = PeyangMcBansLookuper.MOD_ID,
        name = PeyangMcBansLookuper.MOD_NAME,
        version = PeyangMcBansLookuper.MOD_VERSION,
        dependencies = PeyangMcBansLookuper.MOD_DEPENDENCIES,
        acceptedMinecraftVersions = PeyangMcBansLookuper.MOD_ACCEPTED_MC_VERSIONS,
        useMetadata = true)
public class PeyangMcBansLookuper
{
    public static final String MOD_ID = "PeyangMcbansLookuper";
    public static final String MOD_NAME = "PeyangMcbansLookuper";
    public static final String MOD_VERSION = "0.0.1";
    public static final String MOD_DEPENDENCIES = "required-after:Forge@[1.8-11.15.1.1722,)";
    public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.8,1.8.9]";
    public static KeyBinding lookupKeys;
    public static String apikey = "";
    public static String colours;
    public static Logger logger;
    public static String FirstColor;
    public static String SecondColor;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        if(e.getSide() == Side.SERVER)
        {
            System.out.println("This mod has not working in server.");
            Minecraft.getMinecraft().crashed(new CrashReport("This Mod(PML) Is Not Server Side Compatible.",
                    new BadSideException("This Mod(PML) Is Not Server Side Compatible. Require Client Side.")));
        }
        Configuration cfg = new Configuration(e.getSuggestedConfigurationFile());
        try
        {
            cfg.load();
            apikey = cfg.getString("apikey", "core", "edit required!", "The API key to MCBans is necessary for the use.");
            FirstColor = cfg.getString("name", "color", "blue", "Name Colors.");
            SecondColor = cfg.getString("value", "color", "blue", "Value Colors.");
        }
        finally
        {
            cfg.save();
        }

        if (apikey.equals("edit required!"))
        {
            Minecraft.getMinecraft().crashed(new CrashReport(I18n.format("command.error.apinotf"), new BadApikeyException((I18n.format("command.error.apinotf")))));
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(new Events());
        lookupKeys = new KeyBinding(I18n.format("main.init.success.keybind"), 38, "PeyangMcBansLookup");
        ClientRegistry.registerKeyBinding(lookupKeys);
        ClientCommandHandler.instance.registerCommand(new LookupCommands());
        logger = Logger.getLogger(MOD_ID);
    }



}
