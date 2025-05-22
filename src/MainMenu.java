import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class MainMenu {
    private VaultVault game;
    private enum MenuState { MAIN, LEVEL_SELECT }
    private MenuState menuState = MenuState.MAIN;
    private int selectedMenuItem = 0;
    private int selectedLevel = 0;
    private final Font titleFont = new Font("Segoe UI", Font.BOLD, 54);
    private final Font menuFont = new Font("Segoe UI", Font.BOLD, 28);
    private final Font glowFont = new Font("Segoe UI", Font.BOLD, 32);
    private static final int WID = VaultVault.WIDTH;
    private static final int HEI = VaultVault.HEIGHT;

    // For animated background circles
    private static final int CIRCLE_COUNT = 12;
    private final float[] circleX = new float[CIRCLE_COUNT];
    private final float[] circleY = new float[CIRCLE_COUNT];
    private final float[] circleR = new float[CIRCLE_COUNT];
    private final float[] circleSpeed = new float[CIRCLE_COUNT];
    private final Color[] circleColors = new Color[CIRCLE_COUNT];
    private final Random rand = new Random();

    {
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            circleX[i] = rand.nextInt(WID);
            circleY[i] = rand.nextInt(HEI);
            circleR[i] = 40 + rand.nextInt(60);
            circleSpeed[i] = 0.2f + rand.nextFloat() * 0.4f;
            float hue = 0.55f + rand.nextFloat() * 0.25f;
            circleColors[i] = new Color(Color.HSBtoRGB(hue, 0.5f, 1.0f));
            circleColors[i] = new Color(circleColors[i].getRed(), circleColors[i].getGreen(), circleColors[i].getBlue(), 60);
        }
    }

    public MainMenu(VaultVault game) {
        this.game = game;
    }

    public void handleKeyPressed(KeyEvent e) {
        if (menuState == MenuState.MAIN) {
            handleMainMenuKeyPressed(e);
        } else if (menuState == MenuState.LEVEL_SELECT) {
            handleLevelSelectKeyPressed(e);
        }
    }

    private void handleMainMenuKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                selectedMenuItem = Math.max(0, selectedMenuItem - 1);
                break;
            case KeyEvent.VK_DOWN:
                selectedMenuItem = Math.min(2, selectedMenuItem + 1);
                break;
            case KeyEvent.VK_ENTER:
                if (selectedMenuItem == 0) {
                    menuState = MenuState.LEVEL_SELECT;
                } else if (selectedMenuItem == 1) {
                    game.setDebugMode(!game.isDebugMode());
                } else if (selectedMenuItem == 2) {
                    System.exit(0);
                }
                break;
        }
    }

    private void handleLevelSelectKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                selectedLevel = Math.max(0, selectedLevel - 1);
                break;
            case KeyEvent.VK_RIGHT:
                selectedLevel = Math.min(game.getLevelCount() - 1, selectedLevel + 1);
                break;
            case KeyEvent.VK_ENTER:
                game.startGame(selectedLevel);
                break;
            case KeyEvent.VK_ESCAPE:
                menuState = MenuState.MAIN;
                break;
        }
    }

    public void render(Graphics g) {
        updateCircles();
        if (menuState == MenuState.MAIN) {
            renderMainMenu(g);
        } else if (menuState == MenuState.LEVEL_SELECT) {
            renderLevelSelect(g);
        }
    }

    private void updateCircles() {
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            circleY[i] += circleSpeed[i];
            if (circleY[i] - circleR[i] > HEI) {
                circleY[i] = -circleR[i];
                circleX[i] = rand.nextInt(WID);
            }
        }
    }

    private void renderBackground(Graphics2D g2d) {
        // Blue to purple gradient
        GradientPaint gp = new GradientPaint(0, 0, new Color(40, 60, 120), WID, HEI, new Color(120, 40, 160));
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, WID, HEI);

        // Floating circles
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            g2d.setColor(circleColors[i]);
            g2d.fill(new Ellipse2D.Float(circleX[i] - circleR[i], circleY[i] - circleR[i], circleR[i]*2, circleR[i]*2));
        }
    }

    private void renderMainMenu(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        renderBackground(g2d);

        // Neon border around menu area
        int menuW = 420, menuH = 260, menuX = WID/2 - menuW/2, menuY = 200;
        g2d.setColor(new Color(0,255,255,80));
        g2d.setStroke(new BasicStroke(10f));
        g2d.drawRoundRect(menuX-20, menuY-60, menuW+40, menuH+100, 40, 40);
        g2d.setColor(new Color(0,255,255,180));
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRoundRect(menuX-20, menuY-60, menuW+40, menuH+100, 40, 40);

        // Draw title with neon glow
        g.setFont(titleFont);
        drawNeonCenteredString(g2d, "VAULT VAULT", WID/2, 140, new Color(0,255,255), new Color(120,0,255), 8);

        // Menu item positions
        int[] yPositions = {260, 330, 400};
        String[] menuItems = {"Play Game", "Debug Mode: " + (game.isDebugMode() ? "ON" : "OFF"), "Exit"};
        int[] boxWidths = {220, 320, 180};
        int boxHeight = 50;

        for (int i = 0; i < menuItems.length; i++) {
            int boxX = WID/2 - boxWidths[i]/2;
            int boxY = yPositions[i] - boxHeight/2;

            if (selectedMenuItem == i) {
                // Neon glow effect
                g2d.setColor(new Color(0,255,255,80));
                g2d.fillRoundRect(boxX-10, boxY-10, boxWidths[i]+20, boxHeight+20, 32, 32);

                // Highlighted background
                g2d.setColor(new Color(0, 180, 255, 180));
                g2d.fillRoundRect(boxX, boxY, boxWidths[i], boxHeight, 24, 24);

                // Outline
                g2d.setStroke(new BasicStroke(3f));
                g2d.setColor(new Color(0,255,255,220));
                g2d.drawRoundRect(boxX, boxY, boxWidths[i], boxHeight, 24, 24);

                // Neon text
                g.setFont(glowFont);
                drawNeonCenteredString(g2d, menuItems[i], WID/2, yPositions[i]+10, new Color(0,255,255), new Color(120,0,255), 4);
            } else {
                g2d.setColor(new Color(255,255,255,30));
                g2d.fillRoundRect(boxX, boxY, boxWidths[i], boxHeight, 24, 24);

                g.setFont(menuFont);
                drawCenteredString(g, menuItems[i], WID/2, yPositions[i]+8, new Color(220,220,255));
            }
        }

        // Decorative corner triangles
        Polygon tri1 = new Polygon(new int[]{menuX-30, menuX-10, menuX-30}, new int[]{menuY-70, menuY-70, menuY-50}, 3);
        Polygon tri2 = new Polygon(new int[]{menuX+menuW+30, menuX+menuW+10, menuX+menuW+30}, new int[]{menuY+menuH+110, menuY+menuH+110, menuY+menuH+90}, 3);
        g2d.setColor(new Color(0,255,255,120));
        g2d.fillPolygon(tri1);
        g2d.fillPolygon(tri2);

        // Instructions
        g.setColor(new Color(180, 220, 255));
        g.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        drawCenteredString(g, "Use ↑/↓ to navigate, ENTER to select", WID/2, HEI-60, new Color(180,220,255));
    }

    private void renderLevelSelect(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        renderBackground(g2d);

        int selW = 340, selH = 180, selX = WID/2 - selW/2, selY = 200;
        g2d.setColor(new Color(120,0,255,80));
        g2d.setStroke(new BasicStroke(8f));
        g2d.drawRoundRect(selX-20, selY-60, selW+40, selH+100, 40, 40);

        g.setFont(new Font("Segoe UI", Font.BOLD, 40));
        drawNeonCenteredString(g2d, "SELECT LEVEL", WID/2, 140, new Color(120,0,255), new Color(0,255,255), 6);

        g.setFont(menuFont);

        int boxW = 180, boxH = 80;
        int boxX = WID/2 - boxW/2, boxY = 240;
        g2d.setColor(new Color(120,0,255,40));
        g2d.fillRoundRect(boxX, boxY, boxW, boxH, 28, 28);

        g2d.setColor(new Color(0,255,255,180));
        g2d.setStroke(new BasicStroke(3f));
        g2d.drawRoundRect(boxX, boxY, boxW, boxH, 28, 28);

        // Updated level name display for new level 6
        String levelName;
        if (selectedLevel == 0) {
            levelName = "Tutorial";
        } else if (selectedLevel == 6) {
            levelName = "Donkey Kong";
        } else {
            levelName = "Level " + selectedLevel;
        }
        g.setFont(glowFont);
        drawNeonCenteredString(g2d, levelName, WID/2, boxY + boxH/2 + 12, new Color(0,255,255), new Color(120,0,255), 3);

        if (game.isLevelCompleted(selectedLevel)) {
            g.setFont(new Font("Segoe UI", Font.BOLD, 36));
            g.setColor(new Color(80, 255, 220));
            drawCenteredString(g, "✓", WID/2 + 60, boxY + boxH/2 + 10, new Color(80,255,220));
        }

        g2d.setColor(new Color(0,255,255,180));
        int arrowY = boxY + boxH/2 - 15;
        int[] leftX = {WID/2-120, WID/2-100, WID/2-100};
        int[] leftY = {arrowY+15, arrowY, arrowY+30};
        g2d.fillPolygon(leftX, leftY, 3);

        int[] rightX = {WID/2+120, WID/2+100, WID/2+100};
        int[] rightY = {arrowY+15, arrowY, arrowY+30};
        g2d.fillPolygon(rightX, rightY, 3);

        g.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        drawCenteredString(g, "Completed: " + game.getCompletedLevelCount() + "/" + game.getLevelCount(), WID/2, 420, new Color(180,255,255));
        drawCenteredString(g, "Use ←/→ to change level, ENTER to start, ESC to go back", WID/2, 480, new Color(180,220,255));
    }

    private void drawCenteredString(Graphics g, String text, int x, int y, Color color) {
        g.setColor(color);
        FontMetrics metrics = g.getFontMetrics();
        int textX = x - metrics.stringWidth(text) / 2;
        g.drawString(text, textX, y);
    }

    private void drawCenteredString(Graphics g, String text, int x, int y) {
        drawCenteredString(g, text, x, y, Color.WHITE);
    }

    private void drawNeonCenteredString(Graphics2D g2d, String text, int x, int y, Color glow, Color altGlow, int glowSize) {
        FontMetrics metrics = g2d.getFontMetrics();
        int textX = x - metrics.stringWidth(text) / 2;
        for (int i = glowSize; i > 0; i--) {
            g2d.setColor(new Color(glow.getRed(), glow.getGreen(), glow.getBlue(), 30));
            g2d.drawString(text, textX - i, y - i);
            g2d.drawString(text, textX + i, y + i);
            g2d.setColor(new Color(altGlow.getRed(), altGlow.getGreen(), altGlow.getBlue(), 20));
            g2d.drawString(text, textX + i, y - i);
            g2d.drawString(text, textX - i, y + i);
        }
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, textX, y);
    }
}
