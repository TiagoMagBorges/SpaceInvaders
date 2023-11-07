package Entities.Animation;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public interface LoadAnimationStrategy {
    ArrayList<BufferedImage> loadAnimation(int frames, String name);
}
