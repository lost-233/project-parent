package com.zhaoming.web.manage.view;

import com.zhaoming.base.constant.CommonConstant;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author huhuixin
 */
@Data
@NoArgsConstructor
public class Authority {

    /**
     * 用户权限 为 ALL 或 其他权限
     */
    public static final String ALL = "ALL";

    private Map<SideViewItem.ViewGroup,List<SideViewItem>> sideView;

    /**
     * 用户的页面权限列表
     * @param viewsStr
     */
    public static Authority getAuthorities(String viewsStr) {
        if (StringUtils.isEmpty(viewsStr)) {
            return getEmpty();
        } else if (viewsStr.equals(ALL)) {
            return getAllView();
        } else {
            Authority authority = new Authority();
            Set<String> views = new HashSet<>(Arrays
                    .asList(viewsStr.split(CommonConstant.SEPARATOR)));
            // 筛选出有权限的Item并按照Group分组
            Map<SideViewItem.ViewGroup,List<SideViewItem>> map = new TreeMap<>(sortByRank);
            map.putAll(Arrays.stream(SideViewItem.values())
                    .filter(sideViewItem -> views.contains(sideViewItem.name()))
                    .collect(Collectors.groupingBy(SideViewItem::getGroup)));
            authority.setSideView(map);
            return authority;
        }
    }

    private static Authority ALL_VIEW = null;

    public static Authority getAllView(){
        if (ALL_VIEW == null){
            ALL_VIEW = new Authority();
            Map<SideViewItem.ViewGroup,List<SideViewItem>> map = new TreeMap<>(sortByRank);
            map.putAll(Arrays.stream(SideViewItem.values())
                    .collect(Collectors.groupingBy(SideViewItem::getGroup)));
            ALL_VIEW.setSideView(map);
        }
        return ALL_VIEW;
    }

    private static Authority EMPTY = null;

    private static Authority getEmpty(){
        if (EMPTY == null){
            EMPTY = new Authority();
            EMPTY.setSideView(new HashMap<>(0));
        }
        return EMPTY;
    }

    // 排序比较器
    private static Comparator<SideViewItem.ViewGroup> sortByRank
            = Comparator.comparing(SideViewItem.ViewGroup::getRank);
}
