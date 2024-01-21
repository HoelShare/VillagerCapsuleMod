package dev.hoelshare.villagercapsulemod.util;

import dev.hoelshare.villagercapsulemod.VillagerCapsuleMod;
import net.minecraft.item.Item;

public class RegistryUtil {
    public static Item setItemName(Item item, String name) {
        item.setRegistryName(VillagerCapsuleMod.MODID, name);
        item.setUnlocalizedName(VillagerCapsuleMod.MODID + "." + name);
        return item;
    }
}
