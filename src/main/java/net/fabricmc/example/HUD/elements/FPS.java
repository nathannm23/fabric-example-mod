package net.fabricmc.example.HUD.elements;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.FpsCalculator;
import net.fabricmc.example.HUD.HUDElement;
import net.fabricmc.example.HUD.HUDElementType;
import net.minecraft.client.MinecraftClient;

public class FPS extends HUDElement {
    public FPS() {
        this.setTitle("FPS: ");
        this.setType(HUDElementType.FPS);
        this.setColor(0xFFFFFF);
        this.setXPos(2);
        this.setYPos(2);
    }
    public void update(){
        ExampleMod.fpsCalculator.update();
        this.setContent(ExampleMod.fpsCalculator.getFpsStable()+"");
    }

    /*

    public void shouldRender(boolean TF){
        if(TF){
            this.enable();
        }else{
            this.disable();
        }
    }
    public boolean shouldRender(){
        return this.isEnabled();
    }

     */
}
