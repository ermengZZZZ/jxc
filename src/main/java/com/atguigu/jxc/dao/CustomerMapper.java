package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.Customer;
import com.atguigu.jxc.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/4 17:37
 */
public interface CustomerMapper {

    Integer getTotal(@Param("customerName") String customerName);


    List<Customer> findGoodsList(@Param("page") Integer page,
                                 @Param("rows") Integer rows,
                                 @Param("customerName") String customerName);

    Customer getCustomerByName(@Param("customerName") String customerName);

    Integer addCustomer(Customer customer);

    Integer updateCustomer(Customer customer);

    Integer deleteByIds(@Param("idList") List<Integer> idList);
}
