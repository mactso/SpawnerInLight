package com.mactso.spawnerinlight.config;

	import org.apache.commons.lang3.tuple.Pair;
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

	import com.mactso.spawnerinlight.Main;

	import net.minecraftforge.common.ForgeConfigSpec;
	import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
	import net.minecraftforge.common.ForgeConfigSpec.IntValue;
	import net.minecraftforge.eventbus.api.SubscribeEvent;
	import net.minecraftforge.fml.common.Mod;
	import net.minecraftforge.fml.config.ModConfig;

	@Mod.EventBusSubscriber(modid = Main.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)

	public class MyConfig
	{
		private static final Logger LOGGER = LogManager.getLogger();
		public static final Server SERVER;
		public static final ForgeConfigSpec SERVER_SPEC;
		static
		{
			final Pair<Server, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Server::new);
			SERVER_SPEC = specPair.getRight();
			SERVER = specPair.getLeft();
		}

		public static int debugLevel;
		public static double spawnersExplodePercentage;
		public static String[]  defaultMobBreakPercentageValues;
		public static String    defaultMobBreakPercentageValues6464;
		
		@SubscribeEvent
		public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent)
		{
			if (configEvent.getConfig().getSpec() == MyConfig.SERVER_SPEC)
			{
				bakeConfig();
				MobSpawnerBreakPercentageItemManager.mobBreakPercentageInit();
			}
		}

		public static void pushDebugValue() {
			if (debugLevel > 0) {
				System.out.println("dbgL:"+MyConfig.debugLevel);
			}
			SERVER.debugLevel.set( MyConfig.debugLevel);
		}

		public static void pushSpawnersExplodePercentage() {
			if (debugLevel > 0) {
				System.out.println("dbgL:"+MyConfig.spawnersExplodePercentage);
			}
			SERVER.spawnersExplodePercentage.set( MyConfig.spawnersExplodePercentage);
		}
		
		public static void bakeConfig()
		{
			debugLevel = SERVER.debugLevel.get();
			spawnersExplodePercentage = SERVER.spawnersExplodePercentage.get();
			defaultMobBreakPercentageValues6464 = SERVER.defaultNoBreakMobsActual.get() ;
			if (debugLevel > 0) {
				System.out.println("Harder Spawners Debug: " + debugLevel );
			}
		}

		
		
		public static class Server
		{

			public final IntValue debugLevel;
			public final DoubleValue spawnersExplodePercentage;
			public final ConfigValue<String> defaultNoBreakMobsActual;
			public final String defaultNoBreakMobs6464 = 
					  "harderspawners:default,0.2;"
					+ "minecraft:pig,0.0;"
					+ "minecraft:cow,0.0;"
					+ "minecraft:sheep,0.0;"
					+ "minecraft:parrot,0.0;"
					+ "minecraft:blaze,0.0;"
					;	
			
			public Server(ForgeConfigSpec.Builder builder)
			{
				builder.push("Spawners Spawn in Light Control Values");

				debugLevel = builder
						.comment("Debug Level: 0 = Off, 1 = Log, 2 = Chat+Log")
						.translation(Main.MODID + ".config." + "debugLevel")
						.defineInRange("debugLevel", () -> 0, 0, 2);
		
				spawnersExplodePercentage = builder
						.comment("Explode percentage when Spawners Break")
						.translation(Main.MODID + ".config." + "spawnersExplodePercentage")
						.defineInRange("spawnersExplodePercentage", () -> 33.0, 0.0, 100.0);
				
				builder.pop();
				
				builder.push ("No Break Mobs Values 6464");
				
				defaultNoBreakMobsActual = builder
						.comment("Trail Block String 6464")
						.translation(Main.MODID + ".config" + "defaultNoBreakMobsActual")
						.define("defaultNoBreakMobsActual", defaultNoBreakMobs6464);
				builder.pop();	
			}
		}	
}
