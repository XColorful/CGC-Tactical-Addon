package dev.xcolorful.cgctactical.client.network.message.resource;

import dev.xcolorful.cgctactical.client.api.resource.ClientResourceApi;
import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import dev.xcolorful.cgctactical.client.resource.assets.display.ThrowableDisplay;
import dev.xcolorful.cgctactical.client.resource.instance.assets.MeleeDisplayInstance;
import dev.xcolorful.cgctactical.client.resource.instance.assets.ThrowableDisplayInstance;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalCategory;
import dev.xcolorful.cgctactical.core.api.resource.assets.display.SoundNameTag;
import dev.xcolorful.cgctactical.core.network.message.resource.S2CMessageResourceSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

@ApiStatus.Internal
public class _S2CMessageResourceSound {

    /**
     * 历史遗留的沙雕玩意
     * <li>外部调用需要自行处理{@link SoundNameTag.Suffix#FEEDBACK}后缀问题</li>
     */
    @ApiStatus.Internal
    public static final String FEEDBACK_SUFFIX = "_" + SoundNameTag.Suffix.FEEDBACK;

    public static void handle(S2CMessageResourceSound message) {
        Minecraft mc = Minecraft.getInstance();
        @Nullable LocalPlayer localPlayer = mc.player;
        @Nullable ClientLevel level = mc.level;
        if (localPlayer == null || level == null) return;

        @Nullable var sounds = getSounds(message);
        if (sounds == null) return;

        String sound = message.sound();
        @Nullable var soundLocation = sounds.get(sound);
        if (soundLocation == null && sound.endsWith(FEEDBACK_SUFFIX)) {
            soundLocation = sounds.get(sound.substring(0, sound.length() - FEEDBACK_SUFFIX.length()));
        }
        if (soundLocation == null) return;

        Vec3 soundPos = message.soundPos();
        level.playLocalSound(soundPos.x, soundPos.y, soundPos.z,
                SoundEvent.createVariableRangeEvent(soundLocation),
                SoundSource.PLAYERS, message.volume(), message.pitch(), false);
    }

    private static @Nullable Map<String, Identifier> getSounds(S2CMessageResourceSound message) {
        return switch (message.tacticalCategory()) {
            case MELEE -> {
                @Nullable MeleeDisplayInstance meleeDisplayInstance = ClientResourceApi.getMeleeDisplayInstance(message.tacticalLocation());
                if (meleeDisplayInstance == null) yield null;

                MeleeDisplay meleeDisplay = meleeDisplayInstance.getPojo();
                yield meleeDisplay.getSounds();
            }
            case THROWABLE -> {
                @Nullable ThrowableDisplayInstance throwableDisplayInstance = ClientResourceApi.getThrowableDisplayInstance(message.tacticalLocation());
                if (throwableDisplayInstance == null) yield null;

                ThrowableDisplay throwableDisplay = throwableDisplayInstance.getPojo();
                yield throwableDisplay.getSounds();
            }
            case CONSUMABLE -> null;
            // 增加类型使此处强制编译不通过
        };
    }
}
