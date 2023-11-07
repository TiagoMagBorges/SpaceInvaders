package Entities;

import Entities.Animation.Animation;
import Entities.Animation.LoadAnimationStrategyFactory;

import java.awt.*;

public class Bullet extends Entity {
    public Bullet(int x, int y) {
        super(x, y,5, 13, 2.5F);
    }

    @Override
    public void update() {
        updatePosition();
        animation.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(animation.getImage(), (int)hitbox.x - 12, (int)hitbox.y - 5, (int)hitbox.width + 25, (int)hitbox.height + 15, null);
    }

    @Override
    protected void updatePosition() {
        hitbox.y -= speed;
    }

    @Override
    protected void loadAnimation() {
        animation = new Animation(6, 5, "tiro", LoadAnimationStrategyFactory.create(LoadAnimationStrategyFactory.MULTIPLE_FILE));
    }
}
