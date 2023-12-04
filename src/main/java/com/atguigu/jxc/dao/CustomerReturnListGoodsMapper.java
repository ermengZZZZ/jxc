package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.CustomerReturnListGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 22:11
 */
public interface CustomerReturnListGoodsMapper {
    Integer saveBatch(@Param("customerReturnListGoods") CustomerReturnListGoods[] customerReturnListGoods);

    List<CustomerReturnListGoods> findList(Integer customerReturnListId);
}
