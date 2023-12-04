package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.GoodsType;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 15:04
 */
public interface GoodsTypeService {
    // 查询所有商品类别
    String findAllGoodsTypeList();

    // 获取所有商品单位
    Map<String, Object> getAll();

    // 保存商品类别
    ServiceVO save(GoodsType goodsType);

    // 删除商品
    ServiceVO deleteGoodsTypeById(Integer goodsTypeId);
}
