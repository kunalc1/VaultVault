import java.awt.*;
import java.awt.event.KeyEvent;

public class MainMenu {
    private VaultVault game;
    private enum MenuState { MAIN, LEVEL_SELECT }
    private MenuState menuState = MenuState.MAIN;
    private int selectedMenuItem = 0;
    private int selectedLevel = 0;
    private final Font titleFont = new Font("Arial", Font.BOLD, 48);
    private final Font menuFont = new Font("Arial", Font.BOLD, 24);
    private static final int WID = VaultVault.WIDTH;
    private static final int HEI = VaultVault.HEIGHT;
    
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
                selectedMenuItem = Math.min(2, selectedMenuItem + 1); // Now 3 options (0, 1, 2)
                break;
            case KeyEvent.VK_ENTER:
                if (selectedMenuItem == 0) {
                    // Start Game
                    menuState = MenuState.LEVEL_SELECT;
                } else if (selectedMenuItem == 1) {
                    // Toggle Debug Mode
                    game.setDebugMode(!game.isDebugMode());
                } else if (selectedMenuItem == 2) {
                    // Exit Game
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
        if (menuState == MenuState.MAIN) {
            renderMainMenu(g);
        } else if (menuState == MenuState.LEVEL_SELECT) {
            renderLevelSelect(g);
        }
    }
    
    private void renderMainMenu(Graphics g) {
        // Draw title
        g.setFont(titleFont);
        g.setColor(Color.WHITE);
        drawCenteredString(g, "VAULT VAULT", WID/2, 150);
        
        // Draw menu items
        g.setFont(menuFont);
        
        // Play Game option
        if (selectedMenuItem == 0) {
            g.setColor(Color.YELLOW);
            g.fillRect(WID/2 - 75, 250, 150, 40);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        drawCenteredString(g, "Play Game", WID/2, 280);
        
        // Debug Mode option
        if (selectedMenuItem == 1) {
            g.setColor(Color.YELLOW);
            g.fillRect(WID/2 - 125, 310, 250, 40);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        // Show current debug mode status
        String debugStatus = game.isDebugMode() ? "ON" : "OFF";
        drawCenteredString(g, "Debug Mode: " + debugStatus, WID/2, 340);
        
        // Exit option
        if (selectedMenuItem == 2) {
            g.setColor(Color.YELLOW);
            g.fillRect(WID/2-75, 370, 150, 40);
            g.setColor(Color.BLACK);
        } else {
            g.setColor(Color.WHITE);
        }
        drawCenteredString(g, "Exit", WID/2, 400);
        
        // Instructions
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        drawCenteredString(g, "Use UP/DOWN arrows to navigate, ENTER to select", WID/2, HEI-75);
    }
    
    private void renderLevelSelect(Graphics g) {
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.setColor(Color.WHITE);
        drawCenteredString(g, "SELECT LEVEL", WID/2, 180);
        
        g.setFont(menuFont);
        
        // Draw level selection
        g.setColor(Color.WHITE);
        g.fillRect(WID/2-50, 250, 100, 60);
        
        g.setColor(Color.BLACK);
        drawCenteredString(g, "Level " + (selectedLevel + 1), WID/2, 285);
        
        // Show completion indicator if level is completed
        if (game.isLevelCompleted(selectedLevel)) {
            g.setColor(Color.GREEN);
            drawCenteredString(g, "✓", WID/2+35, 285); // Checkmark to indicate completion
        }
        
        // Draw arrows
        g.setColor(Color.YELLOW);
        g.fillRect(WID/2-90, 270, 20, 20);
        g.fillRect(WID/2+70, 270, 20, 20);
        
        g.setColor(Color.BLACK);
        g.drawString("<", WID/2-88, 286);
        g.drawString(">", WID/2+72, 286);
        
        // Display completion progress
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        drawCenteredString(g, 
                "Completed: " + game.getCompletedLevelCount() + "/" + game.getLevelCount(), 
                WID/2, 450);
        
        // Instructions
        drawCenteredString(g, "Use LEFT/RIGHT to change level, ENTER to start, ESC to go back", WID/2, 500);
    }
    
    private void drawCenteredString(Graphics g, String text, int x, int y) {
        FontMetrics metrics = g.getFontMetrics();
        int textX = x - metrics.stringWidth(text) / 2;
        g.drawString(text, textX, y);
    }
}
