package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.base.BaseMapper;
import com.atguigu.base.BaseServiceImpl;
import com.atguigu.en.DictCodeEnum;
import com.atguigu.entity.Dict;
import com.atguigu.mapper.DictMapper;
import com.atguigu.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @className: DictServiceImpl
 * @Package: com.atguigu.service.impl

 * @description:
 * @author: xiaoqi
 * @Emial: onxiaoqi@qq.com
 * @version v1.0
 **/
@Slf4j
@Service(interfaceClass = DictService.class)
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Override
    public BaseMapper<Dict> getBaseMapper() {
        return dictMapper;
    }

    @Override
    public List<Map<String, Object>> findZnodeListByParentId(Long id) {

        //根据parent_id查询字典信息
        List<Dict> dictList = dictMapper.findZnodeListByParentId(id);

        log.debug("根据parent_id查询字典信息 dictList={}", dictList);

        //不为null就处理为map类型
        List<Map<String, Object>> dictListMap = null;
        if (Objects.nonNull(dictList)) {
            dictListMap = dictList.stream()
                    .map(dict -> {
                        HashMap<String, Object> dictMap = new HashMap<>();
                        dictMap.put("name", dict.getName());
                        dictMap.put("id", dict.getId());

                        //根据id判断是否还有子节点, 有子节点返回true 没有为false
                        List<Dict> listByParentId = dictMapper.findZnodeListByParentId(dict.getId());
                        log.debug("listByParentId长度:{} 是否有父节点:{}", listByParentId.size(), listByParentId.size() > 0);
                        dictMap.put("isParent", !CollectionUtils.isEmpty(listByParentId));

                        return dictMap;
                    })
                    .collect(Collectors.toList());
        }
        return dictListMap;
    }

    @Override
    public List<Dict> findAreaListByCode(String code) {
        //如果code为空默认查北京
        if ("".equals(code)) {
            code = DictCodeEnum.BEIJING.getDictCode();
        }
        return dictMapper.findAreaListByDictCode(code);
    }
}
