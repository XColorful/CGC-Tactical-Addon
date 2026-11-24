package dev.xcolorful.cgctactical.core.api.resource.assets.display;

import dev.xcolorful.cgctactical.core.api.item.tactical.TacticalAction;

public class SoundNameTag {

    // ----Tactical action----
    public static final String PRIMARY_ACTION = TacticalAction.PRIMARY_ACTION.getCategoryName(); public static final String PRIMARY_ACTION_OLD1 = TacticalAction.PRIMARY_ACTION.typeNameOld;
    public static final String SECONDARY_ACTION = TacticalAction.SECONDARY_ACTION.getCategoryName(); public static final String SECONDARY_ACTION_OLD1 = TacticalAction.SECONDARY_ACTION.typeNameOld;
    // ----IMeleeAttackRuntime----
    public static final String KILL = "kill";
    public static final String CRIT = "crit";

    // --------suffix--------
    public static class Suffix {
        public static final String HIT = "hit";
        public static final String FEEDBACK = "feedback";
    }

    private SoundNameTag() {}
}
