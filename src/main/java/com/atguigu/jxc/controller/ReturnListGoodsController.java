package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.entity.ReturnList;
import com.atguigu.jxc.entity.User;
import com.atguigu.jxc.service.ReturnListGoodsService;
import com.atguigu.jxc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: zm
 * @Date: 2021/9/5 18:57
 */
@RestController
@RequestMapping("/returnListGoods")
public class ReturnListGoodsController {

    @Autowired
    private ReturnListGoodsService returnListGoodsService;

    @Autowired
    private UserService userService;

    // 退货单保存
    // http://localhost:8080/returnListGoods/save?returnNumber=TH1605768591211（退货单号前端生成）
    @PostMapping("save")
    public ServiceVO save(ReturnList returnList, String returnListGoodsStr, HttpSession session) {
        Map<String, Object> currentUserAndRole = userService.getCurrentUserAndRole(session);
        User user = (User) currentUserAndRole.get("user");
        returnList.setUserId(user.getUserId());
        return returnListGoodsService.save(returnList, returnListGoodsStr);
    }

    // http://localhost:8080/returnListGoods/list
    // 退货单列表展示（可条件查询：单据号模糊、供应商、是否付款和日期区间）
    @PostMapping("list")
    public Map<String, Object> list(String returnNumber, Integer supplierId, Integer state, String sTime, String eTime) {
        return returnListGoodsService.list(returnNumber, supplierId, state, sTime, eTime);
    }

    // 请求URL：http://localhost:8080/returnListGoods/goodsList
    // 退货单商品信息
    @PostMapping("goodsList")
    public Map<String, Object> goodsList(Integer returnListId) {
        return returnListGoodsService.goodsList(returnListId);
    }

    // http://localhost:8080/returnListGoods/delete
    // 删除退货单
    @PostMapping("delete")
    public ServiceVO delete(Integer returnListId) {
        return returnListGoodsService.delete(returnListId);
    }

    // /returnListGoods/updateState
    @PostMapping("updateState")
    public ServiceVO updateState(Integer returnListId) {
        return returnListGoodsService.updateState(returnListId);
    }

    // 请求URL：http://localhost:8080/returnListGoods/count
    @PostMapping("count")
    public String count(String sTime, String eTime, Integer goodsTypeId, String codeOrName) {
        return returnListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
    }
}
