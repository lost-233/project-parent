package com.zhaoming.web.manage.view;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.zhaoming.web.manage.view.SideViewItem.ViewGroup.*;

/**
 * @author huhuixin
 */
@Getter
@AllArgsConstructor
public enum SideViewItem {

    /**
     * 页面列表
     */
    DEV_APP(DEV, PlatformDocumentView.APP, "files-o", "通用接口文档"),
    MR_LI(SYSTEM, ManagerView.LIST, "table", "管理员列表"),
    ;
    /**
     * 所属分组
     */
    private ViewGroup group;
    private String url;
    /**
     * 左侧显示对的logo
     */
    private String logo;
    private String name;
    public String getRole() {
        return name();
    }
    public String getHtml(){
        return "<i class='fa nav-icon fa-" + logo + "'></i>" + name;
    }

    /**
     * @author huhuixin
     */
    @Getter
    @AllArgsConstructor
    public enum ViewGroup {
        /**
         * 页面分组
         */
        DEV("files-o", "开发资料", 1),
        SYSTEM("gears" ,"系统管理", 2),
        ;
        private String logo;
        private String name;
        private Integer rank;
    }
}
