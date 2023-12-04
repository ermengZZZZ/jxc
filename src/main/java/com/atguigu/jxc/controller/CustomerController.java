package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.Customer;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.CustomerService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 17:33
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // 查询所有用户
    // http://localhost:8080 /customer/list
    @PostMapping("/list")
    public Map<String, Object> list(Integer page, Integer rows, String customerName) {
        return customerService.findAll(page, rows, customerName);
    }

    // http://localhost:8080/ customer/save?customerId=1
    @PostMapping("/save")
    public ServiceVO save(Customer customer) {
        return customerService.save(customer);
    }

    // http://localhost:8080/customer/delete
    @PostMapping("/delete")
    public ServiceVO delete(String ids) {
        return customerService.delete(ids);
    }

    // http://localhost:8080/customer/getComboboxList
    @PostMapping("/getComboboxList")
    public List<Customer> getComboboxList(String q) {
        return customerService.getComboboxList(q);
    }
}
