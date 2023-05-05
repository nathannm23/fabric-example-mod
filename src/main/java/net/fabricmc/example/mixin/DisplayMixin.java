package net.fabricmc.example.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.HUD.HUDElement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
@Environment(EnvType.CLIENT)
public abstract class DisplayMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    /*
    This mixin is used to update the FpsCalculator variable found in the main mod class, and nothing else.
     */
    @Inject(method = "render", at = @At("TAIL"))
    private void calculateFps(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        ExampleMod.fps.update();
        ExampleMod.location.update();
    }

    /*
    This mixin injects code into the end of the render method inside InGameHud.
    It uses the textRenderer inside MinecraftClient to "draw" the fps display
    This fps counter updates while the game is paused, meaning it calculates menu frames and game frames.
     */
    @Inject(method = "render", at = @At("TAIL"))
    private void renderFps(MatrixStack matrices, float tickDelta, CallbackInfo info) {
        for(HUDElement element : ExampleMod.hudElements){
            element.draw(client, matrices);
        }
        this.client.textRenderer.drawWithShadow(matrices, "NotEmoji's Coins: " + ExampleMod.purse, 2, 50, 0xFFFFFF);
    }
}