package Entities.Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {
    private ArrayList<BufferedImage> sprite;
    private final int frames;
    private final int frequency;
    private final String name;
    private int spriteIndex;
    private int time;

    public Animation(int frames, int frequency, String name, LoadAnimationStrategy strategy) {
        this.frames = frames;
        this.name = name;
        this.frequency = frequency;
        this.spriteIndex = 0;
        this.time = 0;
        sprite = loadSprite(strategy);
    }

    private ArrayList<BufferedImage> loadSprite(LoadAnimationStrategy strategy) {
        return strategy.loadAnimation(frames, name);
    }

    public void update(){
        time++;
        if(time >= frequency){
            spriteIndex++;
            time = 0;
        }
        if(spriteIndex >= frames)
            spriteIndex = 0;
    }

    public BufferedImage getImage(){
        return sprite.get(spriteIndex);
    }
}
