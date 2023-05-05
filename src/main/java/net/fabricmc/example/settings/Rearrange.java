package net.fabricmc.example.settings;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.HUD.HUDElement;
import net.fabricmc.example.HUD.HUDElementType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

@Environment(value=EnvType.CLIENT)
public class Rearrange extends Screen {
    private static HUDElement beingMoved = null;
    public static final double SHIFT_SCROLL_AMOUNT = 7.0;
    private static final Text USAGE_TEXT = Text.translatable("chat_screen.usage");
    private static final int MAX_INDICATOR_TOOLTIP_WIDTH = 210;
    private String originalChatText;

    public Rearrange() {
        super(Text.of("Rearrange HUD"));
    }


    @Override
    protected void init() {

        this.addDrawableChild(ButtonWidget.builder(Text.of("Exit menu"), button ->
                this.client.setScreen(null))
                .dimensions((this.width / 2) - 50, (this.height/2) - 10, 100, 20)
                .build());
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        this.init(client, width, height);
    }

    @Override
    public void removed() {
        this.client.inGameHud.getChatHud().resetScroll();
    }



    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (super.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.setScreen(null);
            return true;
        }
        return false;
    }



    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) {
            // instance of left mouse button
            if(beingMoved == null) {
                // there is currently no element being moved
                // check to see if mouseX and mouseY are inside the bounds of any HUD elements
                if(isOnInformation(mouseX, mouseY)){
                    // mouse is on some HUD element
                    // figure out which HUD element it is, and mark it as being clicked
                    HUDElement toEdit = findElement(mouseX, mouseY);
                    beingMoved = toEdit;
                    toEdit.setIsClicked(true);
                }
            }

        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        // the mouse was just released, check to see if anything was being moved
        if(beingMoved != null){
            // something was being moved, set it to the new position and clean up after the operation
            beingMoved.setXPos((int)mouseX);
            beingMoved.setYPos((int)mouseY);
            beingMoved = null;
        }
        return false;
    }

    // add an @Override to the mouseMoved method to make moving a HUD element appear smooth
    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        // check to see if something was being moved
        if(beingMoved != null){
            // something was being moved, set it to the mouse's new position as it moves
            beingMoved.setXPos((int)mouseX);
            beingMoved.setYPos((int)mouseY);
        }
    }


    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public boolean isOnInformation(double mouseX, double mouseY){
        boolean match = false;
        for(HUDElement element : ExampleMod.hudElements){
            if(element.x1() < mouseX && element.x2() > mouseX && element.y1() < mouseY && element.y2() > mouseY){
                match = true;
                break;
            }
        }
        return match;
    }
    public HUDElement findElement(double mouseX, double mouseY){
        for(HUDElement element : ExampleMod.hudElements){
            if(element.x1() < mouseX && element.x2() > mouseX && element.y1() < mouseY && element.y2() > mouseY){
                return element;
            }
        }
        return null;
    }

}

