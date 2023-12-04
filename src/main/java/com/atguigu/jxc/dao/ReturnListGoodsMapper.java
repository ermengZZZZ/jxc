package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.ReturnListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 19:00
 */
public interface ReturnListGoodsMapper {

    Integer saveList(@Param("returnListGoodsArr") ReturnListGoods[] returnListGoodsArr);

    // 退货单商品信息
    List<ReturnListGoods> findList(Integer returnListId);
}
