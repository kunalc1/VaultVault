import java.awt.*;

public class Hazard {
    private int x, y, width, height;
    private HazardType type;
    private Color color;
    private int animationTick = 0;
    
    public enum HazardType {
        SPIKES,
        LAVA
    }
    
    public Hazard(int x, int y, int width, int height, HazardType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        
        switch (type) {
            case SPIKES:
                this.color = Color.GRAY;
                break;
            case LAVA:
                this.color = Color.ORANGE;
                break;
        }
    }
    
    public void update() {
        animationTick++;
    }
    
    public void render(Graphics g) {
        switch (type) {
            case SPIKES:
                renderSpikes(g);
                break;
            case LAVA:
                renderLava(g);
                break;
        }
    }
    
    private void renderSpikes(Graphics g) {
        g.setColor(color);
        
        int spikeWidth = 10;
        int numSpikes = width / spikeWidth;
        
        for (int i = 0; i < numSpikes; i++) {
            int spikeX = x + (i * spikeWidth);
            int[] xPoints = {spikeX, spikeX + spikeWidth/2, spikeX + spikeWidth};
            int[] yPoints = {y + height, y, y + height};
            g.fillPolygon(xPoints, yPoints, 3);
        }
    }
    
    private void renderLava(Graphics g) {
        // Base lava
        g.setColor(color);
        g.fillRect(x, y, width, height);
        
        // Lava bubbles/highlights
        g.setColor(new Color(255, 255, 150));
        int bubblePhase = animationTick % 40;
        
        for (int i = 0; i < width; i += 15) {
            if ((i / 15) % 3 == bubblePhase / 15) {
                g.fillOval(x + i, y - 3, 6, 6);
            }
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
    
    public HazardType getType() {
        return type;
    }
}
