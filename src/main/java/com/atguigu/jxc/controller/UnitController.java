package com.atguigu.jxc.controller;

import com.atguigu.jxc.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 20:52
 */
@RestController
@RequestMapping("/unit")
public class UnitController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    // 查询所有商品单位
    //http://localhost:8080/unit/list
    @PostMapping("/list")
    public Map<String, Object> list() {
        return goodsTypeService.getAll();
    }
}
