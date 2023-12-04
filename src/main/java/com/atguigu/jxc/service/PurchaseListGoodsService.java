package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.PurchaseList;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 16:51
 */
public interface PurchaseListGoodsService {
    // 进货单保存
    ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr);

    // 进货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    Map<String, Object> list(String purchaseNumber,
                             Integer supplierId,
                             Integer state,
                             String sTime,
                             String eTime);

    // 查询进货单商品信息
    Map<String, Object> goodsList(Integer purchaseListId);

    // 进货单删除
    ServiceVO delete(Integer purchaseListId);

    // // 支付结算（修改进货单付款状态）
    ServiceVO updateState(Integer purchaseListId);

    // 进货统计（可根据 商品类别、商品编码或名称 条件查询）
    String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName);
}
