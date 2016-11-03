package com.winning.monitor.data.api;

import java.util.LinkedHashSet;

/**
 * Created by wangwh on 2016/11/2.
 */
public interface IClientTransactionDataQueryService {
    /**
     * 获取所有的应用服务系统名称
     *
     * @param group               系统类别
     * @return
     */
    LinkedHashSet<String> getAllClientNames(String group);
}
