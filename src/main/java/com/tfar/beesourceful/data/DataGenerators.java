package com.tfar.beesourceful.data;

import com.tfar.beesourceful.data.provider.ModBlockstateProvider;
import com.tfar.beesourceful.data.provider.ModItemModelProvider;
import com.tfar.beesourceful.data.provider.ModLootTableProvider;
import com.tfar.beesourceful.data.provider.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber (bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
  @SubscribeEvent
  public static void getherdata(GatherDataEvent e){
    DataGenerator gen = e.getGenerator();
    ExistingFileHelper helper = e.getExistingFileHelper();

    if (e.includeClient()){
      gen.addProvider(new ModBlockstateProvider(gen,helper));
      gen.addProvider(new ModItemModelProvider(gen,helper));
    }
    if (e.includeServer()){
      gen.addProvider(new ModLootTableProvider(gen));
      gen.addProvider(new ModRecipeProvider(gen));
    }
  }
}
