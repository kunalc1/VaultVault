import java.awt.*;

public class Platform {
    protected int x, y;
    protected int width, height;
    protected Color color;

    public Platform(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void update() {
        // Default implementation does nothing
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    // Getters and setters
    public int getX() { return x; }
    public void setX(int x) { this.x = x; }
    public int getY() { return y; }
    public void setY(int y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Color getColor() { return color; }
    public void setColor(Color color) { this.color = color; }
}