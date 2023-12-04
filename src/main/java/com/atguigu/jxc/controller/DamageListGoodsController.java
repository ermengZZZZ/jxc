package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.*;
import com.atguigu.jxc.service.DamageListGoodsService;
import com.atguigu.jxc.service.UserService;
import com.atguigu.jxc.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 11:52
 */
@RestController
@RequestMapping("/damageListGoods")
public class DamageListGoodsController {

    @Autowired
    private DamageListGoodsService damageListGoodsService;


    @Autowired
    private UserService userService;

    // http://localhost:8080/damageListGoods/save?damageNumber=BS1605766644460（报损单号,前端生成）
    // 保存报损
    @PostMapping("/save")//,
    public ServiceVO save(DamageList damageList, HttpSession session, String damageListGoodsStr) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        damageList.setUserId(user.getUserId());
        return damageListGoodsService.save(damageList, damageListGoodsStr);
    }

    // 报损单查询
    // http://localhost:8080/damageListGoods/list
    @PostMapping("list")
    public Map<String,Object> list(String  sTime,String  eTime){
        return damageListGoodsService.list(sTime,eTime);
    }

    // 查询报损单商品信息
    // http://localhost:8080/damageListGoods/goodsList
    @PostMapping("goodsList")
    public Map<String,Object> goodsList(Integer damageListId){
        return damageListGoodsService.goodsList(damageListId);
    }

}
