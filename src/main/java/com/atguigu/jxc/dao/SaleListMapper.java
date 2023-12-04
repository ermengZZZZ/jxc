package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.SaleList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 21:42
 */
public interface SaleListMapper {
    // 保存
    Integer addSaleList(SaleList saleList);

    List<SaleList> findList(@Param("saleNumber") String saleNumber,
                            @Param("customerId") Integer customerId,
                            @Param("state") Integer state,
                            @Param("sTime") String sTime,
                            @Param("eTime") String eTime);

    Integer updateState(Integer saleListId);

    // 销售统计
    List<Map<String, Object>> count(@Param("sTime") String sTime,
                                    @Param("eTime") String eTime,
                                    @Param("goodsTypeId") Integer goodsTypeId,
                                    @Param("codeOrName") String codeOrName);

    // 日统计
    List<Map<String, Object>> getSaleDataByDay(@Param("sTime") String sTime,
                                               @Param("eTime") String eTime);

    // 月统计
    List<Map<String, Object>> getSaleDataByMonth(@Param("sTime") String sTime,
                                                 @Param("eTime") String eTime);
}
