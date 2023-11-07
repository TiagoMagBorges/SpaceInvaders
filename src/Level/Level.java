package Level;

import Entities.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import java.util.Random;

public class Level implements Observer{
    private BufferedImage backgroundImage;
    private Ship ship;
    private ArrayList<Enemy> enemies;
    private ArrayList<Bullet> bullets;
    private ArrayList<Buff> buffs;
    private boolean paused;
    private boolean lost;
    private boolean won;
    private int debuffTimeControl;
    private int backgroundControl;

    public Level(int enemiesAmount){
        this.paused = false;
        this.lost = false;
        this.won = false;
        this.debuffTimeControl = 0;
        loadEntities(enemiesAmount);
        loadBackground();
    }

    private void loadBackground() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/background.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadEntities(int enemiesAmount) {
        ship = new Ship(960 / 2, 540 - 150);
        ship.addObservers(this);
        loadEnemies(enemiesAmount);
        buffs = new ArrayList<>();
        bullets = new ArrayList<>();
    }

    private void loadEnemies(int enemiesAmount) {
        enemies = new ArrayList<>();
        for(int i = 0; i < enemiesAmount; i++)
            enemies.add(new Enemy((50 + i % 20 * 75)/2, (50 + i / 20  * 75)/2));
    }

    public void update(){
        if(!paused && !lost){
            ArrayList<Enemy> toDeleteEnemies = new ArrayList<>();
            ArrayList<Bullet> toDeleteBullets = new ArrayList<>();
            ArrayList<Buff> toDeleteBuffs = new ArrayList<>();
            boolean changeEnemiesDirection = false;
            boolean applyBuff = false;
            boolean canSpawnDebuf = false;

            debuffTimeControl++;
            canSpawnDebuf = debuffTimeControl >= 1000;

            ship.update();

            for(Enemy e : enemies){
                e.update();
                if(e.colideWith(ship) || e.colideWithScreenBorderVerticaly())
                    lost = true;
                if(e.colideWithScreenBorderHorizontaly())
                    changeEnemiesDirection = true;
                for(Bullet b : bullets){
                    if(e.colideWith(b)){
                        toDeleteEnemies.add(e);
                        toDeleteBullets.add(b);
                    }
                }
            }

            for(Bullet b : bullets){
                b.update();
                if(b.colideWithScreenBorderVerticaly())
                    toDeleteBullets.add(b);
            }

            for(Buff b : buffs){
                b.update();
                if(b.colideWith(ship)) {
                    applyBuff = true;
                    toDeleteBuffs.add(b);
                }
                if(b.colideWithScreenBorderVerticaly())
                    toDeleteBuffs.add(b);
            }

            if(changeEnemiesDirection)
                for(Enemy e : enemies) {
                    e.changeDirection();
                }

            if(applyBuff)
                for(Enemy e : enemies)
                    e.debuff();


            if(new Random().nextInt(1000) == 521 && canSpawnDebuf && !lost && !won){
                buffs.add(new Buff(new Random().nextInt(960 - 50), -50));
                debuffTimeControl = 0;
            }

            enemies.removeAll(toDeleteEnemies);
            bullets.removeAll(toDeleteBullets);
            buffs.removeAll(toDeleteBuffs);

            if(enemies.isEmpty())
                won = true;
        }
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, backgroundControl - 540 * 2, 960, 540, null);
        g.drawImage(backgroundImage, 0, backgroundControl - 540, 960, 540, null);
        g.drawImage(backgroundImage, 0, backgroundControl, 960, 540, null);
        ship.draw(g);
        for (Buff b : buffs)
            b.draw(g);
        for (Enemy e : enemies)
            e.draw(g);
        for (Bullet b : bullets)
            b.draw(g);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 48));

        if (paused) {
            String message = "Game Paused";
            int x = (960 - g.getFontMetrics().stringWidth(message)) / 2;
            int y = (540 / 2) - 20;
            g.drawString(message, x, y);
        } else if (lost) {
            String message = "Level Failed";
            int x = (960 - g.getFontMetrics().stringWidth(message)) / 2;
            int y = (540 / 2) - 20;
            g.drawString(message, x, y);
        } else if (won) {
            String message = "Level Cleared";
            int x = (960 - g.getFontMetrics().stringWidth(message)) / 2;
            int y = (540 / 2) - 20;
            g.drawString(message, x, y);
            backgroundControl += 3;
            if (backgroundControl > 540 * 2)
                backgroundControl = 0;
        } else {
            backgroundControl += 3;
            if (backgroundControl > 540 * 2)
                backgroundControl = 0;
        }
    }

    public void pausePlay(){
        paused = !paused;
    }

    public boolean isLost(){
        return this.lost;
    }

    public boolean isWon(){
        return this.won;
    }

    public Ship getShip(){
        return ship;
    }

    @Override
    public void updateObs(boolean shoot) {
        bullets.add(new Bullet((int)(ship.getHitbox().x + ship.getHitbox().width/2 - 1), (int)ship.getHitbox().y));
    }
}
