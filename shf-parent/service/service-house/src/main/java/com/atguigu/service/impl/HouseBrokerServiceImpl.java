package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.HouseBroker;
import com.atguigu.mapper.HouseBrokerMapper;
import com.atguigu.service.HouseBrokerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @className: HouseBrokerServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = HouseBrokerService.class)
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerMapper houseBrokerMapper;

    @Override
    public BaseMapper<HouseBroker> getBaseMapper() {
        return houseBrokerMapper;
    }


    @Override
    public List<HouseBroker> selectHouseBrokerByHouseId(Long houseId) {
        return houseBrokerMapper.selectHouseBrokerByHouseId(houseId);
    }

    @Override
    public int updateHouseBrokerByOldBrokerId(HouseBroker houseBroker, Long oldId) {
        return houseBrokerMapper.updateHouseBrokerByOldBrokerId(houseBroker, oldId);
    }

    @Override
    public int deleteByHouseIdAndBrikerId(Long houseId, Long brokerId) {
        return houseBrokerMapper.deleteByHouseIdAndBrikerId(houseId, brokerId);
    }


}
