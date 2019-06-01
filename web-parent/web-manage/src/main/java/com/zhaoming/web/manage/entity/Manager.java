package com.zhaoming.web.manage.entity;

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
public class Manager extends Model<Manager> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    protected Integer id;

    /**
     * 头像
     */
    protected String headImage;
    /**
     * 用户名
     */
    protected String username;

    /**
     * 密码
     */
    protected String password;

    /**
     * 是否禁用
     */
    protected Boolean enabled;

    /**
     * 用户的权限
     */
    protected String authority;

    protected String tel;

    /**
     * 邮箱
     */
    protected String email;

    protected String realName;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public Manager() {
        enabled = true;
    }

}
