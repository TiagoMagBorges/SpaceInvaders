package Entities.Animation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SingleFileLoadAnimationStrategy implements LoadAnimationStrategy{
    private static SingleFileLoadAnimationStrategy instance;

    private SingleFileLoadAnimationStrategy(){}

    public static SingleFileLoadAnimationStrategy getInstance(){
        if(instance == null)
            instance = new SingleFileLoadAnimationStrategy();
        return instance;
    }

    @Override
    public ArrayList<BufferedImage> loadAnimation(int frames, String name) {
        ArrayList<BufferedImage> sprite = new ArrayList<>();
        BufferedImage img;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/" + name + ".png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int cont = 0;
        for (int i = 0; i < frames / 4; i++) {
            for (int j = 0; j < frames / 4; j++) {
                sprite.add(cont, img.getSubimage(j * 128, i * 128, 128, 128));
                cont++;
            }
        }
        return sprite;
    }
}
