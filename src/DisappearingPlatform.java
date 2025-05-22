import java.awt.Graphics;

public class DisappearingPlatform extends Platform {
    private int timer;
    private int visibilityDuration;
    private boolean visible = true;
    private boolean disappearedOnce = false;

    public DisappearingPlatform(int x, int y, int width, int height, int visibilityDuration) {
        super(x, y, width, height);
        this.visibilityDuration = visibilityDuration;
        this.timer = 0;
    }

    @Override
    public void update() {
        if (!visible && !disappearedOnce) {
            timer++;
            if (timer >= visibilityDuration) {
                visible = true;
                timer = 0;
                disappearedOnce = true;
            }
        }
    }

    public void triggerDisappear() {
        if (visible && !disappearedOnce) {
            visible = false;
            timer = 0;
        }
    }

    public void reset() {
        visible = true;
        disappearedOnce = false;
        timer = 0;
    }

    @Override
    public void render(Graphics g) {
        if (visible) {
            super.render(g);
        }
    }

    public boolean isVisible() {
        return visible;
    }
}

class Platform6 extends Platform {
    private int timer;
    private int visibilityDuration;
    private boolean visible = true;

    public Platform6(int x, int y, int width, int height, int visibilityDuration) {
        super(x, y, width, height);
        this.visibilityDuration = visibilityDuration;
        this.timer = 0;
    }

    public void update() {
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

    public void render(Graphics g) {
        if (visible) {
            super.render(g);
        }
    }

    public boolean isSolid() {
        return visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        if (visible) {
            this.timer = 0;
        }
    }
}
