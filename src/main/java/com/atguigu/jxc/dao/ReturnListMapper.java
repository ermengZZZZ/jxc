package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.ReturnList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 19:00
 */
public interface ReturnListMapper {
    Integer addReturnList(ReturnList returnList);

    // 退货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    List<ReturnList> findList(@Param("returnNumber") String returnNumber,
                              @Param("supplierId") Integer supplierId,
                              @Param("state") Integer state,
                              @Param("sTime") String sTime,
                              @Param("eTime") String eTime);

    Integer updateState(Integer returnListId);

    // 统计退单
    List<Map<String, Object>> count(@Param("sTime") String sTime,
                                    @Param("eTime") String eTime,
                                    @Param("goodsTypeId") Integer goodsTypeId,
                                    @Param("codeOrName") String codeOrName);
}
