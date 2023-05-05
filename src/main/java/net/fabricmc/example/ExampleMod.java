package net.fabricmc.example;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.example.HUD.HUDElement;
import net.fabricmc.example.HUD.elements.FPS;
import net.fabricmc.example.HUD.elements.Location;
import net.fabricmc.example.settings.Rearrange;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.hypixel.api.HypixelAPI;
import net.hypixel.api.reactor.ReactorHttpClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.*;

public class ExampleMod implements ModInitializer {
	ReactorHttpClient httpClient = new ReactorHttpClient(UUID.fromString("ce86c7ad-703e-4e5e-9218-03116c93cfd2"));
	HypixelAPI hypixelAPI = new HypixelAPI(httpClient);
	public static double purse = 0;


	public static final Logger LOGGER = LoggerFactory.getLogger("modid");

	/*
	This is the fpsCalculator variable used by the DisplayMixin mixin
	 */
	public static FpsCalculator fpsCalculator = new FpsCalculator();

	public static ArrayList<HUDElement> hudElements = new ArrayList<>();
	public static FPS fps = new FPS();
	public static Location location = new Location();
	public static MinecraftClient client = MinecraftClient.getInstance();


	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		hudElements.add(fps);
		hudElements.add(location);

		LOGGER.info("Hello Fabric world!");
		hypixelAPI.getSkyBlockProfile("7ce52783dbd24c1f9ef61d041f4650d9").whenComplete((response, error) -> {
			if(error != null) {
				error.printStackTrace();
				return;
			}

			DecimalFormat df = new DecimalFormat("#.##");
			purse = Double.parseDouble(df.format(response.getProfile()
					.get("members").getAsJsonObject()
					.get("6bafd4a65c6b4675a3070a7c72ad6d66").getAsJsonObject()
					.get("coin_purse").getAsDouble()));
		});

		Gson gson = new Gson();
		hypixelAPI.getSkyBlockProfile("7ce52783dbd24c1f9ef61d041f4650d9").whenComplete((response1, error1)->{
			// Check if there was an API error
			if (error1 != null) {
				error1.printStackTrace();
				return;
			}
		});

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("e")
				.executes(context -> {
					context.getSource().getPlayer().sendMessage(Text.literal("Called /e"));
					// open settings menu
					MinecraftServer server = context.getSource().getServer();
					MinecraftClient mc = MinecraftClient.getInstance();
					mc.execute(()->{
						mc.setScreenAndRender(new Rearrange());
					});

					return 1;
				})));
	}
}
