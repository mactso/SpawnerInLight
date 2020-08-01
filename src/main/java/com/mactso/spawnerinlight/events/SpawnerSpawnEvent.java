package com.mactso.spawnerinlight.events;

import java.util.Random;

import com.mactso.spawnerinlight.config.MyConfig;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
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
    	if (event.getSpawnReason() != SpawnReason.SPAWNER) {
    		return;
    	}
    	
    	IWorld iWorld = event.getWorld();
    	// check that it's server world.
    	
    	System.out.println ("SpawnerInLight Checked Spawner Spawn Event.");
    	boolean spawnOk = false;

    	AbstractSpawner AbSp = event.getSpawner();

    	BlockPos AbSpPos = AbSp.getSpawnerPosition();

    	System.out.println ("SpawnerInLight: Event has a spawner.");

        LivingEntity le = (LivingEntity) event.getEntityLiving();

        // Spawner Spawns acceptable living Entities  (include list later)
        if (le instanceof PigEntity) {
        	return;
        }
        if (le instanceof CowEntity) {
            return;	
        }
        if (le instanceof SheepEntity) {
            return;
        }

        if (le instanceof BlazeEntity) {
            return;
        }
        
        System.out.println ("SpawnerInLight: Break / Explode Check.");

        Random chance = iWorld.getRandom();
        double next = 100.0 * chance.nextDouble();
        if (next < MyConfig.spawnersBreakPercentage) {
        	if (MyConfig.spawnersExplode) {
        		int flags = 3;  // Update Block- Tell Clients.
        		iWorld.setBlockState(AbSpPos, Blocks.TNT.getDefaultState(),flags);
        		iWorld.setBlockState(AbSpPos.down(), Blocks.REDSTONE_BLOCK.getDefaultState(), flags);
        	} else {
        		int flags = 3;  // Update Block- Tell Clients.
        		iWorld.setBlockState(AbSpPos, Blocks.AIR.getDefaultState(),flags);
        	}
        }
    }
}
