package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.PurchaseList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 16:56
 */
public interface PurchaseListMapper {
    // 保存
    Integer addPurchaseList(PurchaseList purchaseList);

    // 进货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    List<PurchaseList> findList(@Param("purchaseNumber") String purchaseNumber,
                                @Param("supplierId") Integer supplierId,
                                @Param("state") Integer state,
                                @Param("sTime") String sTime,
                                @Param("eTime") String eTime);


    // 修改状态
    Integer updateState(PurchaseList purchaseList);

    List<Map<String, Object>> count(@Param("sTime")String sTime,
                                    @Param("eTime")String eTime,
                                    @Param("goodsTypeId")Integer goodsTypeId,
                                    @Param("codeOrName")String codeOrName);
}
