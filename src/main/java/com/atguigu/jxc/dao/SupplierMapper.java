package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.Supplier;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/4 10:42
 */
public interface SupplierMapper {

    List<Supplier> searchSupplier(@Param("page") Integer page, @Param("rows") Integer rows, @Param("supplierName") String supplierName);

    Supplier findSupplierName(String supplierName);

    Integer addSupplier(Supplier supplier);

    Integer updateSupplier(Supplier supplier);

    Integer deleteButchById(@Param("idList") List<Integer> idList);

    Integer deleteSupplierById(int id);

    Integer selectCount(@Param("supplierName") String supplierName);
}
