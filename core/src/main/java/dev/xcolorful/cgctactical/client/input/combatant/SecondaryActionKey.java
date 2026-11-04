package dev.xcolorful.cgctactical.client.input.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.api.minecraft.input.CustomInputKey;
import dev.xcolorful.cgctactical.client.init.registry.ClientInputCategory;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.client.api.event.IInputKeyEvent;
import dev.xcolorful.customgun.client.api.event.IMouseButtonEvent;
import dev.xcolorful.customgun.client.api.input.IInputKeyManager;
import dev.xcolorful.customgun.client.api.input.IKeyConflictContext;
import dev.xcolorful.customgun.client.api.input.IKeyMapping;
import dev.xcolorful.customgun.client.api.input.IKeyModifier;
import dev.xcolorful.customgun.client.input.InputKey;
import dev.xcolorful.customgun.client.util.ClientInputUtils;
import dev.xcolorful.customgun.core.api.event.ICustomEventRegister;

public final class SecondaryActionKey extends InputKey {

    private static final class SecondaryActionKeyHolder {
        private static final SecondaryActionKey INSTANCE = new SecondaryActionKey();
    }

    public static SecondaryActionKey get() {
        return SecondaryActionKeyHolder.INSTANCE;
    }

    private SecondaryActionKey() {
        super(CustomInputKey.SECONDARY_ACTION);
    }
    @Override protected IKeyMapping createKeyMapping(IKeyMapping.Creator creator) {
        return creator.create(this.key.getCategoryLang().getString(),
                IKeyConflictContext.Type.IN_GAME,
                IKeyModifier.Type.NONE,
                ClientInputUtils.KeyType.mouse(),
                ClientInputUtils.KeyCode._MOUSE_BUTTON_RIGHT(),
                ClientInputCategory.COMBATANT);
    }

    public static final String _MANAGER_NAME = String.format("%s:%s", CgcTactical.MOD_ID, SecondaryActionKey.class.getSimpleName());
    @Override public String getManagerName() {
        return _MANAGER_NAME;
    }

    @Override
    public boolean registerEventHandler() {
        ICustomEventRegister customEventRegister = CustomGun.getEventRegister();
        return true;
    }
    @Override
    public boolean unregisterEventHandler() {
        ICustomEventRegister customEventRegister = CustomGun.getEventRegister();
        return true;
    }

    // --------IInputHandler--------

    @Override
    public void onKeyInput(IInputKeyManager inputKeyManager, IInputKeyEvent event) {
        this.onSecondaryActionKeyInput(event.getAction());
    }
    @Override
    public void onMouseInput(IInputKeyManager inputKeyManager, IMouseButtonEvent event) {
        this.onSecondaryActionKeyInput(event.getAction());
    }
    private void onSecondaryActionKeyInput(int action) {
    }
}
