package com.zhaoming.bean.test.dto;

import com.zhaoming.base.annotation.Dict;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zm
 * @since 2019-05-16
 */
@Data
@NoArgsConstructor
public class CategoryDto implements Serializable {

    /**
     * 商品分类
     */
    @Dict(desc = "id", value = "1")
    private Integer id;

    /**
     * 分类名称
     */
    @Dict(desc = "名字", value = "名字")
    private String name;

    /**
     * 没有父节点时为0
     */
    @Dict(desc = "没有父节点时为0", value = "0")
    private Integer parentid;

    /**
     * 等级,目前两级1,2
     */
    @Dict(desc = "等级,目前0,1,2", value = "0")
    private Integer layer;

    /**
     * 图片url地址
     */
    private String image;

    /**
     * 排序规则,数值越大排序越靠前
     */
    private Integer displayorder;

    /**
     * 状态值0表示下架,1表示测试,2表示上架
     */
    private Integer state;

    /**
     * 表示该类型下的商品总数量,为0时不显示在主页
     */
    private Integer count;

    /**
     * 分类标记：tstj:特色推荐，rmzq:热门专区
     */
    private String tag;

}
