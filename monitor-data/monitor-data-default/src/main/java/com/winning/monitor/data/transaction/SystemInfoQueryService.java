package com.winning.monitor.data.transaction;

import com.winning.monitor.data.api.ISystemInfoQueryService;
import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import com.winning.monitor.data.storage.api.ISystemInfoStorage;
import com.winning.monitor.data.transaction.machineMerge.SystemInfoStatisticReportMerger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/8
 */
@Service
public class SystemInfoQueryService implements ISystemInfoQueryService{

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final long HOUR = 3600 * 1000L;

    @Autowired
    private ISystemInfoStorage systemInfoStorage;

    @Override
    public SystemInfoReportVO getSystemInfoReport(String ipAddress) {
        SystemInfoReportVO resReportVO;

        List<SystemInfoReportVO> systemInfoReportVOList = systemInfoStorage.queryRealTimeInfo(ipAddress,this.getCurrentTime());
        if (systemInfoReportVOList.size() > 1) {
            SystemInfoStatisticReportMerger merger = new SystemInfoStatisticReportMerger(systemInfoReportVOList);
            resReportVO = merger.mergeForHour();
        }else {
            resReportVO = systemInfoReportVOList.get(0);
        }
        return resReportVO;
    }

    @Override
    public List<SystemInfoReportVO> getSystemInfoReportList() {
        List<SystemInfoReportVO> systemInfoReportVOList = new ArrayList<>();

        List<SystemInfoReportVO> reportVOList = this.systemInfoStorage.queryRealTimeInfoList(this.getCurrentTime());
        if (reportVOList == null||reportVOList.size() == 0) {
            return null;
        }else {
            SystemInfoStatisticReportMerger merger = new SystemInfoStatisticReportMerger();
            for (SystemInfoReportVO tempVO : reportVOList){
                for (SystemInfoReportVO addVO : systemInfoReportVOList){
                    if (addVO != null){
                        if (addVO.getIpAddress().equals(tempVO.getIpAddress())) {
                            SystemInfoReportVO reportVO = merger.mergeDoubleVO(addVO, tempVO);

                            systemInfoReportVOList.remove(addVO);
                            systemInfoReportVOList.add(reportVO);
                        }else {
                            systemInfoReportVOList.add(tempVO);
                        }
                    }
                }
            }
        }
        return systemInfoReportVOList;
    }

    private String getCurrentTime(){
        long current = System.currentTimeMillis();
        current = current - current%HOUR;

        return this.dateFormat.format(current);
    }

}
