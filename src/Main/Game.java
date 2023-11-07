package Main;

import Level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener, Runnable {
    private Level level;
    private int countdown;

    public Game(){
        this.countdown = 400;
        loadLevel();
        Thread gameLoop = new Thread(this);
        gameLoop.start();
    }

    private void loadLevel() {
        this.level = new Level(60);
    }

    public Level getCurrentLevel(){
        return this.level;
    }

    private void update(){
        getCurrentLevel().update();
        if(getCurrentLevel().isLost()){
            countdown--;
            if(countdown <= 100){
                loadLevel();
                countdown = 400;
            }
        }
        if(getCurrentLevel().isWon()){
            countdown--;
            if(countdown <= 100){
                System.exit(0);
            }
        }
    }

    @Override
    public void run() {
        int FPS = 60;
        int UPS = 100;
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;
        long previousTime = System.nanoTime();
        int frames = 0;
        int updates = 0;
        long lastChecked = System.currentTimeMillis();
        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if(deltaU >= 1){
                update();
                updates++;
                deltaU--;
            }

            if(deltaF >= 1){
                repaint();
                frames++;
                deltaF--;
            }

            if(System.currentTimeMillis() - lastChecked >= 1000){
                lastChecked = System.currentTimeMillis();
                System.out.printf("FPS: %d | UPS: %d\n", frames, updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        getCurrentLevel().draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        if(getCurrentLevel().isLost()){
            String message = String.format("Restarting in %d...", (int)(countdown / 100));
            int x = (960 - g.getFontMetrics().stringWidth(message)) / 2;
            int y = (540 / 2) + 15;
            g.drawString(message, x, y);
        }
        if(getCurrentLevel().isWon()){
            String message = String.format("Game closing in %d...", (int)(countdown / 100));
            int x = (960 - g.getFontMetrics().stringWidth(message)) / 2;
            int y = (540 / 2) + 15;
            g.drawString(message, x, y);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                getCurrentLevel().getShip().setRight(true);
                break;
            case KeyEvent.VK_A:
                getCurrentLevel().getShip().setLeft(true);
                break;
            case KeyEvent.VK_SPACE:
                getCurrentLevel().getShip().setShooting(true);
                break;
            case KeyEvent.VK_ESCAPE:
                getCurrentLevel().pausePlay();
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_D:
                getCurrentLevel().getShip().setRight(false);
                break;
            case KeyEvent.VK_A:
                getCurrentLevel().getShip().setLeft(false);
                break;
            case KeyEvent.VK_SPACE:
                getCurrentLevel().getShip().setShooting(false);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
