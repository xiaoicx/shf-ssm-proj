package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.base.BaseController;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @className: DictController
 * @Package: com.atguigu.controller

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    /**
     * @Description 字典遍历
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Result
     */
    @RequestMapping("/findZnodes")
    public Result findZnodesByPrentId(@RequestParam(value = "id", defaultValue = "0") Long id) {
        log.info("查找的父id为: id={}", id);

        List<Map<String, Object>> zNodeList = dictService.findZnodeListByParentId(id);

        return Result.ok(zNodeList);
    }

    /**
     * @Description 根据id查询父id的所有字典信息
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param id
     * @return Result
     */
    @RequestMapping("/findListByParentId/{id}")
    public Result findDictListByPid(@PathVariable("id") Long id) {

        log.debug("findDictListByParentId:{}", id);
        List<Map<String, Object>> listByParentId = dictService.findZnodeListByParentId(id);

        return Result.ok(listByParentId);
    }


}
