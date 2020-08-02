package com.mactso.spawnerinlight.events;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.item.Items;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.GlowstoneBlobFeature;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MyPlaceEntityEvent {

    @SubscribeEvent()
    public void onPlaceBlock(BlockEvent.EntityPlaceEvent event) {
    	Entity entityPlaced = event.getEntity();
    	IWorld iWorld = event.getWorld();

    	if (iWorld instanceof ClientWorld) {
    		return;
    	}
    	// is player available?
    	
    	String name = entityPlaced.getCachedUniqueIdString();
    	BlockState placedBlockState = event.getPlacedBlock();
    	int blockLightValue = placedBlockState.getLightValue();
    	BlockPos placedBlockPos = event.getPos();
    	Block placedBlock = placedBlockState.getBlock();
    	
//    	// okay to place lights outside .
//    	if (iWorld.canSeeSky(placedBlockPos)) {
//    		return;
//    	}

    	if ((blockLightValue < 8)&&(placedBlock != Blocks.REDSTONE_LAMP)) {
    		return;
    	}  
    	
    	// okay to place lights in already bright areas.
    	int light = iWorld.getLight(placedBlockPos);

    	if (light==15) {
    		return;
    	}
    	
    	// placing dark light that's not a redstone lamp.
  	
    	if (SpawnerNearby(iWorld, placedBlockPos)) {
    		event.setCanceled(true);
    	}
    	
//    	boolean torch = (placedBlock == Blocks.TORCH);
//    	torch = torch | (placedBlock == Blocks.SEA_LANTERN);
//    	torch = torch | (placedBlock == Blocks.GLOWSTONE);    	
//    	torch = torch | (placedBlock == Blocks.REDSTONE_LAMP);
//    	torch = torch | (placedBlock == Blocks.NETHERRACK);
//    	torch = torch | (placedBlock == Blocks.LAVA);   
    	
    }
    
    private boolean SpawnerNearby (IWorld iWorld, BlockPos blockPos) {

    	int x = blockPos.getX();
    	int y = blockPos.getY();
    	int z = blockPos.getZ();
    	int dx, dy, dz;
    	// quick scan for spawner
    	
		for ( dy=-1;dy<2;dy++) {
			for( dx=-1;dx<2;dx++) {
				for( dz=-1;dz<2;dz++) {
					Block tempBlock = iWorld.getBlockState(new BlockPos (x+dx,y+dy,z+dz)).getBlock();
					if (tempBlock == Blocks.SPAWNER) {
						return true;
					}
				}
			}
		}
		// slower scan for spawner
    	for ( dy=-1;dy<3;dy++) {
			for( dx=-5;dx<5;dx++) {
				for( dz=-5;dz<5;dz++) {
					Block tempBlock = iWorld.getBlockState(new BlockPos (x+dx,y+dy,z+dz)).getBlock();
					if (tempBlock == Blocks.SPAWNER) {
						return true;
					}
				}
			}
		}
    	
    	return false;
    }
} 
