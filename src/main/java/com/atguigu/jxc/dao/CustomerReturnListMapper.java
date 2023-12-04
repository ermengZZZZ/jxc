package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.CustomerReturnList;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 22:11
 */
public interface CustomerReturnListMapper {
    //
    Integer addSaleList(CustomerReturnList customerReturnList);

    List<CustomerReturnList> fincList(@Param("returnNumber") String returnNumber,
                                      @Param("customerId") Integer customerId,
                                      @Param("state") Integer state,
                                      @Param("sTime") String sTime,
                                      @Param("eTime") String eTime);

    List<Map<String, Object>> count(@Param("sTime") String sTime,
                                    @Param("eTime") String eTime,
                                    @Param("goodsTypeId")Integer goodsTypeId,
                                    @Param("codeOrName")String codeOrName);
}
