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
    // Animation for level change
    private boolean levelChangeAnimation = false;
    private int levelChangeTicks = 0;
    private final int MAX_LEVEL_CHANGE_TICKS = 45;
    private int nextLevelToStart = -1;

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

        // Initialize completed levels array (after all levels are added)
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
        // Level 0 - Tutorial
        Level tutorial = new Level();
        tutorial.addPlayer(player);
        tutorial.createLevel(0);
        levels.add(tutorial);

        // Level 1 - Easy
        Level level1 = new Level();
        level1.addPlayer(player);
        level1.createLevel(1);
        levels.add(level1);

        // Level 2 - Medium
        Level level2 = new Level();
        level2.addPlayer(player);
        level2.createLevel(2);
        levels.add(level2);

        // Level 3 - Hard
        Level level3 = new Level();
        level3.addPlayer(player);
        level3.createLevel(3);
        levels.add(level3);

        // Level 4 - Very Hard
        Level level4 = new Level();
        level4.addPlayer(player);
        level4.createLevel(4);
        levels.add(level4);

        // Level 5 - Insane
        Level level5 = new Level();
        level5.addPlayer(player);
        level5.createLevel(5);
        levels.add(level5);

        // Level 6 - Donkey Kong
        Level level6 = new Level();
        level6.addPlayer(player);
        level6.createLevel(6);
        levels.add(level6);
    }

    // Add these methods to support MainMenu and level logic

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public int getLevelCount() {
        return levels.size();
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

        // Reset the current level
        Level currentLevel = levels.get(currentLevelIndex);
        currentLevel.createLevel(currentLevelIndex);
        currentLevel.addPlayer(player);

        // Reset level completion animation
        levelCompletionAnimation = false;
        animationTicks = 0;

        // Reset and start the timer
        levelStartTime = System.currentTimeMillis();
        timerRunning = true;

        setGameState(GameState.PLAYING);
    }

    public boolean isLevelCompleted(int idx) {
        if (idx >= 0 && idx < completedLevels.length) {
            return completedLevels[idx];
        }
        return false;
    }

    public int getCompletedLevelCount() {
        int count = 0;
        for (boolean b : completedLevels) {
            if (b) count++;
        }
        return count;
    }

    private boolean nextLevelPending = false;

    @Override
    public void run() {
        running = true;
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / FPS;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                update();
                delta -= 1;
            }

            repaint();

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
        if (currentState == GameState.PLAYING) {
            Level currentLevel = levels.get(currentLevelIndex);
            currentLevel.update();

            // Check for level completion
            if (currentLevel.isCompleted() && !levelCompletionAnimation && !levelChangeAnimation) {
                completedLevels[currentLevelIndex] = true;
                levelCompletionAnimation = true;
                animationTicks = 0;
            }

            // Handle level completion animation
            if (levelCompletionAnimation) {
                animationTicks++;
                if (animationTicks >= MAX_ANIMATION_TICKS) {
                    levelCompletionAnimation = false;
                    // Start level change animation
                    if (currentLevelIndex + 1 < levels.size()) {
                        levelChangeAnimation = true;
                        levelChangeTicks = 0;
                        nextLevelToStart = currentLevelIndex + 1;
                    } else {
                        // If last level, return to menu after animation
                        setGameState(GameState.MENU);
                    }
                }
            } else if (levelChangeAnimation) {
                levelChangeTicks++;
                if (levelChangeTicks >= MAX_LEVEL_CHANGE_TICKS) {
                    levelChangeAnimation = false;
                    if (nextLevelToStart >= 0 && nextLevelToStart < levels.size()) {
                        startGame(nextLevelToStart);
                        nextLevelToStart = -1;
                    }
                }
            } else {
                // Only update timer if not in animation
                if (timerRunning) {
                    currentTime = System.currentTimeMillis();
                }
            }
        }
    }

    private void setGameState(GameState newState) {
        currentState = newState;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentState == GameState.PLAYING) {
            Level currentLevel = levels.get(currentLevelIndex);
            currentLevel.render(g);

            // Draw timer
            if (timerRunning && !levelCompletionAnimation && !levelChangeAnimation) {
                long elapsedTime = (currentTime - levelStartTime) / 1000;
                g.setColor(Color.WHITE);
                g.drawString("Time: " + elapsedTime + "s", 10, 20);
            }

            // Draw level completion animation
            if (levelCompletionAnimation) {
                Graphics2D g2d = (Graphics2D) g;
                float alpha = Math.min(1f, animationTicks / (float)MAX_ANIMATION_TICKS);

                // Remove special animation for level 6, use default for all levels
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(new Color(0, 255, 255));
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 64));
                String msg = "Level Complete!";
                int msgWidth = g2d.getFontMetrics().stringWidth(msg);
                g2d.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            // Draw level change animation
            if (levelChangeAnimation) {
                Graphics2D g2d = (Graphics2D) g;
                float alpha = 1f - (levelChangeTicks / (float)MAX_LEVEL_CHANGE_TICKS);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(new Color(120, 0, 255));
                g2d.fillRect(0, 0, WIDTH, HEIGHT);
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, 48));
                String msg = "Get Ready!";
                int msgWidth = g2d.getFontMetrics().stringWidth(msg);
                g2d.drawString(msg, (WIDTH - msgWidth) / 2, HEIGHT / 2);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
        } else if (currentState == GameState.MENU) {
            mainMenu.render(g);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VaultVault");
        VaultVault game = new VaultVault();
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        game.start();
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void stop() {
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
