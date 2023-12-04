package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.OverflowList;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.OverflowListGoodsService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 14:32
 */
@RestController
@RequestMapping("/overflowListGoods")
public class OverflowListGoodsController {

    @Autowired
    private OverflowListGoodsService overflowListGoodsService;

    @Autowired
    private UserService userService;

    // 新增报溢单
    //请求URL：http://localhost:8080/overflowListGoods/save?overflowNumber=BY1605767033015（报溢单号）
    @PostMapping("/save")
    public ServiceVO save(OverflowList overflowList, String overflowListGoodsStr, HttpSession session) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        overflowList.setUserId(user.getUserId());
        return overflowListGoodsService.save(overflowList, overflowListGoodsStr);
    }

    // 报溢单查询
    // http://localhost:8080/overflowListGoods/list
    @PostMapping("/list")
    public Map<String, Object> list(String sTime, String eTime) {
        return overflowListGoodsService.list(sTime, eTime);
    }


    // 报溢单商品信息
    // http://localhost:8080/overflowListGoods/goodsList
    @PostMapping("goodsList")
    public Map<String,Object> goodsList(Integer overflowListId){
        return overflowListGoodsService.goodsList(overflowListId);
    }



}
