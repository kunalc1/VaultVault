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
    private DisappearingPlatform topLayerDisappearingPlatform = null;
    private int topLayerY = -1;
    private Platform6 topLayerPlatform6 = null;
    private List<Platform6> platform6List = new ArrayList<>();
    private boolean platform6CascadeTriggered = false;

    public Level() {
        platforms = new ArrayList<>();
        hazards = new ArrayList<>();
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    public void addPlatform(Platform platform) {
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
                break;
            case 6:
                createLevel6DonkeyKong();
                break;
            default:
                createSampleLevel();
                break;
        }
    }

    private void createLevel1() {
        platformColor = new Color(0, 200, 0);
        backgroundColor = new Color(25, 25, 50);

        addPlatform(new Platform(100, HEI-120, 120, 20));
        addPlatform(new Platform(300, HEI-200, 120, 20));
        addPlatform(new MovingPlatform(500, HEI-250, 120, 20, 500, 700, 1.0f, true));
        addPlatform(new Platform(800, HEI-180, 120, 20));
        addHazard(new Hazard(420, HEI-100, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-120, 40, 70));
    }

    private void createLevel2() {
        platformColor = new Color(0, 150, 200);
        backgroundColor = new Color(40, 40, 70);

        addPlatform(new Platform(80, HEI-100, 100, 20));
        addPlatform(new DisappearingPlatform(250, HEI-180, 100, 20, 100));
        addPlatform(new Platform(400, HEI-260, 100, 20));
        addPlatform(new MovingPlatform(600, HEI-340, 100, 20, 600, 800, 1.5f, true));
        addPlatform(new Platform(850, HEI-420, 100, 20));
        addHazard(new Hazard(350, HEI-80, 120, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(700, HEI-80, 120, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(500, HEI-200, 80, 20, Hazard.HazardType.ELECTRIC));
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-190, 40, 70));
    }

    private void createLevel3() {
        platformColor = new Color(70, 120, 60);
        backgroundColor = new Color(36, 56, 36);

        addPlatform(new Platform(60, HEI-100, 80, 20));
        addPlatform(new DisappearingPlatform(200, HEI-180, 80, 20, 80));
        addPlatform(new MovingPlatform(350, HEI-260, 80, 20, 350, 600, 2.0f, true));
        addPlatform(new Platform(600, HEI-340, 80, 20));
        addPlatform(new DisappearingPlatform(800, HEI-420, 80, 20, 60));
        addPlatform(new Platform(900, HEI-500, 80, 20));
        addHazard(new Hazard(150, HEI-80, 100, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(500, HEI-80, 100, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(750, HEI-80, 100, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(700, HEI-300, 40, 40, Hazard.HazardType.SAW));
        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 80, HEI-270, 40, 70));
    }

    private void createLevel4() {
        platformColor = new Color(180, 220, 255);
        backgroundColor = new Color(120, 180, 240);

        addPlatform(new Platform(80, HEI-120, 120, 20));
        addHazard(new Hazard(260, HEI-200, 120, 20, Hazard.HazardType.ICE));
        addHazard(new Hazard(440, HEI-280, 120, 20, Hazard.HazardType.ICE));
        addPlatform(new MovingPlatform(620, HEI-360, 120, 20, 620, 820, 1.2f, true));
        addHazard(new Hazard(800, HEI-440, 120, 20, Hazard.HazardType.ICE));
        addPlatform(new Platform(980, HEI-520, 120, 20));

        addHazard(new Hazard(200, HEI-100, 80, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(380, HEI-180, 80, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(560, HEI-260, 80, 20, Hazard.HazardType.SAW));
        addHazard(new Hazard(740, HEI-340, 80, 20, Hazard.HazardType.ELECTRIC));
        addHazard(new Hazard(920, HEI-420, 80, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(1100, HEI-500, 80, 20, Hazard.HazardType.LAVA));

        addHazard(new Hazard(1160, HEI-600, 120, 20, Hazard.HazardType.ICE));
        addHazard(new Hazard(1280, HEI-580, 80, 20, Hazard.HazardType.SAW));
        addPlatform(new Platform(1360, HEI-680, 120, 20));
        addHazard(new Hazard(1480, HEI-660, 80, 20, Hazard.HazardType.ELECTRIC));

        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(WID - 100, HEI-730, 60, 70));
    }

    private void createLevel5() {
        platformColor = new Color(255, 50, 50);
        backgroundColor = new Color(10, 0, 0);

        addPlatform(new Platform(30, HEI-100, 70, 20));
        addHazard(new Hazard(120, HEI-80, 80, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(220, HEI-180, 70, 20, 40));
        addHazard(new Hazard(300, HEI-160, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new MovingPlatform(400, HEI-240, 70, 20, 400, 600, 2.5f, true));
        addHazard(new Hazard(500, HEI-220, 80, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(620, HEI-320, 70, 20, 30));
        addHazard(new Hazard(700, HEI-300, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new MovingPlatform(800, HEI-400, 70, 20, 800, 1000, 3.0f, true));
        addHazard(new Hazard(900, HEI-380, 80, 20, Hazard.HazardType.LAVA));
        addPlatform(new DisappearingPlatform(1020, HEI-480, 70, 20, 20));
        addHazard(new Hazard(1100, HEI-460, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new Platform(1200, HEI-560, 70, 20));
        addHazard(new Hazard(1270, HEI-540, 80, 20, Hazard.HazardType.LAVA));
        addPlatform(new MovingPlatform(1400, HEI-640, 70, 20, 1400, 1600, 3.5f, true));
        addHazard(new Hazard(1500, HEI-620, 80, 20, Hazard.HazardType.SPIKES));
        addPlatform(new DisappearingPlatform(1620, HEI-700, 70, 20, 10));
        addHazard(new Hazard(1700, HEI-680, 80, 20, Hazard.HazardType.LAVA));
        addPlatform(new Platform(1800, HEI-760, 70, 20));
        addHazard(new Hazard(1870, HEI-740, 80, 20, Hazard.HazardType.SPIKES));
        for (int i = 0; i < 8; i++) {
            int x = 2000 + i * 120;
            if (i % 2 == 0) {
                addPlatform(new MovingPlatform(x, HEI-800 + i*30, 90, 20, x, x+60, 2.0f + i*0.2f, true));
                if (i % 4 == 0) {
                    addHazard(new Hazard(x+100, HEI-780 + i*30, 60, 20, Hazard.HazardType.ELECTRIC));
                } else {
                    addHazard(new Hazard(x+100, HEI-780 + i*30, 60, 20, Hazard.HazardType.SAW));
                }
            } else {
                addPlatform(new DisappearingPlatform(x, HEI-800 + i*30, 90, 20, 30 + i*10));
                addHazard(new Hazard(x+100, HEI-780 + i*30, 80, 20, Hazard.HazardType.SPIKES));
            }
        }
        addPlatform(new Platform(0, HEI-50, 2500, 50));
        setGoal(new Goal(WID - 80, HEI-450, 40, 70));
    }

    private void createLevel6DonkeyKong() {
        platformColor = new Color(200, 100, 50);
        backgroundColor = new Color(30, 20, 40);

        int platformHeight = 20;
        int platformLength = 800;
        int gap = 120;
        int y = HEI - 100;
        boolean leftToRight = false;

        platform6List.clear();
        platform6CascadeTriggered = false;
        topLayerPlatform6 = null;

        for (int i = 0; i < 6; i++) {
            int x = leftToRight ? 80 : WID - platformLength - 80;
            addPlatform(new Platform(x, y, 180, platformHeight));
            if (i == 5) {
                Platform6 p6 = new Platform6(x + 180, y, 440, platformHeight, 60);
                addPlatform(p6);
                platform6List.add(p6);
                topLayerPlatform6 = p6;
                topLayerY = y;
            } else {
                DisappearingPlatform dp = new DisappearingPlatform(x + 180, y, 440, platformHeight, 60);
                addPlatform(dp);
            }
            if (i == 5) {
                int hazardX = x + 180 + 440;
                int hazardY = y - 18;
                addHazard(new Hazard(hazardX, hazardY, 20, 20, Hazard.HazardType.SPIKES));
            }
            addPlatform(new Platform(x + 620, y, 180, platformHeight));
            y -= gap;
            leftToRight = !leftToRight;
        }

        addPlatform(new Platform(0, HEI-50, WID, 50));
        setGoal(new Goal(80, y + gap - 80, 40, 70));
    }

    private void createTutorialLevel() {
        platformColor = new Color(120, 220, 255);
        backgroundColor = new Color(10, 10, 40);

        addPlatform(new Platform(50, HEI - 150, 120, 20));
        addPlatform(new Platform(220, HEI - 200, 100, 20));
        addPlatform(new Platform(370, HEI - 250, 80, 20));
        addPlatform(new Platform(500, HEI - 200, 100, 20));
        addPlatform(new Platform(650, HEI - 150, 120, 20));
        addPlatform(new Platform(820, HEI - 100, 100, 20));

        addPlatform(new Platform(320, HEI - 320, 60, 10));
        addPlatform(new Platform(620, HEI - 320, 60, 10));

        addHazard(new Hazard(170, HEI - 130, 50, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(450, HEI - 180, 40, 20, Hazard.HazardType.SPIKES));
        addHazard(new Hazard(300, HEI - 80, 120, 20, Hazard.HazardType.LAVA));
        addHazard(new Hazard(740, HEI - 80, 80, 20, Hazard.HazardType.LAVA));

        addPlatform(new Platform(0, HEI - 50, WID, 50));
        setGoal(new Goal(880, HEI - 170, 40, 70));
    }

    public void createSampleLevel() {
        addPlatform(new Platform(200, 400, 100, 20));
        addPlatform(new Platform(350, 350, 100, 20));
        addPlatform(new Platform(500, 300, 100, 20));
        addPlatform(new Platform(200, 250, 100, 20));
        addPlatform(new Platform(50, 200, 100, 20));
    }

    public void update() {
        for (Platform platform : platforms) {
            platform.update();
        }

        if (player != null) {
            player.applyGravity();
            player.update();
            checkCollisions();
        }

        if (goal != null) {
            goal.update();
            if (player.getBounds().intersects(goal.getBounds())) {
                levelCompleted = true;
            }
        }

        for (Hazard hazard : hazards) {
            hazard.update();
        }

        if (topLayerDisappearingPlatform != null && player != null) {
            if (Math.abs(player.getY() + player.getHeight() - topLayerY) < 10 &&
                player.getX() + player.getWidth() > topLayerDisappearingPlatform.getX() &&
                player.getX() < topLayerDisappearingPlatform.getX() + topLayerDisappearingPlatform.getWidth()) {
                topLayerDisappearingPlatform.triggerDisappear();
            }
        }

        if (topLayerPlatform6 != null && player != null) {
            if (Math.abs(player.getY() + player.getHeight() - topLayerY) < 10 &&
                player.getX() + player.getWidth() > topLayerPlatform6.getX() &&
                player.getX() < topLayerPlatform6.getX() + topLayerPlatform6.getWidth()) {
                topLayerPlatform6.triggerDisappear();
            }
        }

        if (!platform6List.isEmpty() && player != null) {
            for (Platform6 p6 : platform6List) {
                if (Math.abs(player.getY() + player.getHeight() - p6.getY()) < 10 &&
                    player.getX() + player.getWidth() > p6.getX() &&
                    player.getX() < p6.getX() + p6.getWidth()) {
                    p6.triggerDisappear();
                }
            }
        }

        if (!platform6List.isEmpty() && player != null && topLayerPlatform6 != null && !platform6CascadeTriggered) {
            if (Math.abs(player.getY() + player.getHeight() - topLayerY) < 10 &&
                player.getX() + player.getWidth() > topLayerPlatform6.getX() &&
                player.getX() < topLayerPlatform6.getX() + topLayerPlatform6.getWidth() &&
                player.getVelocityY() == 0) {
                for (Platform6 p6 : platform6List) {
                    p6.triggerDisappear();
                }
                platform6CascadeTriggered = true;
            }
        }

        if (platform6CascadeTriggered && player != null && player.getY() + player.getHeight() >= HEI - 50) {
            for (Platform6 p6 : platform6List) {
                p6.setVisible(true);
            }
            platform6CascadeTriggered = false;
        }
    }

    private void checkCollisions() {
        player.setOnGround(false);
        player.applyGravity();

        final int GROUND_TOLERANCE = 5;
        final int FRAMES_AHEAD = 5;

        boolean onIce = false;
        boolean wasOnIce = false;

        for (Platform platform : platforms) {
            if (platform instanceof Platform6 && !((Platform6) platform).isVisible()) {
                continue;
            }
            if (platform instanceof DisappearingPlatform && !((DisappearingPlatform) platform).isVisible()) {
                continue;
            }

            Rectangle playerBounds = player.getBounds();
            Rectangle platformBounds = platform.getBounds();

            if (platform instanceof Platform6 &&
                    ((Platform6) platform).isVisible() &&
                    player.getVelocityY() > 0 &&
                    player.getY() + player.getHeight() <= platform.getY() + GROUND_TOLERANCE &&
                    player.getY() + player.getHeight() + player.getVelocityY() >= platform.getY() &&
                    player.getX() + player.getWidth() > platform.getX() &&
                    player.getX() < platform.getX() + platform.getWidth()) {
                ((Platform6) platform).triggerDisappear();
            }

            if (platform instanceof DisappearingPlatform &&
                    ((DisappearingPlatform) platform).isVisible() &&
                    player.getVelocityY() > 0 &&
                    player.getY() + player.getHeight() <= platform.getY() - 5 &&
                    player.getY() + player.getHeight() + (player.getVelocityY() * FRAMES_AHEAD) >= platform.getY() &&
                    player.getX() + player.getWidth() > platform.getX() &&
                    player.getX() < platform.getX() + platform.getWidth()) {
                ((DisappearingPlatform) platform).triggerDisappear();
            }

            boolean closeToGround = player.getY() + player.getHeight() <= platform.getY() + GROUND_TOLERANCE &&
                    player.getY() + player.getHeight() + player.getVelocityY() >= platform.getY();

            if (closeToGround &&
                    player.getX() + player.getWidth() > platform.getX() &&
                    player.getX() < platform.getX() + platform.getWidth()) {
                player.setY(platform.getY() - player.getHeight());
                player.setVelocityY(0);
                player.setOnGround(true);

                for (Hazard hazard : hazards) {
                    if (hazard.getType() == Hazard.HazardType.ICE) {
                        Rectangle iceRect = hazard.getBounds();
                        if (player.getX() + player.getWidth() > iceRect.x &&
                            player.getX() < iceRect.x + iceRect.width &&
                            Math.abs((player.getY() + player.getHeight()) - iceRect.y) <= GROUND_TOLERANCE) {
                            onIce = true;
                        }
                    }
                }
            } else if (player.getTopBounds().intersects(platformBounds) && player.getVelocityY() < 0) {
                player.setY(platform.getY() + platform.getHeight());
                player.setVelocityY(0);
            }

            if (player.getLeftBounds().intersects(platformBounds) && player.getVelocityX() < 0) {
                player.setX(platform.getX() + platform.getWidth());
            } else if (player.getRightBounds().intersects(platformBounds) && player.getVelocityX() > 0) {
                player.setX(platform.getX() - player.getWidth());
            }
        }

        for (Hazard hazard : hazards) {
            if (hazard.getType() == Hazard.HazardType.ICE) {
                Rectangle iceRect = hazard.getBounds();
                boolean closeToIceGround = player.getY() + player.getHeight() <= iceRect.y + GROUND_TOLERANCE &&
                        player.getY() + player.getHeight() + player.getVelocityY() >= iceRect.y;

                if (closeToIceGround &&
                    player.getX() + player.getWidth() > iceRect.x &&
                    player.getX() < iceRect.x + iceRect.width) {
                    player.setY(iceRect.y - player.getHeight());
                    player.setVelocityY(0);
                    player.setOnGround(true);
                    onIce = true;
                }
            }
        }

        boolean hitHazard = false;
        for (Hazard hazard : hazards) {
            if (hazard.getType() == Hazard.HazardType.ICE) continue;
            if (player.getBounds().intersects(hazard.getBounds())) {
                player.setX(50);
                player.setY(HEI-200);
                player.setVelocityX(0);
                player.setVelocityY(0);
                hitHazard = true;
                break;
            }
        }

        if (player.getY() > HEI) {
            player.setX(50);
            player.setY(HEI-200);
            player.setVelocityX(0);
            player.setVelocityY(0);
            hitHazard = true;
        }

        if (hitHazard) {
            for (Platform platform : platforms) {
                if (platform instanceof DisappearingPlatform) {
                    ((DisappearingPlatform) platform).reset();
                }
            }
        }

        if (onIce) {
            float slideAccel = 0.5f;
            float maxSlideSpeed = 8.0f;
            float slideFriction = 0.985f;

            if (player.getMovingLeft()) {
                player.setVelocityX(Math.max(player.getVelocityX() - slideAccel, -maxSlideSpeed));
            } else if (player.getMovingRight()) {
                player.setVelocityX(Math.min(player.getVelocityX() + slideAccel, maxSlideSpeed));
            } else {
                player.setVelocityX(player.getVelocityX() * slideFriction);
                if (Math.abs(player.getVelocityX()) < 0.2f) {
                    player.setVelocityX(0);
                }
            }
        } else {
            if (!player.getMovingLeft() && !player.getMovingRight()) {
                player.setVelocityX(0);
            }
        }
    }

    public void render(Graphics g) {
        g.setColor(backgroundColor);
        g.fillRect(0, 0, WID, HEI);

        if (platformColor.equals(new Color(70, 120, 60))) {
            Graphics2D g2d = (Graphics2D) g;

            int islandW = 500;
            int islandH = 90;
            int islandX = 260;
            int islandY = 220;

            g2d.setColor(new Color(120, 100, 60, 230));
            g2d.fillRoundRect(islandX, islandY, islandW, islandH, 80, 60);
            g2d.setColor(new Color(70, 120, 60, 220));
            g2d.fillRoundRect(islandX, islandY, islandW, 38, 80, 38);

            g2d.setStroke(new BasicStroke(7f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(60, 90, 40, 180));
            for (int i = 0; i < 6; i++) {
                int vx = islandX + 60 + i * 70;
                int vy1 = islandY + islandH - 10;
                int vy2 = vy1 + 60 + (i % 2) * 30;
                int vy3 = vy2 + 30 + (i % 3) * 10;
                g2d.drawLine(vx, vy1, vx - 10 + (i % 2) * 20, vy2);
                g2d.drawLine(vx - 10 + (i % 2) * 20, vy2, vx + 5, vy3);
            }

            g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(40, 90, 40, 180));
            for (int i = 0; i < 6; i++) {
                int vineX = 120 + i * 140;
                int vineTop = 0;
                int vineBot = HEI / 2 + (i % 2 == 0 ? 40 : 0);
                int segments = 12;
                int[] xs = new int[segments + 1];
                int[] ys = new int[segments + 1];
                for (int j = 0; j <= segments; j++) {
                    xs[j] = vineX + (int)(Math.sin(j * 0.7 + i) * 12);
                    ys[j] = vineTop + (vineBot - vineTop) * j / segments;
                }
                g2d.drawPolyline(xs, ys, segments + 1);

                g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setColor(new Color(60, 160, 60, 180));
                for (int j = 2; j < segments; j += 2) {
                    int rungY = ys[j];
                    int rungX1 = xs[j] - 10;
                    int rungX2 = xs[j] + 10;
                    g2d.drawLine(rungX1, rungY, rungX2, rungY);
                }
                g2d.setStroke(new BasicStroke(5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.setColor(new Color(40, 90, 40, 180));
            }

            int pyramidBaseW = 420;
            int pyramidBaseH = 160;
            int pyramidX = islandX + (islandW - pyramidBaseW) / 2;
            int pyramidY = islandY - pyramidBaseH + 38;
            int levels = 7;
            Color stone = new Color(220, 190, 120, 220);
            Color moss = new Color(170, 160, 90, 90);

            for (int l = levels - 1; l >= 0; l--) {
                int stepW = pyramidBaseW - l * 60;
                int stepH = pyramidBaseH / levels;
                int stepX = pyramidX + l * 30;
                int stepY = pyramidY + (levels - 1 - l) * stepH;
                g2d.setColor(stone);
                g2d.fillRect(stepX, stepY, stepW, stepH);
                if (l % 2 == 0) {
                    g2d.setColor(moss);
                    g2d.fillRect(stepX, stepY, stepW, 8);
                }
                g2d.setColor(new Color(180, 150, 90, 90));
                for (int cx = stepX; cx < stepX + stepW; cx += 32) {
                    g2d.drawRect(cx, stepY, 32, stepH);
                }
            }

            int topW = 48, topH = 36;
            int topX = pyramidX + pyramidBaseW / 2 - topW / 2;
            int topY = pyramidY - topH + 18 + pyramidBaseH - (pyramidBaseH / levels);
            g2d.setColor(stone.darker());
            g2d.fillRect(topX, topY, topW, topH);

            int orbX = topX + topW / 2 - 18;
            int orbY = topY - 38;
            for (int r = 32; r > 0; r -= 8) {
                g2d.setColor(new Color(255, 240, 180, 30 + r * 2));
                g2d.fillOval(orbX + 18 - r, orbY + 18 - r, r * 2, r * 2);
            }
            g2d.setColor(new Color(255, 255, 200, 220));
            g2d.fillOval(orbX + 8, orbY + 8, 20, 20);

            g2d.setColor(new Color(180, 140, 80, 220));
            int doorW = 36, doorH = 44;
            int doorX = pyramidX + pyramidBaseW / 2 - doorW / 2;
            int doorY = pyramidY + pyramidBaseH - (pyramidBaseH / levels) - 8;
            g2d.fillRoundRect(doorX, doorY, doorW, doorH, 18, 18);
            g2d.setColor(new Color(255, 230, 170, 180));
            g2d.drawArc(doorX, doorY - 10, doorW, 20, 0, 180);

            g2d.setColor(new Color(230, 210, 140, 180));
            g2d.fillRect(pyramidX + 24, pyramidY + pyramidBaseH - 60, 12, 60);
            g2d.fillRect(pyramidX + pyramidBaseW - 36, pyramidY + pyramidBaseH - 60, 12, 60);

            g2d.setColor(new Color(210, 180, 120, 180));
            for (int s = 0; s < 3; s++) {
                int stepY = pyramidY - (s + 1) * 8;
                int stepW = pyramidBaseW - s * 40;
                int stepX = pyramidX + s * 20;
                g2d.fillRect(stepX, stepY, stepW, 8);
            }

            g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2d.setColor(new Color(255, 255, 200, 120));
            for (int i = -2; i <= 2; i++) {
                g2d.drawLine(topX + topW / 2, orbY + 18, topX + topW / 2 + i * 30, orbY - 40);
            }
        }

        if (platformColor.equals(new Color(180, 220, 255))) {
            Graphics2D g2d = (Graphics2D) g;
            int golemX = WID - 220;
            int golemY = HEI - 180;
            g2d.setColor(new Color(240, 250, 255, 230));
            g2d.fillOval(golemX, golemY + 60, 80, 80);
            g2d.fillOval(golemX + 10, golemY + 20, 60, 60);
            g2d.fillOval(golemX + 22, golemY, 36, 36);

            g2d.setColor(Color.BLACK);
            g2d.fillOval(golemX + 32, golemY + 10, 6, 6);
            g2d.fillOval(golemX + 42, golemY + 10, 6, 6);
            for (int i = 0; i < 5; i++) {
                g2d.fillOval(golemX + 30 + i * 5, golemY + 28, 4, 4);
            }

            g2d.setColor(new Color(255, 140, 40));
            Polygon nose = new Polygon(
                new int[]{golemX + 40, golemX + 54, golemX + 42},
                new int[]{golemY + 18, golemY + 22, golemY + 22},
                3
            );
            g2d.fillPolygon(nose);

            g2d.setColor(new Color(120, 80, 40));
            g2d.setStroke(new BasicStroke(4f));
            g2d.drawLine(golemX + 18, golemY + 50, golemX - 20, golemY + 10);
            g2d.drawLine(golemX + 62, golemY + 50, golemX + 110, golemY + 10);

            g2d.setColor(Color.BLACK);
            g2d.fillOval(golemX + 38, golemY + 45, 6, 6);
            g2d.fillOval(golemX + 38, golemY + 65, 6, 6);
            g2d.fillOval(golemX + 38, golemY + 85, 6, 6);
        }

        if (platformColor.equals(new Color(200, 100, 50))) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(180, 180, 80, 180));
            int platformHeight = 20;
            int platformLength = 800;
            int gap = 120;
            int y = HEI - 100;
            boolean leftToRight = false;
            for (int i = 0; i < 5; i++) {
                int x = leftToRight ? 80 : WID - platformLength - 80;
                int ladderX = leftToRight ? x + platformLength - 100 : x + 100;
                int ladderYTop = y - gap + platformHeight;
                int ladderYBot = y + platformHeight;
                g2d.fillRect(ladderX, ladderYTop, 8, ladderYBot - ladderYTop);
                g2d.fillRect(ladderX + 32, ladderYTop, 8, ladderYBot - ladderYTop);
                for (int rung = 0; rung < 5; rung++) {
                    int rungY = ladderYTop + 10 + rung * ((ladderYBot - ladderYTop - 20) / 4);
                    g2d.fillRect(ladderX, rungY, 40, 4);
                }
                y -= gap;
                leftToRight = !leftToRight;
            }
        }

        for (Platform platform : platforms) {
            if (platform.getColor() == null) {
                platform.setColor(platformColor);
            }
            platform.render(g);
        }

        for (Hazard hazard : hazards) {
            hazard.render(g);
        }

        if (goal != null) {
            goal.render(g);
        }

        if (player != null) {
            player.render(g);
        }
    }
}
