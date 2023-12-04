package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.ReturnList;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 18:58
 */
public interface ReturnListGoodsService {
    // 退货单保存
    ServiceVO save(ReturnList returnList, String returnListGoodsStr);

    // 退货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    Map<String, Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime);

    // 退货单商品信息
    Map<String, Object> goodsList(Integer returnListId);

    // 删除退货单
    ServiceVO delete(Integer returnListId);

    // 修改状态
    ServiceVO updateState(Integer returnListId);

    // 退货统计
    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
