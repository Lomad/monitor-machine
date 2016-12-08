package com.winning.monitor.data.storage.api;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import com.winning.monitor.data.storage.api.exception.StorageException;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public interface ISystemInfoStorage {

    void storeCollectDataReport(SystemInfoReportVO infoReportVO)throws StorageException;
}
