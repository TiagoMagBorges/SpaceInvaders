package Entities.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class OneImageLoadAnimationStrategy implements LoadAnimationStrategy{
    private static OneImageLoadAnimationStrategy instance;

    private OneImageLoadAnimationStrategy(){}

    public static OneImageLoadAnimationStrategy getInstance(){
        if(instance == null)
            instance = new OneImageLoadAnimationStrategy();
        return instance;
    }

    @Override
    public ArrayList<BufferedImage> loadAnimation(int frames, String name) {
        ArrayList<BufferedImage> sprite = new ArrayList<>();
        try{
            sprite.add(ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/" + name + ".png"))));
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        return sprite;
    }
}
