package Entities;

import Entities.Animation.Animation;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {
    protected Rectangle2D.Float hitbox;
    protected Animation animation;
    protected boolean left, right;
    protected float speed;

    public Entity(int x, int y, int width, int height, float speed){
        this.hitbox = new Rectangle2D.Float(x, y, width, height);
        this.speed = speed;
        this.left = false;
        this.right = false;
        loadAnimation();
    }

    public abstract void update();
    public abstract void draw(Graphics g);
    protected abstract void updatePosition();
    protected abstract void loadAnimation();

    public boolean colideWithScreenBorderHorizontaly(){
        return (left && hitbox.x <= 0) || (right && hitbox.x + hitbox.width >= 960 - speed);
    }

    public boolean colideWithScreenBorderVerticaly(){
        return (hitbox.y <= -70 || hitbox.y >= 540);
    }

    public boolean colideWith(Entity entity){
        return this.hitbox.intersects(entity.hitbox);
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }
}
