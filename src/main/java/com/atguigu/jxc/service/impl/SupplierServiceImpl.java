package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.SupplierMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Supplier;
import com.atguigu.jxc.service.SupplierService;
import com.atguigu.jxc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: zm
 * @Date: 2021/9/4 10:31
 */
@Service
public class SupplierServiceImpl implements SupplierService {

    @Autowired
    private SupplierMapper supplierMapper;

    // 查询供应商
    @Override
    public Map<String, Object> searchSupplier(Integer page, Integer rows, String supplierName) {
        Map<String, Object> map = new HashMap<>();
        Integer total = supplierMapper.selectCount(supplierName);
        page = page == 0 ? 1 : page;
        List<Supplier> list = supplierMapper.searchSupplier((page - 1) * rows, rows, supplierName);
        map.put("total", total);
        map.put("rows", list);
        return map;
    }

    // 保存 | 修改订单
    @Override
    public ServiceVO save(Supplier supplier) {
        if (supplier != null) {
            return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "参数错误");
        }

        if (supplier.getSupplierId() == null) {
            // 根据供应商名查询供应商是否存在
            Supplier supplierExists = supplierMapper.findSupplierName(supplier.getSupplierName());

            if (supplierExists != null) {
                return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "已有该供应商");
            }

            // 添加
            supplierMapper.addSupplier(supplier);

        } else {
            // 根据供应商名查询供应商是否存在
            Supplier supplierExists = supplierMapper.findSupplierName(supplier.getSupplierName());

            if (supplierExists != null) {
                return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "已有该供应商");
            }
            // 修改
            supplierMapper.updateSupplier(supplier);
        }
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);

    }

    // 删除表记录
    @Override
    public ServiceVO delete(String ids) {
        if (StringUtils.isEmpty(ids)) {
            return new ServiceVO(ErrorCode.ROLE_DEL_ERROR_CODE, "请选择要删除供应商");
        }
        String[] split = ids.split(",");

        if (split.length > 1) {
            List<Integer> idList = Arrays.stream(split).map((id) -> {
                return Integer.parseInt(id);
            }).collect(Collectors.toList());

            supplierMapper.deleteButchById(idList);

        } else {
            supplierMapper.deleteSupplierById(Integer.parseInt(split[0]));
        }

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 供应商下拉列表查询
    @Override
    public List<Supplier> getComboboxList(String q) {
        return supplierMapper.searchSupplier(null, null, q);

    }


}
