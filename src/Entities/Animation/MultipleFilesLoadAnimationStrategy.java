package Entities.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class MultipleFilesLoadAnimationStrategy implements LoadAnimationStrategy{
    private static MultipleFilesLoadAnimationStrategy instance;

    private MultipleFilesLoadAnimationStrategy(){}

    public static MultipleFilesLoadAnimationStrategy getInstance(){
        if(instance == null)
            instance = new MultipleFilesLoadAnimationStrategy();
        return instance;
    }

    @Override
    public ArrayList<BufferedImage> loadAnimation(int frames, String name) {
        ArrayList<BufferedImage> sprite = new ArrayList<>();
        for(int i = 0; i < frames; i++){
            try{
                sprite.add(i, ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/" + name + i + ".png"))));
            }catch(Exception e){
                throw new RuntimeException(e);
            }
        }
        return sprite;
    }
}
