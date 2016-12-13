package com.winning.monitor.data.api;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;

import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/8
 */
public interface ISystemInfoQueryService {

    SystemInfoReportVO getSystemInfoReport(String ipAddress);

    List<SystemInfoReportVO> getSystemInfoReportList();
}
