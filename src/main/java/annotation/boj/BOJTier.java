package annotation.boj;


import java.util.Arrays;

public enum BOJTier {


    UNDEFINED(0),

    BRONZE_I(5), BRONZE_II(4), BRONZE_III(3), BRONZE_IV(2), BRONZE_V(1),

    SILVER_I(10), SILVER_II(9), SILVER_III(8), SILVER_IV(7), SILVER_V(6),

    GOLD_I(15), GOLD_II(14), GOLD_III(13), GOLD_IV(12), GOLD_V(11),

    PLATINUM_I(20), PLATINUM_II(19), PLATINUM_III(18), PLATINUM_IV(17), PLATINUM_V(16),

    DIAMOND_I(25), DIAMOND_II(24), DIAMOND_III(23), DIAMOND_IV(22), DIAMOND_V(21),

    RUBY_I(30), RUBY_II(29), RUBY_III(28), RUBY_IV(27), RUBY_V(26);

    private static final String IMAGE_PATH_PREFIX = "https://static.solved.ac/tier_small/";
    private static final String IMAGE_PATH_SUFFIX = ".svg";


    private int num;

    BOJTier(int num) {
        this.num = num;
    }

    public static BOJTier getByPath(String imagePathElem) {
        return Arrays.stream(BOJTier.values()).filter(tier -> tier.getImagePath().equals(imagePathElem)).findAny().get();
    }

    public String getImagePath(){
        return IMAGE_PATH_PREFIX + num + IMAGE_PATH_SUFFIX;
    };


}


