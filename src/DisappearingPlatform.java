import java.awt.Graphics;

public class DisappearingPlatform extends Platform {
    private int timer;
    private int visibilityDuration;
    private boolean visible = true;

    public DisappearingPlatform(int x, int y, int width, int height, int visibilityDuration) {
        super(x, y, width, height);
        this.visibilityDuration = visibilityDuration;
        this.timer = 0;
    }

    @Override
    public void update() {
        // If platform is invisible, count time until it should reappear
        if (!visible) {
            timer++;
            if (timer >= visibilityDuration) {
                visible = true;
                timer = 0;
            }
        }
    }

    public void triggerDisappear() {
        if (visible) {
            visible = false;
            timer = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        if (visible) {
            super.render(g);
            // No warning indicators
        }
    }

    public boolean isVisible() {
        return visible;
    }
}