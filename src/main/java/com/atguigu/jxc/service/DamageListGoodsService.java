package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.DamageList;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 11:54
 */
public interface DamageListGoodsService {
    // 保存报损单
    ServiceVO save(DamageList damageList, String damageListGoodsStr);

    // 查询 报损单列表
    Map<String, Object> list(String sTime, String eTime);

    // 查询报损单商品信息
    Map<String, Object> goodsList(Integer damageListId);
}
