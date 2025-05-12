import java.awt.*;

public class Coin {
    private int x, y;
    private int width = 15;
    private int height = 15;
    private int animationTick = 0;

    public Coin(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        animationTick++;
        if (animationTick > 60) {
            animationTick = 0;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.YELLOW);

        // Simple animation - the coin pulsates
        int pulseSize = (int)(Math.sin(animationTick * 0.1) * 2);

        g.fillOval(x - pulseSize/2, y - pulseSize/2,
                width + pulseSize, height + pulseSize);

        // Inner circle for coin detail
        g.setColor(new Color(255, 215, 0)); // Gold color
        g.fillOval(x + 3, y + 3, width - 6, height - 6);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
}