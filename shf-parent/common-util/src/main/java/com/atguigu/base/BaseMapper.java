package com.atguigu.base;

import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/**
 * @className: BaseDao
 * @Package: com.atguigu.base

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface BaseMapper<T> {

    /**
     * @Description 查询所有角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return List<Role>
     */
    List<T> findAll();

    /**
     * @Description 添加一个角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param t
     * @return int
     */
    int insert(T t);

    /**
     * @Description 根据id查询角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Role
     */
    T selectById(Long id);

    /**
     * @Description 修改角色信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param t
     * @return int
     */
    int update(T t);

    /**
     * @Description 根据id删除角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return int
     */
    int deleteById(Long id);

    /**
     * @Description 分页模糊查询所有角色
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param
     * @return Page<Role>
     */
    Page<T> findPage(Map<String, Object> filterMap);

}
