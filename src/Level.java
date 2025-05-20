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
    private static final int WID = VaultVault.WIDTH;
    private static final int HEI = VaultVault.HEIGHT;

    // Boss fields for level 5
    private boolean bossActive = false;
    private int bossX, bossY, bossW = 80, bossH = 80;
    private int bossHits = 0;
    private final int bossMaxHits = 5;
    private boolean bossDefeated = false;
    private int bossMoveCooldown = 0;

    public Level() {
        platforms = new ArrayList<>();
        hazards = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    public void addPlatform(Platform platform) {
        // Set the platform color if not already set
        if (platform.getColor() == null) {
            platform.setColor(platformColor);
        }
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

    public boolean isCompleted() {
        return levelCompleted;
    }

    public void createLevel(int levelNumber) {
        platforms.clear();
        hazards.clear();
        levelCompleted = false;
        bossActive = false;
        bossDefeated = false;
        bossHits = 0;
        bossMoveCooldown = 0;

        switch (levelNumber) {
            case 0:
                createTutorialLevel();
                break;
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
            case 5:
                createLevel5();
                bossActive = true;
                bossX = 700;
                bossY = HEI - 200;
                break;
            default:
                createSampleLevel();
                break;
        }

    }

    private void createLevel1() {
        // Easy, slightly larger, introduces moving platform and spikes
        platformColor = new Color(0, 200, 0);
        backgroundColor = new Color(25, 25, 50);

        addPlatform(new Platform(100, HEI-120, 120, 20));
        addPlatform(new Platform(300, HEI-200, 120, 20));
        addPlatform(new MovingPlatform(500, HEI-250, 120, 20, 500, 700, 1.0f, true));
        addPlatform(new Platform(800, HEI-180, 120, 20));
        addHazard(new Hazard(420, HEI-100, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-120, 40, 70)); // Adjusted to be visible above ground
    }

    private void createLevel2() {
        // Medium, more vertical, introduces disappearing platforms and lava
        platformColor = new Color(0, 90, 140); // less bright
        backgroundColor = new Color(25, 30, 45);

        addPlatform(new Platform(80, HEI-100, 100, 20));
        addPlatform(new DisappearingPlatform(250, HEI-180, 100, 20, 100));
        addPlatform(new Platform(400, HEI-260, 100, 20));
        addPlatform(new MovingPlatform(600, HEI-340, 100, 20, 600, 800, 1.5f, true));
        addPlatform(new Platform(850, HEI-420, 100, 20));
        addHazard(new Hazard(350, HEI-80, 100, 20, Hazard.HazardType.LAVA)); // fits on ground
        addHazard(new Hazard(700, HEI-80, 100, 20, Hazard.HazardType.SPIKES)); // fits on ground
        addHazard(new Hazard(500, HEI-220, 60, 20, Hazard.HazardType.ELECTRIC)); // fits on platform
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-190, 40, 70));
    }

    private void createLevel3() {
        // Hard, larger, more hazards, more moving/disappearing platforms
        platformColor = new Color(120, 60, 30); // less bright
        backgroundColor = new Color(35, 20, 35);

        addPlatform(new Platform(60, HEI-100, 80, 20));
        addPlatform(new DisappearingPlatform(200, HEI-180, 80, 20, 80));
        addPlatform(new MovingPlatform(350, HEI-260, 80, 20, 350, 600, 2.0f, true));
        addPlatform(new Platform(600, HEI-340, 80, 20));
        addPlatform(new DisappearingPlatform(800, HEI-420, 80, 20, 60));
        addPlatform(new Platform(900, HEI-500, 80, 20));
        addHazard(new Hazard(150, HEI-80, 80, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(500, HEI-80, 80, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(750, HEI-80, 80, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(700, HEI-320, 40, 20, Hazard.HazardType.SAW)); // fits on platform
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-270, 40, 70));
    }

    private void createLevel4() {
        // Snowy/Ice themed: blue/white, ice hazards, slippery platforms
        platformColor = new Color(120, 180, 210); // less bright
        backgroundColor = new Color(90, 120, 160);

        addPlatform(new Platform(40, HEI-100, 80, 20));
        addPlatform(new MovingPlatform(180, HEI-180, 80, 20, 180, 400, 2.0f, true));
        addPlatform(new DisappearingPlatform(350, HEI-260, 80, 20, 60));
        addPlatform(new Platform(500, HEI-340, 80, 20));
        addPlatform(new MovingPlatform(650, HEI-420, 80, 20, 650, 900, 1.5f, true));
        addPlatform(new DisappearingPlatform(850, HEI-500, 80, 20, 40));
        addPlatform(new Platform(950, HEI-580, 80, 20));
        // ICE hazards (make them fit on platforms)
        addHazard(new Hazard(120, HEI-120, 80, 20, Hazard.HazardType.ICE));
        addHazard(new Hazard(400, HEI-360, 80, 20, Hazard.HazardType.ICE));
        addHazard(new Hazard(700, HEI-440, 80, 20, Hazard.HazardType.ICE));
        addHazard(new Hazard(900, HEI-500, 60, 20, Hazard.HazardType.SAW));
        addHazard(new Hazard(800, HEI-320, 60, 20, Hazard.HazardType.ELECTRIC));
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-350, 40, 70));
    }

    private void createLevel5() {
        // Torturous, huge, many hazards, tight jumps, moving/disappearing platforms, "gauntlet"
        platformColor = new Color(120, 20, 20); // less bright
        backgroundColor = new Color(10, 0, 0);

        addPlatform(new Platform(30, HEI-100, 70, 20));
        addHazard(new Hazard(120, HEI-120, 70, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(220, HEI-180, 70, 20, 40));
        addHazard(new Hazard(300, HEI-200, 70, 20, Hazard.HazardType.SPIKES));
        addPlatform(new MovingPlatform(400, HEI-240, 70, 20, 400, 600, 2.5f, true));
        addHazard(new Hazard(500, HEI-260, 70, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(620, HEI-320, 70, 20, 30));
        addHazard(new Hazard(700, HEI-340, 70, 20, Hazard.HazardType.SPIKES));
        addPlatform(new MovingPlatform(800, HEI-400, 70, 20, 800, 1000, 3.0f, true));
        addHazard(new Hazard(900, HEI-420, 70, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(1020, HEI-480, 70, 20, 20));
        addHazard(new Hazard(1100, HEI-500, 70, 20, Hazard.HazardType.SPIKES));
        addPlatform(new Platform(1200, HEI-560, 70, 20));
        addHazard(new Hazard(1270, HEI-580, 70, 20, Hazard.HazardType.LAVA));
        addPlatform(new MovingPlatform(1400, HEI-640, 70, 20, 1400, 1600, 3.5f, true));
        addHazard(new Hazard(1500, HEI-660, 70, 20, Hazard.HazardType.SPIKES));
        addPlatform(new DisappearingPlatform(1620, HEI-700, 70, 20, 10));
        addHazard(new Hazard(1700, HEI-720, 70, 20, Hazard.HazardType.LAVA));
        addPlatform(new Platform(1800, HEI-760, 70, 20));
        addHazard(new Hazard(1870, HEI-780, 70, 20, Hazard.HazardType.SPIKES));
        // Final gauntlet
        for (int i = 0; i < 8; i++) {
            int x = 2000 + i * 120;
            if (i % 2 == 0) {
                addPlatform(new MovingPlatform(x, HEI-800 + i*30, 90, 20, x, x+60, 2.0f + i*0.2f, true));
                if (i % 4 == 0) {
                    addHazard(new Hazard(x+100, HEI-800 + i*30, 60, 20, Hazard.HazardType.ELECTRIC));
                } else {
                    addHazard(new Hazard(x+100, HEI-800 + i*30, 60, 20, Hazard.HazardType.SAW));
                }
            } else {
                addPlatform(new DisappearingPlatform(x, HEI-800 + i*30, 90, 20, 30 + i*10));
                addHazard(new Hazard(x+100, HEI-800 + i*30, 80, 20, Hazard.HazardType.SPIKES));
            }
        }
        addPlatform(new Platform(0, HEI-50, 2500, 50));
        setGoal(new Goal(WID - 80, HEI-450, 40, 70));
    }

    private void createTutorialLevel() {
        // Unique, visually interesting, simple tutorial with gaps, spikes, and lava
        // Colors
        platformColor = new Color(120, 220, 255);
        backgroundColor = new Color(10, 10, 40);

        // Floating platforms with gaps
        addPlatform(new Platform(50, HEI - 150, 120, 20)); // Start
        addPlatform(new Platform(220, HEI - 200, 100, 20));
        addPlatform(new Platform(370, HEI - 250, 80, 20));
        addPlatform(new Platform(500, HEI - 200, 100, 20));
        addPlatform(new Platform(650, HEI - 150, 120, 20));
        addPlatform(new Platform(820, HEI - 100, 100, 20)); // Near goal

        // Decorative arch
        addPlatform(new Platform(320, HEI - 320, 60, 10));
        addPlatform(new Platform(620, HEI - 320, 60, 10));

        // Hazards: spikes and lava in the gaps
        addHazard(new Hazard(170, HEI - 130, 50, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(450, HEI - 180, 40, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(300, HEI - 80, 120, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(740, HEI - 80, 80, 20, Hazard.HazardType.LAVA));

        // Ground (for falling)
        addPlatform(new Platform(0, HEI - 50, WID, 50));

        // Goal
        setGoal(new Goal(880, HEI - 170, 40, 70)); // Already visible, but you can adjust if needed
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
            // Always apply gravity before updating player
            player.applyGravity();
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

        // Boss logic for level 5
        if (bossActive && !bossDefeated) {
            if (bossMoveCooldown > 0) bossMoveCooldown--;

            // Check if player jumps on boss
            Rectangle bossRect = new Rectangle(bossX, bossY, bossW, bossH);
            Rectangle playerRect = player.getBounds();
            boolean playerAboveBoss = player.getY() + player.getHeight() - 10 < bossY;
            boolean falling = player.getVelocityY() > 0;
            if (playerRect.intersects(bossRect) && playerAboveBoss && falling && bossMoveCooldown == 0) {
                bossHits++;
                bossMoveCooldown = 30;
                player.setVelocityY(-10); // Bounce player up
                if (bossHits >= bossMaxHits) {
                    bossDefeated = true;
                    levelCompleted = true;
                } else {
                    // Move boss to a new random position on a platform
                    moveBossToRandomPlatform();
                }
            }
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
        boolean hitHazard = false;
        boolean onIce = false;
        for (Hazard hazard : hazards) {
            if (player.getBounds().intersects(hazard.getBounds())) {
                if (hazard.getType() == Hazard.HazardType.ICE) {
                    onIce = true;
                } else {
                    player.setX(50);
                    player.setY(HEI-200);
                    player.setVelocityX(0);
                    player.setVelocityY(0);
                    hitHazard = true;
                    break;
                }
            }
        }

        if (player.getY() > HEI) {
            player.setX(50);
            player.setY(HEI-200);
            player.setVelocityX(0);
            player.setVelocityY(0);
            hitHazard = true;
        }

        // Reset all disappearing platforms if player hit a hazard or fell off
        if (hitHazard) {
            for (Platform platform : platforms) {
                if (platform instanceof DisappearingPlatform) {
                    ((DisappearingPlatform) platform).reset();
                }
            }
        }

        // If on ice, make player slide (increase horizontal velocity persistence)
        if (onIce) {
            // Simulate sliding by keeping velocityX for longer and reducing friction
            float vx = player.getVelocityX();
            if (vx == 0) {
                // If not moving, give a small nudge in the last direction
                if (player.getMovingLeft()) {
                    player.setVelocityX(-4.5f);
                } else if (player.getMovingRight()) {
                    player.setVelocityX(4.5f);
                }
            } else {
                // Reduce friction: velocityX decays slower
                player.setVelocityX(vx * 0.98f + (player.getMovingLeft() ? -0.15f : 0) + (player.getMovingRight() ? 0.15f : 0));
            }
        }
    }

    private void moveBossToRandomPlatform() {
        // Pick a random platform (not ground) and place boss on it
        ArrayList<Platform> candidates = new ArrayList<>();
        for (Platform p : platforms) {
            if (p.getY() < HEI - 100 && p.getWidth() >= bossW) {
                candidates.add(p);
            }
        }
        if (!candidates.isEmpty()) {
            Platform p = candidates.get((int)(Math.random() * candidates.size()));
            bossX = p.getX() + p.getWidth()/2 - bossW/2;
            bossY = p.getY() - bossH;
        } else {
            // Fallback: random position
            bossX = 400 + (int)(Math.random() * 400);
            bossY = 200 + (int)(Math.random() * 200);
        }
    }

    public void render(Graphics g) {
        // Draw background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, WID, HEI);

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

        // Draw boss for level 5
        if (bossActive && !bossDefeated) {
            Graphics2D g2d = (Graphics2D) g;
            // Boss body
            g2d.setColor(new Color(120, 0, 255));
            g2d.fillOval(bossX, bossY, bossW, bossH);
            // Boss face
            g2d.setColor(Color.WHITE);
            g2d.fillOval(bossX + 20, bossY + 25, 15, 15);
            g2d.fillOval(bossX + 45, bossY + 25, 15, 15);
            g2d.setColor(Color.BLACK);
            g2d.fillOval(bossX + 25, bossY + 30, 6, 6);
            g2d.fillOval(bossX + 50, bossY + 30, 6, 6);
            g2d.setColor(new Color(255, 80, 120));
            g2d.fillArc(bossX + 25, bossY + 50, 30, 15, 0, -180);
            // Boss health
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 22));
            g2d.drawString("Boss: " + (bossMaxHits - bossHits) + " hits left", bossX + bossW/2 - 50, bossY - 10);
        }
    }
}
