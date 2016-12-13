package com.winning.monitor.data.transaction.machineMerge;

import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;

import java.util.List;
import java.util.UUID;

/**
 * @Author Lemod
 * @Version 2016/12/8
 */
public class SystemInfoStatisticReportMerger {

    private static final String ID = UUID.randomUUID().toString();

    private List<SystemInfoReportVO> initInfoList;

    public SystemInfoStatisticReportMerger() {
    }

    public SystemInfoStatisticReportMerger(List<SystemInfoReportVO> initInfoList) {
        this.initInfoList = initInfoList;
    }

    public SystemInfoReportVO mergeForHour(){
        SystemInfoReportVO resInfoReportVO = new SystemInfoReportVO();

        SystemInfoReportVO lastVO = initInfoList.get(initInfoList.size()-1);
        resInfoReportVO.setIpAddress(lastVO.getIpAddress());
        resInfoReportVO.setHostName(lastVO.getHostName());
        resInfoReportVO.setStartTime(lastVO.getStartTime());
        resInfoReportVO.setEndTime(lastVO.getEndTime());
        resInfoReportVO.setInfoList(lastVO.getInfoList());

        for (int i = initInfoList.size()-2; i >= 0; i--){
            SystemInfoReportVO tempVO = initInfoList.get(i);

            resInfoReportVO.addInfoList(tempVO.getInfoList().
                    subList(1,tempVO.getInfoList().size()));
        }

        resInfoReportVO.setId(ID);

        return resInfoReportVO;
    }

    public SystemInfoReportVO mergeDoubleVO(SystemInfoReportVO startVO,SystemInfoReportVO lastVO){
        SystemInfoReportVO systemInfoReportVO = new SystemInfoReportVO();

        systemInfoReportVO.setIpAddress(lastVO.getIpAddress());
        systemInfoReportVO.setHostName(lastVO.getHostName());
        systemInfoReportVO.setStartTime(lastVO.getStartTime());
        systemInfoReportVO.setEndTime(lastVO.getEndTime());
        systemInfoReportVO.setInfoList(startVO.getInfoList());
        systemInfoReportVO.addInfoList(lastVO.getInfoList().subList(1,lastVO.getInfoList().size()));

        return systemInfoReportVO;
    }
}
