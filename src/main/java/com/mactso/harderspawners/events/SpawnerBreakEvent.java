package com.mactso.harderspawners.events;

import com.mactso.harderspawners.config.MyConfig;

import net.minecraft.block.SpawnerBlock;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SpawnerBreakEvent {
		static int spamLimiter = 0;
		@SubscribeEvent
		public void blockBreakSpeed(PlayerEvent.BreakSpeed event) {
	    	final int THREE_SECONDS = 60;
	    	final boolean SHOW_PARTICLES = true;
	    	if(event.getPlayer() == null) {
				return;
			} else if (event.getPlayer().isCreative()) {
				return;
			}
	    	
	    	if(!(event.getState().getBlock() instanceof SpawnerBlock)) {
	    		return;
	    	}

	    	// this runs on both sides.  
	    	// On the server to affect the real digging speed.
	    	// On the client to affect the apparent visual digging speed.
	    	String debugWorldName = "server-local ";
	    	PlayerEntity player = event.getPlayer();

	    	if (player.world.isRemote()) {
	   			debugWorldName = "client-remote ";
	   		} 
	   		
	    	Item playerItem = player.getHeldItemMainhand().getItem();
//	    	if (!playerItem.canHarvestBlock(p.getHeldItemMainhand(), event.getState())) {
//	    		return;
//	    	}
	    	boolean toolHarvestsBlockFaster = false;
	    	float originalToolSpeed = event.getOriginalSpeed();

	    	if (originalToolSpeed > 1.0f) {
	    		toolHarvestsBlockFaster = true;
	    	}
	    	
			// float baseDestroySpeed = playerItem.getDestroySpeed(p.getHeldItemMainhand(), s);
			int revengeLevel = MyConfig.spawnerRevengeLevel - 1;

			if (MyConfig.spawnerRevengeLevel>0) {
				if (player instanceof ServerPlayerEntity) {

		    		// This is tricky--- if the player has a more powerful effect, it sometimes
		    		// sticks "on" and won't expire so remove it once it has half a second left.
		 			EffectInstance ei = player.getActivePotionEffect(Effects.POISON);
		    		if (ei != null) {
		    			if (ei.getDuration() > 10) {
		    				return;
		    			}
		    			if ((ei.getDuration() < 1) || (ei.getAmplifier() > revengeLevel)) {
		    				player.removeActivePotionEffect(Effects.POISON );
		    			}
		    		}
					player.addPotionEffect(new EffectInstance(Effects.POISON, THREE_SECONDS, revengeLevel, true, SHOW_PARTICLES));
				}	
				if (MyConfig.debugLevel > 0) {
					System.out.println("Spawner Revenge applied.");
				}
			}			

	    	float baseDestroySpeed = event.getOriginalSpeed();
			float newDestroySpeed = baseDestroySpeed;


			if (MyConfig.spawnerBreakSpeedMultiplier>0) {
				newDestroySpeed = newDestroySpeed / (1 + MyConfig.spawnerBreakSpeedMultiplier);
				if (newDestroySpeed > 0) {
					event.setNewSpeed(newDestroySpeed);
					if (!(player instanceof ServerPlayerEntity)) {
						if ((spamLimiter++)%20 == 0) {
							MyConfig.sendChat(player, "The spawner slowly breaks...", TextFormatting.DARK_AQUA);
						}
					}
					if (MyConfig.debugLevel > 0) {
						System.out.println("Slowed breaking spawner modifier applied:" + MyConfig.spawnerBreakSpeedMultiplier + ".");
					}
				}
			}

		}
}

