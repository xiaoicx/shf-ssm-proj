package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Community;
import com.atguigu.mapper.CommunityMapper;
import com.atguigu.mapper.HouseMapper;
import com.atguigu.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: CommunityServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = CommunityService.class)
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Autowired
    private HouseMapper houseMapper;

    @Override
    public BaseMapper<Community> getBaseMapper() {
        return communityMapper;
    }


    @Override
    public Community getCommunityById(Long id) {
        return communityMapper.selectById(id);
    }

    @Override
    public Integer deleteCommunityById(Long id) {

        //查询小区是否发布房源
        Long totalCommunity = houseMapper.selectHouseTotalByCommunityId(id);

        if (totalCommunity > 0) {
            throw new RuntimeException("小区发布了房源无法删除!");
        }

        //逻辑删除
        int effectRows = communityMapper.deleteById(id);
        return effectRows;
    }


}
