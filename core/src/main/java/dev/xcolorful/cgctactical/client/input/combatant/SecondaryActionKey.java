package dev.xcolorful.cgctactical.client.input.combatant;

import dev.xcolorful.cgctactical.CgcTactical;
import dev.xcolorful.cgctactical.client.api.entity.ILocalCombatant;
import dev.xcolorful.cgctactical.client.api.entity.combatant.ILocalCombatantGetter;
import dev.xcolorful.cgctactical.client.api.minecraft.input.CustomInputKey;
import dev.xcolorful.cgctactical.client.init.registry.ClientInputCategory;
import dev.xcolorful.cgctactical.core.api.entity.ILivingCombatant;
import dev.xcolorful.cgctactical.core.api.entity.combatant.ILivingCombatantGetter;
import dev.xcolorful.cgctactical.core.api.item.ITacticalItem;
import dev.xcolorful.cgctactical.core.api.item.tactical.ITacticalItemGetter;
import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;
import dev.xcolorful.customgun.CustomGun;
import dev.xcolorful.customgun.client.CustomGunClient;
import dev.xcolorful.customgun.client.api.event.IClientTickEvent;
import dev.xcolorful.customgun.client.api.event.IInputKeyEvent;
import dev.xcolorful.customgun.client.api.event.IMouseButtonEvent;
import dev.xcolorful.customgun.client.api.event.IPrepareClientTickEvent;
import dev.xcolorful.customgun.client.api.input.*;
import dev.xcolorful.customgun.client.api.item.IAnimateGeoItem;
import dev.xcolorful.customgun.client.api.renderer.item.IAnimateGeoItemRenderer;
import dev.xcolorful.customgun.client.input.InputKey;
import dev.xcolorful.customgun.client.input.shooter.ShootKey;
import dev.xcolorful.customgun.client.util.ClientInputUtils;
import dev.xcolorful.customgun.core.api.common.McLogicalSide;
import dev.xcolorful.customgun.core.api.event.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class SecondaryActionKey extends InputKey implements IEventHandler {

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
        customEventRegister.register(this, EventType.PREPARE_CLIENT_TICK_EVENT, EventPriority.NORMAL, false);
        customEventRegister.register(this, EventType.CLIENT_TICK_EVENT, EventPriority.NORMAL, false);
        return true;
    }
    @Override
    public boolean unregisterEventHandler() {
        ICustomEventRegister customEventRegister = CustomGun.getEventRegister();
        customEventRegister.unregister(this, EventType.PREPARE_CLIENT_TICK_EVENT, EventPriority.NORMAL, false);
        customEventRegister.unregister(this, EventType.CLIENT_TICK_EVENT, EventPriority.NORMAL, false);
        return true;
    }

    @Override public String getEventHandlerName() {
        return this.getClass().getName();
    }
    @Override
    public void handleEvent(EventType eventType, IEvent event) {
        switch (eventType) {
            case PREPARE_CLIENT_TICK_EVENT -> onSecondaryActionPreInput((IPrepareClientTickEvent) event);
            case CLIENT_TICK_EVENT -> tickSecondaryAction((IClientTickEvent) event);
            default -> onReceiveWrongEvent(eventType);
        }
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
        // 不在焦点就不进input队列，否则从screen点击回去的时候会误触
        if (!ClientInputUtils.isGameplayFocused()) return;

        switch (InputAction.of(action)) {
            case PRESS -> this.inputQueue = true;
            case RELEASE -> {}
        }
    }

    /**
     * 设计类似 {@link ShootKey#inputQueue}
     */
    private volatile boolean inputQueue = false;
    /**
     * 本tick是否出现过按压
     */
    private boolean hasPressedKey = false;

    /**
     * 清空上一tick信息 + 消费输入队列
     */
    private void onSecondaryActionPreInput(IPrepareClientTickEvent event) {
        this.hasPressedKey = this.inputQueue;

        // 消费输入队列
        this.inputQueue = false;
    }
    private void tickSecondaryAction(IClientTickEvent event) {
        _checkAndDoSecondaryAction();
    }
    /**
     * 只读 this 状态
     * @return 是否执行成功
     */
    private boolean _checkAndDoSecondaryAction() {
        if (!ClientInputUtils.isGameplayFocused()) return false; // 不在焦点

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null || player.isSpectator()) return false; // 旁观模式

        ItemStack tacticalItem = player.getMainHandItem();
        @Nullable ITacticalItem iTacticalItem = ITacticalItemGetter.fromItemStack(tacticalItem);
        if (iTacticalItem == null) return false; // 主手没战术物品

        // --------secondary action--------

        boolean actionPressed = this.hasPressedKey;
        if (
                // 没输入按键
                !actionPressed
        ) return false;

        ILocalCombatant iLocalCombatant = ILocalCombatantGetter.cgctactical$fromLocalPlayer(player);
        ILivingCombatant iLivingCombatant = ILivingCombatantGetter.cgctactical$fromLivingEntity(player);
        if (iLivingCombatant.cgctactical$meleeCharge(McLogicalSide.CLIENT, TacticalAction.SECONDARY_ACTION)) {
            @Nullable MultiPlayerGameMode gameMode = Minecraft.getInstance().gameMode;
            if (gameMode != null) {
                /*
                需要确保服务端收到当前slot已经切换，不然会误以为还在用旧的物品
                 */
                CustomGunClient.getAccessTransformer().ensureHasSentCarriedItem(gameMode);
            }

            this._triggerAnimation(tacticalItem, player);

            return true;
        } else {
            return false;
        }
    }
    private void _triggerAnimation(ItemStack tacticalItem, LocalPlayer player) {
        @Nullable IAnimateGeoItemRenderer<?, ?> render = IAnimateGeoItem.cgc$getCustomRenderer(tacticalItem);
        if (render == null) return;

        render.triggerAnimation(tacticalItem, TacticalAction.SECONDARY_ACTION.getCategoryName());
        render.triggerAnimation(tacticalItem, TacticalAction.SECONDARY_ACTION.typeNameOld);
        player.swing(InteractionHand.MAIN_HAND);
    }
}
