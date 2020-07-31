package com.mactso.spawnerinlight.events;

import java.util.Random;

import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.spawner.AbstractSpawner;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;


public class SpawnerSpawnEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onCheckSpawnerSpawn(LivingSpawnEvent.CheckSpawn event) {

//    	ASM code needed here:
//
//    	package net.minecraft.entity.monster;
//    	public static boolean canMonsterSpawnInLight(EntityType<? extends MonsterEntity> type, IWorld worldIn, SpawnReason reason, BlockPos pos, Random randomIn) {
//    	     return worldIn.getDifficulty() != Difficulty.PEACEFUL && isValidLightLevel(worldIn, pos, randomIn) && canSpawnOn(type, worldIn, reason, pos, randomIn);
//    	}
//
//      Look at beekeeper
//	    

    	int debugline = 7;
    	System.out.println ("SpawnerInLight Checked Spawner Spawn Event.");
    	boolean spawnOk = false;

    	AbstractSpawner AbSp = event.getSpawner();
    	if (AbSp == null) {
    		return;
    	}
    	System.out.println ("SpawnerInLight: Event has a spawner.");

    	System.out.println ("SpawnerInLight: Event status: " + event.getResult() );
    	
        LivingEntity le = (LivingEntity) event.getEntityLiving();
        if (event.getResult() == Event.Result.DENY) {
           	System.out.println ("SpawnerInLight: Event has Deny Status.");
        }
        if (event.getResult() == Event.Result.DEFAULT && le instanceof LivingEntity) {

            if (le.collidedHorizontally || le.collidedVertically) {
            	return;
            }

            // Tweaks pendingSpawners to prevent light from disabling spawns, except when the entity can see the sun
            if (event.getEntity() instanceof IMob) {
            	event.setResult(Event.Result.ALLOW);
            }    	
        }
    }
}
