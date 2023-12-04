package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 16:52
 */
public interface PurchaseListGoodsMapper {
    // 批量保存
    void saveButchPurchaseListGoods(@Param("purchaseListGoodsArr") PurchaseListGoods[] purchaseListGoodsArr);

    // 查询进货单商品信息
    List<PurchaseListGoods> findList(Integer purchaseListId);
}
