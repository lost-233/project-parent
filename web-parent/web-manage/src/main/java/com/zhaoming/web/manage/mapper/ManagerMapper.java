package com.zhaoming.web.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhaoming.web.manage.entity.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author system
 * @since 2019-05-16
 */
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {
    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    @Update("update manager set `password` = #{password} where username = #{username}")
    int updatePassword(@Param("username") String username, @Param("password") String password);

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    @Select("select * from manager where username = #{username}")
    Manager selectByUsername(@Param("username") String username);

    /**
     * 根据权限获取用户
     *
     * @param role
     * @return
     */
    @Select("select * from manager where authority like concat('%', #{role}, '%')")
    List<Manager> selectByRole(@Param("role") String role);
}
