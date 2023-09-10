package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: DictMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface DictMapper extends BaseMapper<Dict> {


    List<Dict> findZnodeListByParentId(@Param("id") Long id);

    List<Dict> findAreaListByDictCode(@Param("code") String code);
}
