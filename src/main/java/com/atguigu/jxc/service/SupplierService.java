package com.atguigu.jxc.service;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Supplier;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 10:31
 */
public interface SupplierService {
    // 查询供应商
    Map<String,Object> searchSupplier(Integer page, Integer rows, String supplierName);

    // 保存 | 修改订单
    ServiceVO save(Supplier supplier);

    // 删除订单
    ServiceVO delete(String ids);

    // 供应商下拉列表查询
    List<Supplier> getComboboxList(String q);
}
