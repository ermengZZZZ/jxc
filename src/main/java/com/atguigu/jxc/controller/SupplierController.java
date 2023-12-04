package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Supplier;
import com.atguigu.jxc.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 10:21
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    @PostMapping("/list")
    public Map<String, Object> searchSupplier(Integer page,
                                              Integer rows,
                                              String supplierName) {
        return supplierService.searchSupplier(page, rows, supplierName);
    }

    @PostMapping("/save")
    public ServiceVO save(Supplier supplier) {
        return supplierService.save(supplier);
    }

    @PostMapping("/delete")
    public ServiceVO delete(String ids) {
        return supplierService.delete(ids);
    }

    // http://localhost:8080/supplier/getComboboxList
    // 供应商下拉列表查询
    @PostMapping("/getComboboxList")
    public List<Supplier> getComboboxList (String q){
        return  supplierService.getComboboxList(q);
    }

}
