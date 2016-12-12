package com.winning.monitor.supervisor.consumer.machine.machine;

import com.winning.monitor.agent.collector.api.entity.CollectData;
import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import com.winning.monitor.supervisor.consumer.machine.machine.entity.SystemInfoReport;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author Lemod
 * @Version 2016/12/7
 */
public class CollectDataReportConverter {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static SystemInfoReportVO toSystemInfoReportVO(SystemInfoReport infoReport){
        SystemInfoReportVO infoReportVO = new SystemInfoReportVO();

        infoReportVO.setId(infoReport.getId());
        infoReportVO.setHostName(infoReport.getHostName());
        infoReportVO.setIpAddress(infoReport.getIpAddress());
        infoReportVO.setStartTime(dateFormat.format(infoReport.getM_startTime()));
        infoReportVO.setEndTime(dateFormat.format(infoReport.getM_endTime()));
        infoReportVO.setInfoList(toCollectDataVO(infoReport.getInfoList()));

        return infoReportVO;
    }

    public static List toCollectDataVO(List<CollectData> list){
        List<LinkedHashMap<String,Object>> collectDataList = new ArrayList<>();
        for (CollectData collectData : list){
            LinkedHashMap<String,Object> info = (LinkedHashMap)collectData;

            collectDataList.add(info);
        }
        return collectDataList;
    }
}
