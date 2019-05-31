package com.zhaoming.service.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Category extends Model<Category> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品分类
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 没有父节点时为0
     */
    private Integer parentid;

    /**
     * 等级,目前两级1,2
     */
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
