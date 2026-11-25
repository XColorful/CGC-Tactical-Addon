package dev.xcolorful.cgctactical.core.network.message.resource;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalCategory;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.cgctactical.client.network.message.resource._S2CMessageResourceSound;
import dev.xcolorful.customgun.core.api.common.McSide;
import dev.xcolorful.customgun.core.api.network.MessageDirection;
import dev.xcolorful.customgun.core.api.network.message.IMessage;
import dev.xcolorful.customgun.core.util.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;

import java.util.function.Consumer;

public record S2CMessageResourceSound(TacticalCategory tacticalCategory,
                                      Identifier tacticalLocation,
                                      String sound,
                                      Vec3 soundPos,
                                      float volume,
                                      float pitch)
        implements IMessage<S2CMessageResourceSound> {

    @Override
    public void encode(FriendlyByteBuf buffer) {
        var message = this;
        buffer.writeEnum(message.tacticalCategory);
        NetworkUtils.writeResourceLocation(buffer, message.tacticalLocation);
        NetworkUtils.writeUtf(buffer, sound);
        buffer.writeDouble(soundPos.x);
        buffer.writeDouble(soundPos.y);
        buffer.writeDouble(soundPos.z);
        buffer.writeFloat(volume);
        buffer.writeFloat(pitch);
    }

    public static S2CMessageResourceSound decode(FriendlyByteBuf buffer) {
        TacticalCategory tacticalCategory = buffer.readEnum(TacticalCategory.class);
        var tacticalLocation = NetworkUtils.readResourceLocation(buffer);
        String sound = NetworkUtils.readUtf(buffer);
        Vec3 soundPos = new Vec3(buffer.readDouble(),
                buffer.readDouble(),
                buffer.readDouble());
        float volume = buffer.readFloat();
        float pitch = buffer.readFloat();
        return new S2CMessageResourceSound(tacticalCategory, tacticalLocation, sound, soundPos, volume, pitch);
    }

    @Override
    public void handle(Consumer<Runnable> handler, NetworkContext context) {
        if (context.direction() == MessageDirection.SERVER_TO_CLIENT) {
            handler.accept(() -> {
                CustomGun.getSideExecutor().executeOn(McSide.CLIENT, () -> () ->
                        _S2CMessageResourceSound.handle(this)
                );
            });
        }
    }
}
