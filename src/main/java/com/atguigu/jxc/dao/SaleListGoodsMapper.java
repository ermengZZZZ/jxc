package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.SaleListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 21:42
 */
public interface SaleListGoodsMapper {
    Integer saveBatch(@Param("saleListGoodsArr") SaleListGoods[] saleListGoodsArr);

    List<SaleListGoods> findList(Integer saleListId);
}
