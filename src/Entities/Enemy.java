package Entities;

import Entities.Animation.Animation;
import Entities.Animation.LoadAnimationStrategyFactory;

import java.awt.*;

public class Enemy extends Entity{
    private Animation explosion;
    private boolean exploded;
    private boolean remove;
    private int removalTimer;
    public Enemy(int x, int y) {
        super(x, y, 32, 32, 0.5F);
        exploded = false;
        removalTimer = 0;
        right = true;
    }

    @Override
    public void update() {
        updatePosition();
        if(!exploded)
            animation.update();
        else {
            explosion.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        if(!exploded)
            g.drawImage(animation.getImage(), (int)hitbox.x - 15, (int)hitbox.y - 15, (int)(hitbox.width + 35), (int)(hitbox.height + 35), null);
        else {
            g.drawImage(explosion.getImage(), (int) hitbox.x, (int) hitbox.y, 32, 32, null);
            removalTimer++;
            if(removalTimer == 15)
                remove = true;
        }
    }

    @Override
    protected void updatePosition() {
        if(right && !colideWithScreenBorderHorizontaly()){
            hitbox.x += speed;
        }else if(right){
            changeDirection();
        }else if(left && !colideWithScreenBorderHorizontaly()){
            hitbox.x -= speed;
        }else if(left){
            changeDirection();
        }
    }

    @Override
    protected void loadAnimation() {
        animation = new Animation(2, 25, "inimigo", LoadAnimationStrategyFactory.create(LoadAnimationStrategyFactory.MULTIPLE_FILE));
        explosion = new Animation(16, 3, "explosao", LoadAnimationStrategyFactory.create(LoadAnimationStrategyFactory.SINGLE_FILE));
    }

    public void changeDirection(){
        right = !right;
        left = !left;
        hitbox.y += 30;
        speed += 0.13F;
    }

    public void debuff(){
        speed -= 0.15F;
        hitbox.y -= 45;
    }

    public boolean isRemove(){
        return this.remove;
    }

    public void exploded() {
        exploded = true;
    }
}
