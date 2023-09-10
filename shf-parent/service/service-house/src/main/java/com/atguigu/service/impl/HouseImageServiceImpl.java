package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.House;
import com.atguigu.entity.HouseImage;
import com.atguigu.mapper.HouseImageMapper;
import com.atguigu.service.HouseImageService;
import com.atguigu.service.HouseService;
import com.atguigu.util.QiniuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @className: HouseImageServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Service(interfaceClass = HouseImageService.class)
public class HouseImageServiceImpl extends BaseServiceImpl<HouseImage> implements HouseImageService {

    @Autowired
    private HouseImageMapper houseImageMapper;

    @Autowired
    private HouseService houseService;

    @Override
    public BaseMapper<HouseImage> getBaseMapper() {
        return houseImageMapper;
    }

    @Override
    public List<HouseImage> selectHouseImageByType(Long id, Integer type) {
        return houseImageMapper.selectHouseImageByType(id, type);
    }

    @Override
    public int deleteHouseImageAndDeleteDefaultHouseImageById(Long id) {

        //查询houseImage记录
        HouseImage houseImage = houseImageMapper.selectById(id);

        //判断删除的id是否和房源的默认图片一样, 一致就删除房源的默认图片和房源表中的图片
        if (houseImage != null && !ObjectUtils.isEmpty(houseImage.getHouseId())) {

            String defaultHouseImageUrl = houseService.selectDefaultHouseImageUrlByHouseId(houseImage.getHouseId());
            log.debug("房源中的默认图片url={}", defaultHouseImageUrl);

            if (defaultHouseImageUrl.equalsIgnoreCase(houseImage.getImageUrl())) {
                int r1 = houseService.updateDefaultImageUrlByHouseId(houseImage.getHouseId(), "");
                log.debug("删除房源中的默认图片条数 rows={}", r1);
            }
        }

        //删除图片表中的图片, 逻辑删除
        int r2 = houseImageMapper.deleteById(id);
        log.debug("删除房源图片表中的图片数为: rows={}", r2);

        //删除qiniuyun中的图片
        log.debug("删除七牛云中的图片路径为 path={}", houseImage.getImageName());
        QiniuUtils.deleteFileFromQiniu(houseImage.getImageName());

        return r2;
    }
}
