import java.awt.*;

public class MovingPlatform extends Platform {
    private float startPosition;
    private float endPosition;
    private float speed;
    private boolean horizontal;
    private float currentPosition;
    private boolean movingForward = true;

    public MovingPlatform(int x, int y, int width, int height, int start, int end, float speed, boolean horizontal) {
        super(x, y, width, height);
        this.horizontal = horizontal;
        this.speed = speed;

        if (horizontal) {
            this.startPosition = start;
            this.endPosition = end;
            this.currentPosition = x;
        } else {
            this.startPosition = start;
            this.endPosition = end;
            this.currentPosition = y;
        }
    }

    @Override
    public void update() {
        if (movingForward) {
            currentPosition += speed;
            if (currentPosition >= endPosition) {
                movingForward = false;
            }
        } else {
            currentPosition -= speed;
            if (currentPosition <= startPosition) {
                movingForward = true;
            }
        }

        if (horizontal) {
            setX((int)currentPosition);
        } else {
            setY((int)currentPosition);
        }
    }
}