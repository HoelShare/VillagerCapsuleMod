package dev.hoelshare.villagercapsulemod;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = VillagerCapsuleMod.MODID, name = VillagerCapsuleMod.NAME, version = VillagerCapsuleMod.VERSION)
public class VillagerCapsuleMod
{
    public static final String MODID = "villagercapsulemod";
    public static final String NAME = "Villager Capsule Mod";
    public static final String VERSION = "1.0";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("Init Villager Capsule Mod");
    }
}
