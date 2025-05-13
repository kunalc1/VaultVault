import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class VaultVault extends JPanel implements Runnable {
    public static final int WIDTH = 1024; // 800px
    public static final int HEIGHT = 768; // 600px
    private static final int FPS = 60;

    // Game states
    public enum GameState {
        MENU,
        PLAYING,
        GAME_OVER
    }

    private GameState currentState = GameState.MENU;
    private boolean running;
    private Thread gameThread;
    private List<Level> levels;
    private int currentLevelIndex = 0;
    private Player player;
    private MainMenu mainMenu;
    // Track completed levels
    private boolean[] completedLevels;

    // Timer related fields
    private long levelStartTime;
    private long currentTime;
    private boolean timerRunning = false;

    // Animation for level completion
    private boolean levelCompletionAnimation = false;
    private int animationTicks = 0;
    private final int MAX_ANIMATION_TICKS = 60;
    
    // Debug mode flag
    private boolean debugMode = true;

    public VaultVault() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        setFocusable(true);

        // Create player
        player = new Player(50, 300);

        // Initialize levels
        levels = new ArrayList<>();
        createLevels();
        
        // Initialize completed levels array
        completedLevels = new boolean[levels.size()];

        // Create main menu
        mainMenu = new MainMenu(this);

        // Set up keyboard input
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (currentState == GameState.PLAYING) {
                    handlePlayingKeyPressed(e);
                } else if (currentState == GameState.MENU) {
                    mainMenu.handleKeyPressed(e);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (currentState == GameState.PLAYING) {
                    handlePlayingKeyReleased(e);
                }
            }
        });
        
        // Add mouse listener for debug platform creation
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (debugMode && currentState == GameState.PLAYING) {
                    int x = e.getX();
                    int y = e.getY();
                    createPlatformAtPosition(x, y);
                    System.out.println("Created platform at: x=" + x + ", y=" + y);
                }
            }
        });
    }
    
    // Method to create a platform at click position
    private void createPlatformAtPosition(int x, int y) {
        if (currentLevelIndex >= 0 && currentLevelIndex < levels.size()) {
            Level currentLevel = levels.get(currentLevelIndex);
            // Create a platform centered on the click point
            int platformWidth = 100;
            int platformHeight = 20;
            Platform platform = new Platform(x - platformWidth/2, y - platformHeight/2, platformWidth, platformHeight);
            currentLevel.addPlatform(platform);
        }
    }

    private void handlePlayingKeyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_UP:
                player.jump();
                break;
            case KeyEvent.VK_ESCAPE:
                setGameState(GameState.MENU);
                break;
        }
    }

    private void handlePlayingKeyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                player.setMovingLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                player.setMovingRight(false);
                break;
        }
    }

    private void createLevels() {
        // Level 1 - Basic platforms
        Level level1 = new Level();
        level1.addPlayer(player);
        level1.createLevel(1);
        levels.add(level1);

        // Level 2 - More complex platforms
        Level level2 = new Level();
        level2.addPlayer(player);
        level2.createLevel(2);
        levels.add(level2);

        // Level 3 - Advanced layout
        Level level3 = new Level();
        level3.addPlayer(player);
        level3.createLevel(3);
        levels.add(level3);
    }

    public void startGame(int levelIndex) {
        currentLevelIndex = levelIndex;
        if (currentLevelIndex >= levels.size()) {
            currentLevelIndex = 0;
        }

        // Reset player position
        player.setX(50);
        player.setY(300);
        player.setVelocityX(0);
        player.setVelocityY(0);

        // Reset the current level (don't auto-complete)
        Level currentLevel = levels.get(currentLevelIndex);
        currentLevel.createLevel(currentLevelIndex + 1); // Recreate level from scratch
        currentLevel.addPlayer(player);

        // Reset level completion animation
        levelCompletionAnimation = false;
        animationTicks = 0;
        
        // Reset and start the timer
        levelStartTime = System.currentTimeMillis();
        timerRunning = true;

        setGameState(GameState.PLAYING);
    }

    public void setGameState(GameState state) {
        this.currentState = state;
    }

    public void start() {
        if (gameThread == null || !running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerUpdate = 1000000000.0 / FPS;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerUpdate;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta--;
            }

            repaint();

            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (currentState == GameState.PLAYING) {
            Level currentLevel = levels.get(currentLevelIndex);
            currentLevel.update();
            
            // Update timer if it's running
            if (timerRunning) {
                currentTime = System.currentTimeMillis();
            }

            // Check if level is completed by reaching the goal
            if (currentLevel.isLevelCompleted() && !levelCompletionAnimation) {
                levelCompletionAnimation = true;
                animationTicks = 0;
                timerRunning = false; // Stop the timer when level is completed
            }

            // Handle level completion animation
            if (levelCompletionAnimation) {
                animationTicks++;
                if (animationTicks >= MAX_ANIMATION_TICKS) {
                    nextLevel();
                }
            }
        }
    }

    private void nextLevel() {
        // Mark current level as completed
        if (currentLevelIndex >= 0 && currentLevelIndex < completedLevels.length) {
            completedLevels[currentLevelIndex] = true;
        }
        
        currentLevelIndex++;
        if (currentLevelIndex >= levels.size()) {
            // Game completed, return to menu
            currentLevelIndex = 0;
            setGameState(GameState.MENU);
        } else {
            // Start next level
            player.setX(50);
            player.setY(300);
            player.setVelocityX(0);
            player.setVelocityY(0);
            
            // Reset and restart timer for next level
            levelStartTime = System.currentTimeMillis();
            timerRunning = true;
        }

        // Reset level completion animation
        levelCompletionAnimation = false;
        animationTicks = 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentState == GameState.PLAYING) {
            levels.get(currentLevelIndex).render(g);

            // Display level information
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.drawString("Level " + (currentLevelIndex + 1), 20, 30);
            
            // Display timer in top left with milliseconds
            if (timerRunning) {
                long elapsedMillis = currentTime - levelStartTime;
                int millis = (int)(elapsedMillis % 1000);
                int seconds = (int)(elapsedMillis / 1000);
                int minutes = seconds / 60;
                seconds %= 60;
                String timeString = String.format("%02d:%02d.%03d", minutes, seconds, millis);
                g.drawString("Time: " + timeString, 20, 55);
            }
            
            // Display debug mode indicator if enabled
            if (debugMode) {
                g.setColor(Color.ORANGE);
                g.drawString("DEBUG MODE - Click to create platforms", 20, 80);
            }
            
            g.setColor(Color.WHITE);
            g.drawString("ESC - Menu", WIDTH - 100, 30);

            // Draw level completion animation if active
            if (levelCompletionAnimation) {
                drawLevelCompletionAnimation(g);
            }
        } else if (currentState == GameState.MENU) {
            mainMenu.render(g);
        }
    }

    private void drawLevelCompletionAnimation(Graphics g) {
        float alpha = Math.min(1.0f, (float)animationTicks / 30);
        Color overlayColor = new Color(1.0f, 1.0f, 1.0f, alpha * 0.5f);

        g.setColor(overlayColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(new Color(255, 215, 0)); // Gold
        g.setFont(new Font("Arial", Font.BOLD, 48));
        FontMetrics metrics = g.getFontMetrics();
        String message = "Level Complete!";
        int x = (WIDTH - metrics.stringWidth(message)) / 2;
        int y = HEIGHT / 2;

        g.drawString(message, x, y);

        // Display "Next Level" message
        if (animationTicks > 20) {
            g.setFont(new Font("Arial", Font.PLAIN, 24));
            metrics = g.getFontMetrics();
            message = "Preparing next level...";
            x = (WIDTH - metrics.stringWidth(message)) / 2;
            y = HEIGHT / 2 + 50;
            g.drawString(message, x, y);
        }
    }

    public int getLevelCount() {
        return levels.size();
    }

    public boolean isLevelCompleted(int levelIndex) {
        if (levelIndex >= 0 && levelIndex < completedLevels.length) {
            return completedLevels[levelIndex];
        }
        return false;
    }
    
    public int getCompletedLevelCount() {
        int count = 0;
        for (boolean completed : completedLevels) {
            if (completed) count++;
        }
        return count;
    }

    // Getter and setter for debug mode
    public boolean isDebugMode() {
        return debugMode;
    }
    
    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Vault Vault");
        VaultVault game = new VaultVault();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        game.start();
    }
}
