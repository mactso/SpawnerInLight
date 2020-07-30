package com.mactso.spawnerinlight.events;

import net.minecraft.block.SpawnerBlock;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;



public class SpawnerSpawnEvent {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onCheckSpawnerSpawn(LivingSpawnEvent.CheckSpawn event) {

    	int debugline = 7;
    	System.out.println ("SpawnerInLight Checked Spawner Spawn Event.");
    }
}
