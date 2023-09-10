package com.atguigu.service;

import com.atguigu.base.BaseService;
import com.atguigu.entity.Dict;

import java.util.List;
import java.util.Map;

/**
 * @className: DictService
 * @Package: com.atguigu.service

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface DictService extends BaseService<Dict> {

    /**
     * @Description 根据父id查询所有字典
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return List<Dict>
     */
    List<Map<String, Object>> findZnodeListByParentId(Long id);

    /**
     * @Description 根据code查询id后 在根据id查询所有父节点等于id的子节点
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param code
     * @return List<Dict>
     */
    List<Dict> findAreaListByCode(String code);

}
