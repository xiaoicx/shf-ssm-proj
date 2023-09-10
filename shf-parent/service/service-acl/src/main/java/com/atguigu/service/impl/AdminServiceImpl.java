package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.entity.Admin;
import com.atguigu.mapper.AdminMapper;
import com.atguigu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @className: AdminServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Service(interfaceClass = AdminService.class)
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public BaseMapper<Admin> getBaseMapper() {
        return adminMapper;
    }

    @Override
    public Admin selectByUserName(Admin admin) {
        return adminMapper.selectByUserName(admin);
    }

    @Override
    public List<Admin> selectOtherHouseBrokerByHouseId(Long houseId, Long brokerId) {
        return adminMapper.selectOtherHouseBrokerByHouseId(houseId, brokerId);
    }
}
