package dev.xcolorful.cgctactical.core.api.config;

public class ClientModConfigTag {

    // KeyConfig
    public static final String key_path = "key";

    public static final String continuousActionInput_path = "ContinuousActionInput";
    public static final String continuousActionInput_comment = "Whether to hold the left mouse button to continuously perform primary actions";

    // RenderConfig
    public static final String render_path = "render";

    public static final String blackFlash_path = "BlackFlash";
    public static final String blackFlash_comment = "Use a black overlay instead of a white one when blinded by a flashbang";

    public static final String explodeScreenShakeMultiplier_path = "ExplodeScreenShakeMultiplier";
    public static final String explodeScreenShakeMultiplier_comment = "Screen shake multiplier for explosions";

    private ClientModConfigTag() {}
}
