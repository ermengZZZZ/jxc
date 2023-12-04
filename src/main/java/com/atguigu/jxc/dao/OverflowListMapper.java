package com.atguigu.jxc.dao;

import com.atguigu.jxc.entity.OverflowList;

import java.util.List;

/**
 * @Author: zm
 * @Date: 2021/9/5 14:43
 */
public interface OverflowListMapper {
    // 保存报溢单
    Integer addOverflowList(OverflowList overflowList);

    // 报溢单查询
    List<OverflowList> findList(String sTime, String eTime);
}
