package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.CustomerReturnList;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 22:10
 */
public interface CustomerReturnListGoodsService {

    ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr);

    Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime);

    Map<String, Object> goodsList(Integer customerReturnListId);

    ServiceVO delete(Integer customerReturnListId);

    // 退货统计
    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
