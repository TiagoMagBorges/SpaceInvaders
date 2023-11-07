package Entities;

import Entities.Animation.Animation;
import Entities.Animation.LoadAnimationStrategyFactory;

import java.awt.*;

public class Buff extends Entity{

    public Buff(int x, int y) {
        super(x, y, 25, 25, 0.5F);
    }

    @Override
    public void update() {
        updatePosition();
        animation.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(animation.getImage(), (int)hitbox.x, (int)hitbox.y, 32, 32, null);

    }

    @Override
    protected void updatePosition() {
        hitbox.y += speed;
    }

    @Override
    protected void loadAnimation() {
        animation = new Animation(1, 0, "buff", LoadAnimationStrategyFactory.create(LoadAnimationStrategyFactory.ONE_IMAGE));
    }
}
