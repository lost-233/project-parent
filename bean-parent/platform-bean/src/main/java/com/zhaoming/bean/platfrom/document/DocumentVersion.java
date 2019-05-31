package com.zhaoming.bean.platfrom.document;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 区分版本的文档
 * @author zm
 */
@Data
@NoArgsConstructor
public class DocumentVersion {
    private String description;
    private List<PlatformDocument.Parameter> parameters;
    private String response;
    private String responseJson;
    private List<PlatformDocument.Result> results;
    private List<PlatformDocument.Dict> dictionaries;
}
