package dev.hoelshare.villagercapsulemod.item;

import java.util.List;

import dev.hoelshare.villagercapsulemod.entity.EntityVillagerCapsule;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VillagerCapsuleItem extends Item {
    private final boolean containsVillager;

    public VillagerCapsuleItem(boolean containsVillager) {
        super();
        this.setMaxStackSize(1);
        this.setMaxDamage(0);
        this.containsVillager = containsVillager;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
            EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemStack = player.getHeldItem(hand);

        if (worldIn.isRemote) {
            return EnumActionResult.SUCCESS;
        } else if (!player.canPlayerEdit(pos.offset(facing), facing, itemStack) || !this.containsVillager) {
            return EnumActionResult.FAIL;
        } else {
                BlockPos blockpos = pos.offset(facing);
                double d0 = this.getYOffset(worldIn, blockpos);

                Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation("villager"),
                        (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + d0,
                        (double) blockpos.getZ() + 0.5D);

                if (entity != null) {
                    if (entity instanceof EntityLivingBase && itemStack.hasDisplayName()) {
                        entity.setCustomNameTag(itemStack.getDisplayName());
                    }

                    ItemMonsterPlacer.applyItemEntityDataToEntity(worldIn, player, itemStack, entity);

                    if (!player.capabilities.isCreativeMode) {
                        itemStack.shrink(1);
                    }
                }
        }
        return EnumActionResult.SUCCESS;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (this.containsVillager) {
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
        }

        if (!playerIn.capabilities.isCreativeMode) {
            itemstack.shrink(1);
        }

        worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ,
                SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote) {
            if (containsVillager) {
                BlockPos blockpos = playerIn.getPosition();
                ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation("villager"),
                        (double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D,
                        (double) blockpos.getZ() + 0.5D);
            } else {
                EntityVillagerCapsule entityCapsule = new EntityVillagerCapsule(worldIn, playerIn, this.containsVillager);
                entityCapsule.shoot(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, 0.0F, 1.5F, 1.0F);
                worldIn.spawnEntity(entityCapsule);
            }
        }

        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

    protected double getYOffset(World p_190909_1_, BlockPos p_190909_2_) {
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(p_190909_2_)).expand(0.0D, -1.0D, 0.0D);
        List<AxisAlignedBB> list = p_190909_1_.getCollisionBoxes((Entity) null, axisalignedbb);

        if (list.isEmpty()) {
            return 0.0D;
        } else {
            double d0 = axisalignedbb.minY;

            for (AxisAlignedBB axisalignedbb1 : list) {
                d0 = Math.max(axisalignedbb1.maxY, d0);
            }

            return d0 - (double) p_190909_2_.getY();
        }
    }

}
