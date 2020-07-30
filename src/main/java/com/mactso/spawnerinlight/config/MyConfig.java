package com.mactso.spawnerinlight.config;

	import org.apache.commons.lang3.tuple.Pair;
	import org.apache.logging.log4j.LogManager;
	import org.apache.logging.log4j.Logger;

	import com.mactso.spawnerinlight.Main;

	import net.minecraftforge.common.ForgeConfigSpec;
	import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
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
		public static boolean SpawnersSpawnInLight;
		public static boolean SpawnersExplode;
		public static double breakPercentage;
		
		@SubscribeEvent
		public static void onModConfigEvent(final ModConfig.ModConfigEvent configEvent)
		{
			if (configEvent.getConfig().getSpec() == MyConfig.SERVER_SPEC)
			{
				bakeConfig();
			}
		}

		public static void bakeConfig()
		{
			debugLevel = SERVER.debugLevel.get();
			SpawnersSpawnInLight = SERVER.SpawnersSpawnInLight.get();
			SpawnersExplode = SERVER.SpawnersExplode.get();
			breakPercentage = SERVER.breakPercentage.get();

		}


		public static class Server
		{

			public final IntValue debugLevel;
			public static BooleanValue SpawnersSpawnInLight;
			public static BooleanValue SpawnersExplode;
			public static DoubleValue breakPercentage;
			
			public Server(ForgeConfigSpec.Builder builder)
			{
				builder.push("Spawners Spawn in Light Control Values");

				debugLevel = builder
						.comment("Debug Level: 0 = Off, 1 = Log, 2 = Chat+Log")
						.translation(Main.MODID + ".config." + "debugLevel")
						.defineInRange("debugLevel", () -> 0, 0, 2);
			
				breakPercentage = builder
						.comment("Respawn Percentage")
						.translation(Main.MODID + ".config." + "breakPercentage")
						.defineInRange("breakPercentage", () -> .02, 0.0, 10.0);
				SpawnersSpawnInLight = builder
						.comment("Zombie Deaths In Hard Mode")
						.translation(Main.MODID + ".config." + "SpawnersSpawnInLight")
						.define ("SpawnersSpawnInLight", () -> true);
				
				SpawnersExplode = builder
						.comment("Zombie Deaths In Hard Mode")
						.translation(Main.MODID + ".config." + "SpawnersExplode")
						.define ("SpawnersExplode", () -> true);
				
				builder.pop();
			}
		}	
}
