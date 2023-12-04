package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.GoodsType;
import com.atguigu.jxc.service.GoodsTypeService;
import com.atguigu.jxc.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/4 14:59
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {

    @Autowired
    private GoodsTypeService goodsTypeService;

    // 查询所有商品分类
    // http://localhost:8080/goodsType/loadGoodsType
    @PostMapping("/loadGoodsType")
    public String loadGoodsType() {
        return goodsTypeService.findAllGoodsTypeList();
    }


    // http://localhost:8080/goodsType/save
    @PostMapping("/save")
    public ServiceVO save(GoodsType goodsType) {
        goodsType.setGoodsTypeState(0);
        return goodsTypeService.save(goodsType);
    }

    // http://localhost:8080/goodsType/delete
    @PostMapping("delete")
    public ServiceVO delete(Integer goodsTypeId) {
        return goodsTypeService.deleteGoodsTypeById(goodsTypeId);
    }

}
