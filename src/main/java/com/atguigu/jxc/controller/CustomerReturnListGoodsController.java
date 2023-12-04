package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.CustomerReturnList;
import com.atguigu.jxc.entity.CustomerReturnListGoods;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.CustomerReturnListGoodsService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 22:05
 */
@RestController
@RequestMapping("customerReturnListGoods")
public class CustomerReturnListGoodsController {

    @Autowired
    private CustomerReturnListGoodsService customerReturnListGoodsService;

    @Autowired
    private UserService userService;

    //    http://localhost:8080/customerReturnListGoods/save?returnNumber=XT1605772786495（退货单号）
    @PostMapping("save")
    public ServiceVO save(CustomerReturnList customerReturnList, String customerReturnListGoodsStr, HttpSession session) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        customerReturnList.setUserId(user.getUserId());
        return customerReturnListGoodsService.save(customerReturnList, customerReturnListGoodsStr);
    }

    // http://localhost:8080/customerReturnListGoods/list
    // 客户退货单查询（可查询条件：退货单号、客户、退款状态）
    @PostMapping("list")
    public Map<String, Object> list(String returnNumber, Integer customerId, Integer state, String sTime, String eTime) {
        return customerReturnListGoodsService.list(returnNumber, customerId, state, sTime, eTime);
    }

    // http://localhost:8080/customerReturnListGoods/goodsList
    // 退货单商品信息
    @PostMapping("goodsList")
    public Map<String, Object> goodsList(Integer customerReturnListId) {
        return customerReturnListGoodsService.goodsList(customerReturnListId);
    }

    // http://localhost:8080/customerReturnListGoods/delete
    // 删除退货单
    @PostMapping("delete")
    public ServiceVO delete(Integer customerReturnListId) {
        return customerReturnListGoodsService.delete(customerReturnListId);
    }

    // http://localhost:8080/customerReturnListGoods/count
    // String sTime, String eTime, Integer goodsTypeId, String codeOrName
    // 客户退货统计
    @PostMapping("/count")
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        return customerReturnListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
    }
}
