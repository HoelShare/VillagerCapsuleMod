package dev.hoelshare.villagercapsulemod;

import dev.hoelshare.villagercapsulemod.item.VillagerCapsuleItem;
import dev.hoelshare.villagercapsulemod.util.RegistryUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = VillagerCapsuleMod.MODID)
public class RegistrationHandler {
    @SubscribeEvent
    public static void registerItems(Register<Item> event) {
        final Item[] items = {
            RegistryUtil.setItemName(new VillagerCapsuleItem(false), "villager_capsule_empty").setCreativeTab(CreativeTabs.TRANSPORTATION),
            RegistryUtil.setItemName(new VillagerCapsuleItem(true), "villager_capsule_filled").setCreativeTab(CreativeTabs.TRANSPORTATION),
        };

        event.getRegistry().registerAll(items);
    }
}
