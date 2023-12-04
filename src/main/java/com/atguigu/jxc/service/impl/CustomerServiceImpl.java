package com.atguigu.jxc.service.impl;

import com.atguigu.jxc.dao.CustomerMapper;
import com.atguigu.jxc.domain.ErrorCode;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.Customer;
import com.atguigu.jxc.entity.Goods;
import com.atguigu.jxc.service.CustomerService;
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
 * @Date: 2021/9/4 17:34
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Map<String, Object> findAll(Integer page, Integer rows, String customerName) {
        Map<String, Object> map = new HashMap<>();
        Integer total = customerMapper.getTotal(customerName);
        page = page == 0 ? 1 : page;
        List<Customer> goodsList = customerMapper.findGoodsList((page - 1) * rows, rows, customerName);

        map.put("total", total);
        map.put("rows", goodsList);
        return map;
    }

    //保存 | 修改用户
    @Override
    public ServiceVO save(Customer customer) {

        if (customer.getCustomerId() == null) {
            // 新增
            Customer customerExists = customerMapper.getCustomerByName(customer.getCustomerName());

            if (customerExists != null) {
                return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "该门店已经存在");
            }

            customerMapper.addCustomer(customer);
        } else {
            Customer customerExists = customerMapper.getCustomerByName(customer.getCustomerName());

            if (customerExists != null) {
                return new ServiceVO(ErrorCode.ACCOUNT_EXIST_CODE, "该门店名,已经存在");
            }
            customerMapper.updateCustomer(customer);
        }

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    @Override
    public ServiceVO delete(String ids) {

        if (StringUtils.isEmpty(ids)) {
            return new ServiceVO(ErrorCode.ROLE_DEL_ERROR_CODE, "请选择要删除的客户");
        }

        String[] split = ids.split(",");
        if (split != null && split.length > 0) {
            List<Integer> idList = Arrays.stream(split).map((id) -> {
                return Integer.parseInt(id);
            }).collect(Collectors.toList());

            customerMapper.deleteByIds(idList);
        }

        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    // 客户下拉列表
    @Override
    public List<Customer> getComboboxList(String q) {
        return customerMapper.findGoodsList(null, null, q);
    }


}
