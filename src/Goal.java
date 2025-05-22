import java.awt.*;

public class Goal {
    private int x, y, width, height;
    private Color baseColor = Color.YELLOW;
    private Color highlightColor = new Color(255, 215, 0);
    private boolean animate = true;
    private int animationTick = 0;

    public Goal(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        if (animate) {
            animationTick++;
        }
    }

    public void render(Graphics g) {
        int pulse = (int)(Math.sin(animationTick * 0.1) * 30);
        Color currentColor = new Color(
                Math.min(255, baseColor.getRed() + pulse),
                Math.min(255, baseColor.getGreen() + pulse),
                Math.min(255, baseColor.getBlue())
        );

        g.setColor(currentColor);
        g.fillRect(x, y, width, height);

        g.setColor(highlightColor);
        g.fillRect(x + 5, y + 5, width - 10, 5);
        g.fillRect(x + 5, y + height - 10, width - 10, 5);

        g.setColor(Color.WHITE);
        g.fillRect(x + width/2 - 2, y + 5, 4, height - 10);
        g.setColor(Color.RED);
        g.fillPolygon(
                new int[]{x + width/2, x + width - 10, x + width/2},
                new int[]{y + 10, y + 20, y + 30},
                3
        );
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
}
