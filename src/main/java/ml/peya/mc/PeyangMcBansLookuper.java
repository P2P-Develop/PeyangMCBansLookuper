package ml.peya.mc;

import ml.peya.mc.Chat.EnumColor;
import ml.peya.mc.Commands.BanLookupCommands;
import ml.peya.mc.Commands.LookupCommands;
import ml.peya.mc.Exception.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayList;
import java.util.logging.Logger;

@Mod(modid = PeyangMcBansLookuper.MOD_ID,
        name = PeyangMcBansLookuper.MOD_NAME,
        version = PeyangMcBansLookuper.MOD_VERSION,
        acceptedMinecraftVersions = PeyangMcBansLookuper.MOD_ACCEPTED_MC_VERSIONS,
        useMetadata = true)
public class PeyangMcBansLookuper
{
    public static final String MOD_ID = "peyangmcbanslookuper";
    public static final String MOD_NAME = "PeyangMcbansLookuper";
    public static final String MOD_VERSION = "1.1";
    public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.8,)";
    public static KeyBinding lookupKeys;
    public static String apikey = "";
    public static Logger logger;
    public static TextFormatting firstColor;
    public static TextFormatting secondColor;
    public static ArrayList<TextFormatting> banColor;
    @Mod.EventHandler
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
            firstColor = EnumColor.fromString(cfg.getString("firstColor", "color", "aqua", "Name Colors."));
            secondColor = EnumColor.fromString(cfg.getString("secondColor", "color", "blue", "Value Colors."));
            banColor = EnumColor.fromString(cfg.getStringList("BanColor", "color", new String[]{"red", "light_purple", "yellow", "white"}, "Ban Section Colors."));
        }
        finally
        {
            cfg.save();
        }

        if (apikey.equals("edit required!"))
            Minecraft.getMinecraft().crashed(new CrashReport(I18n.format("command.error.apinotf"), new BadApikeyException((I18n.format("command.error.apinotf")))));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        lookupKeys = new KeyBinding(I18n.format("main.init.success.keybind"), 38, "PeyangMcBansLookup");
        MinecraftForge.EVENT_BUS.register(new Events());
        ClientRegistry.registerKeyBinding(lookupKeys);
        ClientCommandHandler.instance.registerCommand(new LookupCommands());
        ClientCommandHandler.instance.registerCommand(new BanLookupCommands());
        logger = Logger.getLogger(MOD_ID);
    }
}
