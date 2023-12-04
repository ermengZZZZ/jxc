package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.SaleList;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 21:40
 */
public interface SaleListGoodsService {
    // 销售单保存
    ServiceVO save(SaleList saleList, String saleListGoodsStr);

    // 查询销售单列表
    Map<String, Object> list(String saleNumber, Integer customerId, Integer state, String sTime, String eTime);

    // 销售单商品信息
    Map<String, Object> goodsList(Integer saleListId);

    // 删除
    ServiceVO delete(Integer saleListId);

    ServiceVO updateState(Integer saleListId);

    // 销售统计
    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);

    // 日统计
    String getSaleDataByDay(String sTime, String eTime);

    // 月统计
    String getSaleDataByMonth(String sTime, String eTime);
}
