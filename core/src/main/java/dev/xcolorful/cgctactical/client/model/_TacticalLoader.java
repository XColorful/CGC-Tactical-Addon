package dev.xcolorful.cgctactical.client.model;

import dev.xcolorful.cgctactical.core.api.resource.assets.model.bedrock.geometry.NodeName;
import dev.xcolorful.customgun.client.api.model.bedrock.IBedrockRenderer;
import dev.xcolorful.customgun.client.renderer.model.HandRender;

import java.util.Map;

public class _TacticalLoader {

    private static final String LEFTHAND_POS_NODE = dev.xcolorful.customgun.core.api.resource.assets.model.bedrock.geometry.NodeName.LEFTHAND_POS.getName();
    private static final String RIGHTHAND_POS_NODE = dev.xcolorful.customgun.core.api.resource.assets.model.bedrock.geometry.NodeName.RIGHTHAND_POS.getName();

    protected static void constructFunctionalRenderer(TacticalModelObject _this) {
        // 左手手臂
        _this.setFunctionalRenderer(LEFTHAND_POS_NODE, bedrockPart -> new HandRender.Left(_this));
        // 右手手臂
        _this.setFunctionalRenderer(RIGHTHAND_POS_NODE, bedrockPart -> new HandRender.Right(_this));

        for (Map.Entry<String, IBedrockRenderer> entry : _this.modelMap_entrySet()) {
            String nodeName = entry.getKey();

            // 第一人称特效，仅第一人称渲染时可见
            if (NodeName.FIRST_PERSON_EFFECT.matches(nodeName)
                    || NodeName.Prefix.FIRST_PERSON_EFFECT.matches(nodeName)
            ) {
                _this.setFunctionalRenderer(nodeName, bedrockPart -> {
                    bedrockPart.visible = _this.getEffectVisible();
                    return null;
                });
                continue;
            }

            // 实体渲染时隐藏
            if (NodeName.ENTITY_HIDE.matches(nodeName)
                    || NodeName.Prefix.ENTITY_HIDE.matches(nodeName)
            ) {
                _this.setFunctionalRenderer(nodeName, bedrockPart -> {
                    bedrockPart.visible = !_this.getEntityRendering();
                    return null;
                });
            }
        }
    }

    protected static void constructOtherPath(TacticalModelObject _this) {
        _this.leftHandPosPath = _this.getPath(_this.modelMap_get(LEFTHAND_POS_NODE));
        _this.rightHandPosPath = _this.getPath(_this.modelMap_get(RIGHTHAND_POS_NODE));
    }
}
