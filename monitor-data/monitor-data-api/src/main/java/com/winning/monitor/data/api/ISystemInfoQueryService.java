package com.winning.monitor.data.api;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;

/**
 * @Author Lemod
 * @Version 2016/12/8
 */
public interface ISystemInfoQueryService {

    SystemInfoReportVO getSystemInfoReport(String ipAddress);
}
