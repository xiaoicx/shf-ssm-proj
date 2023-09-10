package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.Dict;
import com.atguigu.result.Result;
import com.atguigu.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @Description 根据字典编码查询parentId对应的数据
     * @Since: 1.0.0
     * @Author xiaoqi
     * @param code
     * @return String
     */
    @GetMapping("/findDictListByParentDictCode/{code}")
    public Result findListByDictCode(@PathVariable("code") String code) {
        log.debug("查询的dict_code={}", code);
        List<Dict> dictListByCode = dictService.findAreaListByCode(code);
        return Result.ok(dictListByCode);
    }

    @GetMapping("/findDictListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId) {
        log.debug("查询的父id={}", parentId);
        List<Map<String, Object>> listByParentId = dictService.findZnodeListByParentId(parentId);
        return Result.ok(listByParentId);
    }



}
