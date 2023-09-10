package com.atguigu.mapper;

import com.atguigu.base.BaseMapper;
import com.atguigu.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: HouseImageMapper
 * @Package: com.atguigu.mapper

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public interface HouseImageMapper extends BaseMapper<HouseImage> {

    /**
     * @Description 根据类型查询房源图片信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param type
     * @return List<HouseImage>
     */
    List<HouseImage> selectHouseImageByType(@Param("id") Long id, @Param("type") Integer type);

}
