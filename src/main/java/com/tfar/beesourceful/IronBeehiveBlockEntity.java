package com.tfar.beesourceful;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.IronBeeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class IronBeehiveBlockEntity extends BeehiveTileEntity {

  public Item honeycomb = Items.AIR;

  @Nonnull
  @Override
  public TileEntityType<?> getType() {
    return BeeSourceful.Objectholders.BlockEntities.iron_beehive;
  }

  public boolean releaseBee(BlockState state, CompoundNBT nbt, @Nullable List<Entity> entities, BeehiveTileEntity.State beehiveState) {
    BlockPos blockpos = this.getPos();
    if (shouldStayInHive(state,beehiveState)) {
      return false;
    } else {
      nbt.remove("Passengers");
      nbt.remove("Leash");
      nbt.removeUuid("UUID");
      Direction direction = state.get(BeehiveBlock.FACING);
      BlockPos blockpos1 = blockpos.offset(direction);
      if (!this.world.getBlockState(blockpos1).getCollisionShape(this.world, blockpos1).isEmpty()) {
        return false;
      } else {
        Entity entity = EntityType.func_220335_a(nbt, this.world, entity1 -> entity1);
        if (entity != null) {
          float f = entity.getWidth();
          double d0 = 0.55D + f / 2.0F;
          double d1 = blockpos.getX() + 0.5D + d0 * direction.getXOffset();
          double d2 = blockpos.getY() + 0.5D -  (entity.getHeight() / 2.0F);
          double d3 = blockpos.getZ() + 0.5D + d0 * direction.getZOffset();
          entity.setLocationAndAngles(d1, d2, d3, entity.rotationYaw, entity.rotationPitch);
          if (entity instanceof IronBeeEntity) {
            IronBeeEntity beeentity = (IronBeeEntity) entity;
            if (this.hasFlowerPos() && !beeentity.hasFlower() && this.world.rand.nextFloat() < 0.9F) {
              beeentity.setFlowerPos(this.flowerPos);
            }

            if (beehiveState == State.HONEY_DELIVERED) {
              beeentity.onHoneyDelivered();
              int i = getHoneyLevel(state);
              if (i < 5) {
                int j = this.world.rand.nextInt(100) == 0 ? 2 : 1;
                if (i + j > 5) {
                  --j;
                }
                this.honeycomb = beeentity.getHoneyComb();
                this.world.setBlockState(this.getPos(), state.with(BeehiveBlock.HONEY_LEVEL, i + j));
              }
            }
            beeentity.resetPollinationTicks();
            if (entities != null) {
              entities.add(beeentity);
            }
          }
          BlockPos hivePos = this.getPos();
          this.world.playSound(null, hivePos.getX(), hivePos.getY(),  hivePos.getZ(), SoundEvents.field_226132_ag_, SoundCategory.BLOCKS, 1.0F, 1.0F);
          return this.world.addEntity(entity);
        } else {
          return false;
        }
      }
    }
  }

  public boolean shouldStayInHive(BlockState state, State beehiveState){
    return (this.world.isNight() || this.world.isRaining()) && beehiveState != BeehiveTileEntity.State.EMERGENCY;
  }

  @Override
  public boolean isFullOfBees() {
    return bees.size() > 3;
  }

  public Item getResourceHoneyComb(){
    return honeycomb;
  }

  public boolean isAllowedBee(IronBeeEntity bee){
    Block hive = getBlockState().getBlock();
    return hive == BeeSourceful.Objectholders.iron_beehive || bee.getAllowedHive() == hive;
  }

  @Override
  public void read(CompoundNBT nbt) {
    super.read(nbt);
    this.honeycomb = ForgeRegistries.ITEMS.getValue(new ResourceLocation(nbt.getString("Honeycomb")));
  }

  @Nonnull
  @Override
  public CompoundNBT write(CompoundNBT nbt) {
    super.write(nbt);
    nbt.putString("Honeycomb",honeycomb.getRegistryName().toString());
    return nbt;
  }
}
