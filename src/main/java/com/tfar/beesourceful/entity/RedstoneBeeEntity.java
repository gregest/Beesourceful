package com.tfar.beesourceful.entity;

import com.tfar.beesourceful.BeeSourceful;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.IronBeeEntity;
import net.minecraft.item.Item;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class RedstoneBeeEntity extends IronBeeEntity {
  public RedstoneBeeEntity(EntityType<? extends BeeEntity> p_i225714_1_, World p_i225714_2_) {
    super(p_i225714_1_, p_i225714_2_);
  }

  public Block getOre(){
    return Blocks.REDSTONE_ORE;
  }

  public Block getAllowedHive() {
    return BeeSourceful.Objectholders.redstone_bee_nest;
  }

  @Nonnull
  public PointOfInterestType getHivePoi(){
    return BeeSourceful.Objectholders.POI.redstone_bee_nest;
  }

  public Item getHoneyComb(){
    return BeeSourceful.Objectholders.redstone_honeycomb;
  }
}
