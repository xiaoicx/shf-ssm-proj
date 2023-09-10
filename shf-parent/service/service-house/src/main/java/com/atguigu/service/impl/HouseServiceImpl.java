package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.constant.AtguiguConstant;
import com.atguigu.entity.House;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.HouseService;
import com.atguigu.vo.HouseQueryVo;
import com.atguigu.vo.HouseVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

/**
 * @className: HouseServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = HouseService.class)
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public BaseMapper<House> getBaseMapper() {
        return houseMapper;
    }

    @Override
    public Long selectHouseTotalByCommunityId(Long communityId) {
        return houseMapper.selectHouseTotalByCommunityId(communityId);
    }

    public int publishHouseById(Long id, Short status) {
        return houseMapper.publishHouseById(id, status);
    }

    @Override
    public int updateDefaultImageUrlByHouseId(Long houseId, String imageUrl) {
        String defaultHouseImageUrl = selectDefaultHouseImageUrlByHouseId(houseId);
        //如果默认图片为空则设置默认图片
        if (Objects.isNull(defaultHouseImageUrl) || "null".equals(defaultHouseImageUrl) || "".equals(defaultHouseImageUrl)) {
            return houseMapper.updateDefaultImageUrlByHouseId(houseId, imageUrl);

            //如果传入的是空则是删除默认图片
        } else if ("".equalsIgnoreCase(imageUrl)) {
            return houseMapper.updateDefaultImageUrlByHouseId(houseId, imageUrl);
        }
        return 0;
    }

    @Override
    public String selectDefaultHouseImageUrlByHouseId(Long houseId) {
        return houseMapper.selectDefaultHouseImageUrlByHouseId(houseId);
    }

    @Override
    public PageInfo<HouseVo> selectHousePageInfoByHouseQueryVo(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {

        //开启分页
        PageHelper.startPage(pageNum, pageSize);

        //查询数据
        List<HouseVo> houseVoList = houseMapper.selectHousePageInfoByHouseQueryVo(houseQueryVo);

        //返回pageinfo对象
        return new PageInfo<>(houseVoList, AtguiguConstant.PageInfoConstant.DEFAULT_NAVGATEPAGES);
    }

    @Override
    public PageInfo<HouseVo> selectHouseVoListByUserFollowList(int pageNum, int pageSize, List<Long> houseListId) {

        //开启分页
        PageHelper.startPage(pageNum, pageSize);

        //查询数据
        List<HouseVo> houseVoList = houseMapper.selectHousePageInfoByHouseIdList(houseListId);

        //返回pageinfo对象
        return new PageInfo<>(houseVoList, AtguiguConstant.PageInfoConstant.DEFAULT_NAVGATEPAGES);
    }


}
