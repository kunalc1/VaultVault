import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Level {
    private Player player;
    private List<Platform> platforms;
    private List<Hazard> hazards;
    private Goal goal;
    private Color backgroundColor = new Color(25, 25, 50);
    private Color platformColor = Color.GREEN;
    private boolean levelCompleted = false;

    public Level() {
        platforms = new ArrayList<>();
        hazards = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    public void addHazard(Hazard hazard) {
        hazards.add(hazard);
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public void createLevel(int levelNumber) {
        platforms.clear();
        hazards.clear();
        levelCompleted = false;

        // Ground platform for all levels
        addPlatform(new Platform(0, 500, 800, 50));

        switch (levelNumber) {
            case 1:
                createLevel1();
                break;
            case 2:
                createLevel2();
                break;
            case 3:
                createLevel3();
                break;
            case 4:
                createLevel4();
                break;
            default:
                createSampleLevel();
                break;
        }

        // Add goal - now using the Goal class instead of a yellow platform
        goal = new Goal(750, 430, 40, 70);
    }

    private void createLevel1() {
        // Basic level with a mix of platforms
        addPlatform(new Platform(200, 400, 100, 20));
        addPlatform(new MovingPlatform(350, 350, 100, 20, 350, 450, 1.0f, true));
        addPlatform(new Platform(500, 300, 100, 20));

        // Add a basic hazard
        addHazard(new Hazard(300, 480, 80, 20, Hazard.HazardType.SPIKES));

        platformColor = new Color(0, 200, 0);
        backgroundColor = new Color(25, 25, 50);
    }

    private void createLevel2() {
        // Medium difficulty with disappearing platform
        addPlatform(new Platform(150, 450, 80, 20));
        addPlatform(new Platform(280, 400, 80, 20));
        addPlatform(new DisappearingPlatform(400, 350, 80, 20, 120));
        addPlatform(new Platform(540, 300, 80, 20));
        addPlatform(new MovingPlatform(650, 250, 80, 20, 550, 650, 1.5f, true));
        addPlatform(new Platform(500, 200, 80, 20));
        addPlatform(new Platform(350, 150, 80, 20));

        // Add hazards
        addHazard(new Hazard(250, 480, 100, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(450, 480, 80, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(620, 480, 70, 20, Hazard.HazardType.LAVA));

        platformColor = new Color(0, 150, 200);
        backgroundColor = new Color(40, 40, 70);
    }

    private void createLevel3() {
        // Advanced level with complex platform arrangement
        addPlatform(new Platform(120, 450, 60, 20));
        addPlatform(new DisappearingPlatform(220, 400, 60, 20, 100));
        addPlatform(new Platform(320, 450, 60, 20));
        addPlatform(new MovingPlatform(420, 400, 60, 20, 420, 520, 1.0f, true));
        addPlatform(new Platform(520, 350, 60, 20));
        addPlatform(new MovingPlatform(400, 300, 60, 20, 250, 400, 1.5f, false));
        addPlatform(new Platform(300, 250, 60, 20));
        addPlatform(new DisappearingPlatform(400, 200, 60, 20, 80));
        addPlatform(new Platform(500, 150, 60, 20));
        addPlatform(new Platform(600, 200, 60, 20));

        // Add challenging hazards
        addHazard(new Hazard(190, 480, 400, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(620, 480, 120, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(350, 350, 40, 15, Hazard.HazardType.SPIKES));

        platformColor = new Color(200, 100, 50);
        backgroundColor = new Color(60, 30, 60);
    }

    private void createLevel4() {
        // Special level showcasing moving and disappearing platforms
        // Vertical moving platforms
        addPlatform(new MovingPlatform(150, 400, 80, 20, 300, 450, 1.0f, false));
        addPlatform(new MovingPlatform(300, 350, 80, 20, 250, 400, 1.2f, false));

        // Horizontal moving platforms
        addPlatform(new MovingPlatform(450, 350, 80, 20, 400, 600, 2.0f, true));
        addPlatform(new MovingPlatform(600, 250, 80, 20, 500, 700, 1.5f, true));

        // Disappearing platforms with different timings
        addPlatform(new DisappearingPlatform(250, 300, 80, 20, 60));
        addPlatform(new DisappearingPlatform(400, 250, 80, 20, 120));
        addPlatform(new DisappearingPlatform(550, 200, 80, 20, 180));

        // Add some hazards
        addHazard(new Hazard(200, 480, 150, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(400, 480, 150, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(600, 480, 150, 20, Hazard.HazardType.SPIKES));

        platformColor = new Color(100, 150, 200);
        backgroundColor = new Color(30, 50, 80);
    }

    public void createSampleLevel() {
        // Original sample level (now a fallback)
        addPlatform(new Platform(200, 400, 100, 20));
        addPlatform(new Platform(350, 350, 100, 20));
        addPlatform(new Platform(500, 300, 100, 20));
        addPlatform(new Platform(200, 250, 100, 20));
        addPlatform(new Platform(50, 200, 100, 20));
    }

    public void update() {
        // Update all platforms
        for (Platform platform : platforms) {
            platform.update();
        }

        if (player != null) {
            player.update();
            checkCollisions();
        }

        if (goal != null) {
            goal.update();
            // Check for goal collision
            if (player.getBounds().intersects(goal.getBounds())) {
                levelCompleted = true;
            }
        }

        // Update hazards
        for (Hazard hazard : hazards) {
            hazard.update();
        }
    }

    private void checkCollisions() {
        // Reset player standing state
        player.setOnGround(false);

        // Apply gravity first
        player.applyGravity();

        // Small tolerance value to improve ground detection
        final int GROUND_TOLERANCE = 5;
        // Number of frames in advance to trigger disappearance
        final int FRAMES_AHEAD = 5;

        for (Platform platform : platforms) {
            // Skip collision detection for disappearing platforms that are currently invisible
            if (platform instanceof DisappearingPlatform && !((DisappearingPlatform) platform).isVisible()) {
                continue;
            }

            Rectangle playerBounds = player.getBounds();
            Rectangle platformBounds = platform.getBounds();

            // Check if player is approaching a disappearing platform
            if (platform instanceof DisappearingPlatform &&
                    ((DisappearingPlatform) platform).isVisible() && // Only trigger if visible
                    player.getVelocityY() > 0 && // Player is falling
                    player.getY() + player.getHeight() <= platform.getY() - 5 && // Player is above platform
                    // Predict where player will be in FRAMES_AHEAD frames
                    player.getY() + player.getHeight() + (player.getVelocityY() * FRAMES_AHEAD) >= platform.getY() &&
                    player.getX() + player.getWidth() > platform.getX() && // Horizontally aligned with platform
                    player.getX() < platform.getX() + platform.getWidth()) {

                // Trigger the platform to disappear before landing
                ((DisappearingPlatform) platform).triggerDisappear();
            }

            // Rest of collision detection remains the same...
            // Check if player is above and close to platform (within tolerance)
            boolean closeToGround = player.getY() + player.getHeight() <= platform.getY() + GROUND_TOLERANCE &&
                    player.getY() + player.getHeight() + player.getVelocityY() >= platform.getY();

            // Vertical collisions
            if (closeToGround &&
                    player.getX() + player.getWidth() > platform.getX() &&
                    player.getX() < platform.getX() + platform.getWidth()) {
                // Landing on a platform
                player.setY(platform.getY() - player.getHeight());
                player.setVelocityY(0);
                player.setOnGround(true);
            } else if (player.getTopBounds().intersects(platformBounds) && player.getVelocityY() < 0) {
                // Hitting head on bottom of platform
                player.setY(platform.getY() + platform.getHeight());
                player.setVelocityY(0);
            }

            // Horizontal collisions
            if (player.getLeftBounds().intersects(platformBounds) && player.getVelocityX() < 0) {
                player.setX(platform.getX() + platform.getWidth());
            } else if (player.getRightBounds().intersects(platformBounds) && player.getVelocityX() > 0) {
                player.setX(platform.getX() - player.getWidth());
            }
        }

        // Hazard collisions check remains unchanged
        for (Hazard hazard : hazards) {
            if (player.getBounds().intersects(hazard.getBounds())) {
                player.setX(50);
                player.setY(300);
                player.setVelocityX(0);
                player.setVelocityY(0);
                break;
            }
        }
    }

    public void render(Graphics g) {
        // Draw background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, 800, 600);

        // Draw platforms
        for (Platform platform : platforms) {
            if (platform.getColor() == null) {
                platform.setColor(platformColor);
            }
            platform.render(g);
        }

        // Draw hazards
        for (Hazard hazard : hazards) {
            hazard.render(g);
        }

        // Draw goal
        if (goal != null) {
            goal.render(g);
        }

        // Draw player
        if (player != null) {
            player.render(g);
        }
    }
}