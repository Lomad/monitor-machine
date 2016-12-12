package com.winning.monitor.data.transaction.test;

import com.winning.monitor.data.api.ISystemInfoQueryService;
import com.winning.monitor.data.api.systemInfo.SystemInfoReportVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Lemod
 * @Version 2016/12/8
 */
@ContextConfiguration(locations = {"classpath*:META-INF/spring/*-context.xml"})
public class MongodbSystemInfoQueryUT extends AbstractJUnit4SpringContextTests{

    @Autowired
    ISystemInfoQueryService systemInfoQueryService;

    @Test
    public void systemInfoReportQueryTest(){
        SystemInfoReportVO systemInfoReportVO = this.systemInfoQueryService.getSystemInfoReport("192.168.34.245");

        List<LinkedHashMap<String,Object>> infoList = systemInfoReportVO.getInfoList();

        for(LinkedHashMap<String,Object> map : infoList){
            for (Map.Entry<String,Object> entry : map.entrySet()){
                System.out.println(entry.getValue());
            }
        }
    }

}
