package dev.xcolorful.cgctactical.client.resource.instance.assets;

import dev.xcolorful.cgctactical.client.animation.statemachine.MeleeAnimStateContext;
import dev.xcolorful.cgctactical.client.model.TacticalModelObject;
import dev.xcolorful.cgctactical.client.resource.assets.display.MeleeDisplay;
import dev.xcolorful.customgun.client.animation.AnimationHelper;
import dev.xcolorful.customgun.client.animation.controller.AnimController;
import dev.xcolorful.customgun.client.animation.statemachine.LuaAnimStateMachine;
import dev.xcolorful.customgun.client.resource.assets.animation.BedrockAnimation;
import dev.xcolorful.customgun.client.resource.assets.model.BedrockModel;
import dev.xcolorful.customgun.client.resource.assets.script.AssetsScript;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MeleeDisplayInstance extends _AssetsDisplayInstance<MeleeDisplay> {

    private @Nullable TacticalModelObject modelObject;
    private @Nullable LuaAnimStateMachine<MeleeAnimStateContext> animStateMachine;

    private MeleeDisplayInstance(@NotNull MeleeDisplay pojo) {
        super(pojo);
    }

    public static @Nullable MeleeDisplayInstance fromPojo(MeleeDisplay pojo) {
        if (pojo == null) return null;
        MeleeDisplayInstance instance = new MeleeDisplayInstance(pojo);
        if (!instance.isPojoValid()) return null;
        else return instance;
    }

    @Override public boolean resetCache() {
        if (!super.resetCache()) return false;

        @Nullable BedrockModel model = this.getModel();
        if (model != null) this.modelObject = TacticalModelObject.fromPojo(model);

        if (this.modelObject != null) {
            @Nullable BedrockAnimation animation = this.getAnimation();
            @Nullable AssetsScript script = this.getScript();
            if (animation != null && script != null && script.isValid()) {
                AnimController animController = AnimationHelper.createControllerFromBedrock(animation, this.modelObject);
                this.animStateMachine = new LuaAnimStateMachine.Builder<MeleeAnimStateContext>()
                        .setController(animController)
                        .setLuaScripts(script.getResultTable())
                        .build();
            }
        }

        return true;
    }

    // --------Getter--------

    public @Nullable TacticalModelObject getModelObject() {
        return this.modelObject;
    }
    public @Nullable LuaAnimStateMachine<MeleeAnimStateContext> getAnimStateMachine() {
        return this.animStateMachine;
    }

    // --------Deprecated--------

    public @Nullable LuaAnimStateMachine<MeleeAnimStateContext> getStateMachine() {
        return this.getAnimStateMachine();
    }
}
