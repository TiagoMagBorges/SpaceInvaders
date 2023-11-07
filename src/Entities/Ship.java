package Entities;

import Entities.Animation.Animation;
import Entities.Animation.LoadAnimationStrategyFactory;
import Level.Level;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Ship extends Entity implements Subject{
    private ArrayList<Observer> observers;
    private boolean shooting;
    private boolean canShoot;
    private boolean shoot;
    private int shootTime;

    public Ship(int x, int y) {
        super(x, y, 50, 50, 1.5F);
        this.shooting = false;
        this.canShoot = false;
        this.shootTime = 0;
        this.shoot = false;
        this.observers = new ArrayList<>();
    }

    @Override
    public void update() {
        updatePosition();
        updateBullet();
        animation.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(animation.getImage(), (int)hitbox.x - 25, (int)hitbox.y - 35, (int)hitbox.width + 50, (int)hitbox.height + 50, null);
    }

    @Override
    protected void updatePosition() {
        if(right && !colideWithScreenBorderHorizontaly())
            hitbox.x += speed;
        else
            hitbox.x -= speed;
        if(left && !colideWithScreenBorderHorizontaly())
            hitbox.x -= speed;
        else
            hitbox.x += speed;
    }

    @Override
    protected void loadAnimation() {
        animation = new Animation(1, 100, "nave", LoadAnimationStrategyFactory.create(LoadAnimationStrategyFactory.ONE_IMAGE));
    }

    private void updateBullet(){
        shootTime++;
        if(shootTime >= 30)
            canShoot = true;
        if(canShoot && shooting){
            shoot();
            shootTime = 0;
            canShoot = false;
        }
    }

    public boolean canShoot(){
        return this.canShoot;
    }

    public Rectangle2D.Float getHitbox(){
        return this.hitbox;
    }

    public void setShooting(boolean shooting){
        this.shooting = shooting;
    }

    public void shoot(){
        this.shoot = true;
        notifyObservers();
    }

    @Override
    public void addObservers(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObservers(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers)
            o.updateObs(this.shoot);
    }
}
