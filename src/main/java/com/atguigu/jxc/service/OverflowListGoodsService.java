package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.OverflowList;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 14:41
 */
public interface OverflowListGoodsService {
    // 新增报溢单
    ServiceVO save(OverflowList overflowList, String overflowListGoodsStr);

    // 报溢单查询
    Map<String, Object> list(String sTime, String eTime);

    // 报溢单商品信息
    Map<String, Object> goodsList(Integer overflowListId);
}
