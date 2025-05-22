import java.awt.*;

public class Hazard {
    private int x, y, width, height;
    private HazardType type;
    private Color color;
    private int animationTick = 0;

    public enum HazardType {
        SPIKES,
        LAVA,
        ELECTRIC,
        SAW,
        ICE
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
            case ELECTRIC:
                this.color = new Color(80, 220, 255);
                break;
            case SAW:
                this.color = Color.LIGHT_GRAY;
                break;
            case ICE:
                this.color = new Color(180, 240, 255);
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
            case ELECTRIC:
                renderElectric(g);
                break;
            case SAW:
                renderSaw(g);
                break;
            case ICE:
                renderIce(g);
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
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(new Color(255, 255, 150));
        int bubblePhase = animationTick % 40;
        for (int i = 0; i < width; i += 15) {
            if ((i / 15) % 3 == bubblePhase / 15) {
                g.fillOval(x + i, y - 3, 6, 6);
            }
        }
    }

    private void renderElectric(Graphics g) {
        // Electric hazard: animated zigzag
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(3f));
        int segments = 8;
        int[] xs = new int[segments + 1];
        int[] ys = new int[segments + 1];
        for (int i = 0; i <= segments; i++) {
            xs[i] = x + (width * i) / segments;
            int phase = (animationTick + i * 4) % 16;
            ys[i] = y + height / 2 + (int)(Math.sin((phase / 16.0) * 2 * Math.PI) * (height / 3));
        }
        g2d.setColor(new Color(80, 220, 255, 180));
        for (int i = 0; i < segments; i++) {
            g2d.drawLine(xs[i], ys[i], xs[i+1], ys[i+1]);
        }
        g2d.setStroke(new BasicStroke(1f));
    }

    private void renderSaw(Graphics g) {
        // Saw hazard: spinning blade
        Graphics2D g2d = (Graphics2D) g;
        int cx = x + width / 2;
        int cy = y + height / 2;
        int radius = Math.min(width, height) / 2 - 2;
        int teeth = 12;
        double angleStep = 2 * Math.PI / teeth;
        double rotation = (animationTick % 60) * (2 * Math.PI / 60);
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(cx - radius, cy - radius, radius * 2, radius * 2);
        g2d.setColor(color);
        for (int i = 0; i < teeth; i++) {
            double angle = i * angleStep + rotation;
            int tx1 = cx + (int)(Math.cos(angle) * radius);
            int ty1 = cy + (int)(Math.sin(angle) * radius);
            int tx2 = cx + (int)(Math.cos(angle + angleStep/2) * (radius + 8));
            int ty2 = cy + (int)(Math.sin(angle + angleStep/2) * (radius + 8));
            int tx3 = cx + (int)(Math.cos(angle + angleStep) * radius);
            int ty3 = cy + (int)(Math.sin(angle + angleStep) * radius);
            int[] xs = {tx1, tx2, tx3};
            int[] ys = {ty1, ty2, ty3};
            g2d.fillPolygon(xs, ys, 3);
        }
        g2d.setColor(Color.GRAY);
        g2d.fillOval(cx - radius/2, cy - radius/2, radius, radius);
    }

    private void renderIce(Graphics g) {
        // Ice hazard: blue/white with sparkles
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(new Color(255, 255, 255, 120));
        for (int i = 0; i < width; i += 16) {
            int sparkleX = x + i + (animationTick % 16);
            int sparkleY = y + (animationTick * (i+3) % height);
            g.fillRect(sparkleX, sparkleY, 2, 6);
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public HazardType getType() {
        return type;
    }

    // Add helper for slide effect (to be called from Level)
    public static void applyIceSlideEffect(Player player) {
        // Simulate sliding: store momentum, keep sliding after releasing movement keys
        float slideFriction = 0.97f; // Lower value = more slippery
        // If player is pressing left/right, set velocityX as normal
        if (player.getMovingLeft()) {
            player.setVelocityX(-5.0f); // Use a reasonable slide speed
        } else if (player.getMovingRight()) {
            player.setVelocityX(5.0f);
        } else {
            // If not pressing, keep sliding and slowly reduce speed
            player.setVelocityX(player.getVelocityX() * slideFriction);
            // Stop if very slow
            if (Math.abs(player.getVelocityX()) < 0.3f) {
                player.setVelocityX(0);
            }
        }
    }
}
