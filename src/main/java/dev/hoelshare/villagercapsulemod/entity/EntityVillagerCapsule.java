package dev.hoelshare.villagercapsulemod.entity;

import dev.hoelshare.villagercapsulemod.init.ModItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityVillagerCapsule extends EntityPotion {
    private final boolean containsVillager;

    public EntityVillagerCapsule(World worldIn, EntityLivingBase throwerIn, boolean containsVillager)
    {
        super(worldIn, throwerIn, new ItemStack(Items.LINGERING_POTION));
        
        this.containsVillager = containsVillager;
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 3)
        {
            for (int i = 0; i < 8; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    protected void onImpact(RayTraceResult result) {
        if (result.entityHit != null) {
            boolean flagVillager = result.entityHit instanceof EntityVillager;
            boolean flagAttack = !flagVillager
                    || (this.containsVillager && result.entityHit instanceof EntityLivingBase);

            if (flagAttack) {
                result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
            }

            if (flagVillager && !this.containsVillager) {
                EntityVillager villager = (EntityVillager) result.entityHit;
                villager.dropItem(ModItem.VILLAGER_CAPSULE_FILLED, 1);
                this.world.removeEntity(villager);
                villager.setDead();
            }
        }
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
    }
}
