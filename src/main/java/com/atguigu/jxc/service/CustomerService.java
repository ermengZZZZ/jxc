package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Customer;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 17:34
 */
public interface CustomerService {
    // 查询所有用户
    Map<String, Object> findAll(Integer page, Integer rows, String customerName);

    // 保存 | 修改用户
    ServiceVO save(Customer customer);

    // 删除用户
    ServiceVO delete(String ids);

    // 客户下拉列表
    List<Customer> getComboboxList(String q);
}
