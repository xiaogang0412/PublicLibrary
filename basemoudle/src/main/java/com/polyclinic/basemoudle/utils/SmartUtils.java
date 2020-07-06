package com.polyclinic.basemoudle.utils;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * @author Lxg
 * @create 2020/3/3
 * @Describe
 */
public class SmartUtils {
    /**
     * 处理刷新结束
     *
     * @param layoutHomeRefresh
     */
    public static void finishRereshOrLoading(SmartRefreshLayout layoutHomeRefresh) {
        if (layoutHomeRefresh != null) {
            if (layoutHomeRefresh.isRefreshing()) {
                layoutHomeRefresh.finishRefresh(0, true);
            }
            if (layoutHomeRefresh.isLoading()) {
                layoutHomeRefresh.finishLoadMore(0);
            }
        }
    }
}
