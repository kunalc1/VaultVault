import java.awt.*;

public class Player {
    private float x, y;
    private int width = 30;
    private int height = 50;
    private float velocityX = 0;
    private float velocityY = 0;
    private final float moveSpeed = 5.0f;
    private final float jumpStrength = -12.0f;
    private final float gravity = 0.3f;
    private boolean onGround = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        velocityX = 0;
        if (movingLeft) velocityX -= moveSpeed;
        if (movingRight) velocityX += moveSpeed;
        if (velocityY > 20) velocityY = 20;
        x += velocityX;
        y += velocityY;
    }

    public void applyGravity() {
        if (!onGround) {
            velocityY += gravity;
        }
    }

    public void jump() {
        if (onGround) {
            velocityY = jumpStrength;
            onGround = false;
        }
    }

    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)x, (int)y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public Rectangle getTopBounds() {
        return new Rectangle((int)x + 2, (int)y, width - 4, 5);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle((int)x + 2, (int)y + height - 5, width - 4, 5);
    }

    public Rectangle getLeftBounds() {
        return new Rectangle((int)x, (int)y + 2, 5, height - 4);
    }

    public Rectangle getRightBounds() {
        return new Rectangle((int)x + width - 5, (int)y + 2, 5, height - 4);
    }

    public float getX() { return x; }
    public void setX(float x) { this.x = x; }
    public float getY() { return y; }
    public void setY(float y) { this.y = y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getVelocityY() { return velocityY; }
    public void setVelocityY(float velocityY) { this.velocityY = velocityY; }
    public void setOnGround(boolean onGround) { this.onGround = onGround; }
    public void setMovingLeft(boolean movingLeft) { this.movingLeft = movingLeft; }
    public void setMovingRight(boolean movingRight) { this.movingRight = movingRight; }
    public float getVelocityX() { return velocityX; }
    public void setVelocityX(float velocityX) { this.velocityX = velocityX; }
    public boolean getMovingLeft() { return movingLeft; }
    public boolean getMovingRight() { return movingRight; }
}
