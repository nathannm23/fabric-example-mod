package net.fabricmc.example.HUD.elements;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.HUD.HUDElement;
import net.fabricmc.example.HUD.HUDElementType;

public class Location extends HUDElement {
    public Location() {
        this.setTitle("Location: ");
        this.setType(HUDElementType.LOCATION_XYZ);
        this.setColor(0xFFFFFF);
        this.setXPos(2);
        this.setYPos(10);
    }
    public void update(){
        this.setContent("X: " + ExampleMod.client.cameraEntity.getBlockX()
        + " Y: " +ExampleMod.client.cameraEntity.getBlockY()
        + " Z: " +ExampleMod.client.cameraEntity.getBlockZ()
        );
    }
}
