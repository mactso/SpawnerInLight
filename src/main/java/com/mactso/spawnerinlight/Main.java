// 15.2 - 1.0.0.0 Villager Respawn
package com.mactso.spawnerinlight;


import com.mactso.spawnerinlight.config.MyConfig;
import com.mactso.spawnerinlight.events.SpawnerSpawnEvent;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("spawnerinlight")
public class Main {

	    public static final String MODID = "spawnerinlight"; 
	    
	    public Main()
	    {

			FMLJavaModLoadingContext.get().getModEventBus().register(this);
	        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,MyConfig.SERVER_SPEC );
	    }

	    @Mod.EventBusSubscriber()
	    public static class ForgeEvents
	    {
			@SubscribeEvent 
			public static void preInit (final FMLServerStartingEvent  event) {
				System.out.println("Spawner In Light: Registering Handler");
				MinecraftForge.EVENT_BUS.register(new SpawnerSpawnEvent());
				
			}       
	    
	    }

}
