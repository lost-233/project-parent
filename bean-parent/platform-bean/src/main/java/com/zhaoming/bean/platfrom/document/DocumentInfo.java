package com.zhaoming.bean.platfrom.document;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author hhx
 */
@Data
@Accessors(chain = true)
public class DocumentInfo {
    private String address;
    private String profiles;
    private List<GroupDocument> list;
}
