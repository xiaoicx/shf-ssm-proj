package com.atguigu.base;

import com.atguigu.constant.AtguiguConstant;
import com.atguigu.util.CastUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @className: BaseServiceImpl
 * @Package: com.atguigu.base

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    public abstract BaseMapper<T> getBaseMapper();

    @Override
    public List<T> findAll() {
        return getBaseMapper().findAll();
    }

    @Override
    public int insert(T t) {
        return getBaseMapper().insert(t);
    }

    @Override
    public T selectById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public int update(T t) {
        return getBaseMapper().update(t);
    }

    @Override
    public int deleteById(Long id) {
        return getBaseMapper().deleteById(id);
    }

    @Override
    public PageInfo<T> findPage(Map<String, Object> filterMap) {

        //转换分页参数赋予默认值
        int pageSize = CastUtil.castInt(filterMap.get("pageSize"), AtguiguConstant.PageInfoConstant.DEFAULT_PAGE_SIZE);
        int pageNum = CastUtil.castInt(filterMap.get("pageNum"), AtguiguConstant.PageInfoConstant.DEFAULT_PAGE_NUM);

        //开启分页查询
        PageHelper.startPage(pageNum, pageSize);

        //查询
        Page<T> t = getBaseMapper().findPage(filterMap);

        //返回pageinfo
        return new PageInfo<T>(t);
    }

}
