package dev.hoelshare.villagercapsulemod.client;

import dev.hoelshare.villagercapsulemod.VillagerCapsuleMod;
import dev.hoelshare.villagercapsulemod.init.ModItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@EventBusSubscriber(value = Side.CLIENT, modid = VillagerCapsuleMod.MODID)
public class ModelRegistrationHandler {
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerModel(ModItem.VILLAGER_CAPSULE_EMPTY, 0);
        registerModel(ModItem.VILLAGER_CAPSULE_FILLED, 0);
    }

    private static void registerModel(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta,
                 new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
