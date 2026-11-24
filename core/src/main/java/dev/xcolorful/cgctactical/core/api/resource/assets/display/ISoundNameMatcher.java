package dev.xcolorful.cgctactical.core.api.resource.assets.display;

import org.jetbrains.annotations.Nullable;

public interface ISoundNameMatcher {

    boolean matches(@Nullable String name);

    /**
     * @return 如果匹配，则返回去除匹配字符串后的字串，否则返回{@code null}
     */
    @Nullable String getStrippedIfMatches(@Nullable String name);

    /**
     * @return 根据类型返回前缀/全名/后缀
     */
    String getName();
}
