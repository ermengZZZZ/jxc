package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.SaleList;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.SaleListGoodsService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 21:37
 */
@RestController
@RequestMapping("/saleListGoods")
public class SaleListGoodsController {
    @Autowired
    private UserService userService;

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    // 销售单保存
    // 请求URL：http://localhost:8080/saleListGoods/save?saleNumber=XS1605772263999 （销售单号）
    @PostMapping("/save")
    public ServiceVO save(SaleList saleList, String saleListGoodsStr, HttpSession session) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        saleList.setUserId(user.getUserId());
        return saleListGoodsService.save(saleList, saleListGoodsStr);
    }

    // http://localhost:8080/saleListGoods/list
    // 销售单查询（可查询条件：销售单号、客户、付款状态）
    @PostMapping("list")
    public Map<String, Object> list(String saleNumber, Integer customerId, Integer state, String sTime, String eTime) {
        return saleListGoodsService.list(saleNumber, customerId, state, sTime, eTime);
    }

    // http://localhost:8080/saleListGoods/goodsList
    // 销售单商品信息
    @PostMapping("goodsList")
    public Map<String, Object> goodsList(Integer saleListId) {
        return saleListGoodsService.goodsList(saleListId);
    }

    //http://localhost:8080/saleListGoods/delete
    // 删除销售单
    @PostMapping("delete")
    public ServiceVO delete(Integer saleListId) {
        return saleListGoodsService.delete(saleListId);
    }

    // http://localhost:8080/saleListGoods/updateState
    // 支付结算（修改销售单付款状态）
    @PostMapping("updateState")
    public ServiceVO updateState(Integer saleListId) {
        return saleListGoodsService.updateState(saleListId);
    }

    // http://localhost:8080/saleListGoods/count
    @PostMapping("count")
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        return saleListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
    }

    // http://localhost:8080/saleListGoods/getSaleDataByDay
    // String sTime, String eTime
    // 日统计
    @PostMapping("/getSaleDataByDay")
    public String getSaleDataByDay(String sTime, String eTime) {
        return saleListGoodsService.getSaleDataByDay(sTime, eTime);
    }

    // http://localhost:8080/saleListGoods/getSaleDataByMonth
    // 按月统计接口
    @PostMapping("/getSaleDataByMonth")
    public String getSaleDataByMonth(String sTime, String eTime) {
        return saleListGoodsService.getSaleDataByMonth(sTime, eTime);
    }

}
