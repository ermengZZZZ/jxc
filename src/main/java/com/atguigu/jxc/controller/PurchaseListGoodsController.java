package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 16:48
 */
@RestController
@RequestMapping("/purchaseListGoods")
public class PurchaseListGoodsController {

    @Autowired
    private PurchaseListGoodsService purchaseListGoodsService;

    @Autowired
    private UserService userService;

    // 进货单保存
    // http://localhost:8080/purchaseListGoods/save?purchaseNumber=JH1605768306735（进货单号前端生成）
    @PostMapping("/save")
    public ServiceVO save(PurchaseList purchaseList, String purchaseListGoodsStr, HttpSession session) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        purchaseList.setUserId(user.getUserId());
        return purchaseListGoodsService.save(purchaseList, purchaseListGoodsStr);
    }

    // http://localhost:8080/purchaseListGoods/list
    // 进货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    @PostMapping("/list")
    public Map<String, Object> list(String purchaseNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        return purchaseListGoodsService.list(purchaseNumber, supplierId, state, sTime, eTime);
    }

    // http://localhost:8080/purchaseListGoods/goodsList
    // 查询进货单商品信息
    @PostMapping("/goodsList")
    public Map<String, Object> goodsList(Integer purchaseListId) {
        return purchaseListGoodsService.goodsList(purchaseListId);
    }

    // 进货单删除
    // http://localhost:8080/purchaseListGoods/delete
    @PostMapping("/delete")
    public ServiceVO delete(Integer purchaseListId) {
        return purchaseListGoodsService.delete(purchaseListId);
    }

    // http://localhost:8080/purchaseListGoods/updateState
    // 支付结算（修改进货单付款状态）
    @PostMapping("/updateState")
    public ServiceVO updateState(Integer purchaseListId) {
        return purchaseListGoodsService.updateState(purchaseListId);
    }

    // http://localhost:8080/purchaseListGoods/count
    // 进货统计（可根据 商品类别、商品编码或名称 条件查询）
    @PostMapping("count")
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        return purchaseListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
    }
}
