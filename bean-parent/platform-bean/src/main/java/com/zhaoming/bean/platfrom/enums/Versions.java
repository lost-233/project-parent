package com.zhaoming.bean.platfrom.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * app接口服务版本控制列表
 *
 * @author zm
 */
@Getter
@AllArgsConstructor
public enum Versions {
    /**
     * app接口服务版本控制列表
     */
    V1("v1"),
    V2("v2"),
    V3("v3"),
    V4("v4"),
    ;

    private String version;

    public static Versions getVersionByStr(String ver) {
        if (StringUtils.isBlank(ver)) {
            return V1;
        }
        return Arrays.stream(values()).filter(versions -> ver.equals(versions.getVersion())).findFirst().orElse(V1);
    }
}
