package net.fabricmc.example.HUD;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.HUD.elements.isClicked;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public abstract class HUDElement {
    private double height;
    private double width;
    private double scale;
    private int xPos;
    private int yPos;
    private int color;
    private String title;
    private String content;
    private HUDElementType type;
    private boolean isEnabled = true;
    private isClicked isClicked = net.fabricmc.example.HUD.elements.isClicked.FALSE;
    public void draw(MinecraftClient client, MatrixStack matrices) {
        client.textRenderer.drawWithShadow(matrices, title + content, xPos, yPos, color);
        Text text = Text.of(title + content);
        this.width = client.textRenderer.getWidth(text);
        this.height = client.textRenderer.fontHeight;
    }
    public double x1(){
        return xPos;
    }
    public double y1(){
        return yPos;
    }
    public double x2(){
        return xPos + width;
    }
    public double y2(){
        return yPos + height;
    }
    public void enable() {
        this.isEnabled = true;
    }
    public void disable() {
        this.isEnabled = false;
    }
    public boolean isEnabled() {
        return this.isEnabled;
    }
    public void setScale(double scale){
        this.scale = scale;
    }
    public double getScale(){
        return this.scale;
    }
    public int getXPos(){
        return this.xPos;
    }
    public void setXPos(int xPos){
        this.xPos = xPos;
    }
    public int getYPos(){
        return this.yPos;
    }
    public void setYPos(int yPos){
        this.yPos = yPos;
    }
    public void moveTo(int x, int y){
        this.xPos = x;
        this.yPos = y;
    }
    public int[] getPos() {
        return new int[]{xPos, yPos};
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public int getColor() {
        return this.color;
    }
    public void setColor(int color){
        this.color = color;
    }

    public HUDElementType getType() {
        return type;
    }

    public void setType(HUDElementType type) {
        this.type = type;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public net.fabricmc.example.HUD.elements.isClicked getIsClicked() {
        return isClicked;
    }

    public void setIsClicked(boolean isClicked) {
        if(isClicked){
            this.isClicked = net.fabricmc.example.HUD.elements.isClicked.TRUE;
        }else{
            this.isClicked = net.fabricmc.example.HUD.elements.isClicked.FALSE;
        }
    }
}
