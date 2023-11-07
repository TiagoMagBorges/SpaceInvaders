package Entities.Animation;

public class LoadAnimationStrategyFactory {
    public static String MULTIPLE_FILE = "Multiple";
    public static String SINGLE_FILE = "Single";
    public static String ONE_IMAGE = "One-Image";

    public static LoadAnimationStrategy create(String strategy){
        if(strategy.equals(MULTIPLE_FILE)){
            return MultipleFilesLoadAnimationStrategy.getInstance();
        }
        if(strategy.equals(SINGLE_FILE)){
            return SingleFileLoadAnimationStrategy.getInstance();
        }
        if(strategy.equals(ONE_IMAGE)){
            return OneImageLoadAnimationStrategy.getInstance();
        }
        return null;
    }
}
