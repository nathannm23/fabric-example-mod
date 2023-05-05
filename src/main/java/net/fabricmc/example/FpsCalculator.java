package net.fabricmc.example;

public class FpsCalculator {
    private long lastTime;
    private int fps;
    private int fpsStable;
    private long totalElapsed;

    /*
    Create a new FpsCalculator and initialize instance variables
     */
    public FpsCalculator() {
        this.lastTime = System.currentTimeMillis();
        this.fps = 0;
        this.fpsStable = 0;
        this.totalElapsed = 0;
    }

    /*
    Method for updating instance variables of the FpsCalculator.
    This method is called by DisplayMixin every game tick.
     */
    public void update() {
        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTime;
        totalElapsed += elapsedTime;
        fps = (int) (1000 / elapsedTime);
        lastTime = currentTime;
        if(totalElapsed >= 1000) {
            fpsStable = fps;
            totalElapsed = 0;
        }
    }

    /*
    This method returns the raw calculation for frames per second which is updated every game tick.
     */
    public int getFps() {
        return fps;
    }

    /*
    This method returns a stable fps value for frames per second which is updated every 1000ms with a value copied directly from the unstable fps value.
    This mimics the fps calculation of the f3 menu, and makes it possible to read.
     */
    public int getFpsStable() {
        return fpsStable;
    }

}
