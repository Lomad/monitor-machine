package com.winning.monitor.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.data.api.IClientTransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;

/**
 * Created by InnerPeace on 2016/11/3.
 */
@Controller
public class PaasClientController {

    @Autowired
    private IClientTransactionDataQueryService clientTransactionDataQuery;

    private ObjectMapper objectMapper = new ObjectMapper();
    private String GroupId = "BI";

    // 客户端系统名称
    @RequestMapping(value = {"/paas/qeryAllClient"})
    public @ResponseBody
    LinkedHashSet<String> qeryAllClient(){
        LinkedHashSet<String> set = clientTransactionDataQuery.getAllClientNames(GroupId);
        return set;
    }

    // "最近一小时" 的客户端实时报表
    @RequestMapping(value = {"/paas/queryLastHourClientReportByClient"})
    public @ResponseBody TransactionStatisticReport queryLastHourClientReportByClient(String clientAppName){
        TransactionStatisticReport report = clientTransactionDataQuery.queryLastHourClientReportByClient(GroupId, clientAppName);
        return  report;
    }

    // "当天" 的客户端实时报表
    @RequestMapping(value = {"/paas/queryTodayClientReportByClient"})
    public @ResponseBody TransactionStatisticReport queryTodayClientReportByClient(String clientAppName){
        TransactionStatisticReport report = clientTransactionDataQuery.queryTodayClientTypeReportByClient(GroupId,clientAppName);
        return  report;
    }

    // "指定小时" 的客户端实时报表
    @RequestMapping(value = {"/paas/queryHourClientReportByClient"})
    public @ResponseBody TransactionStatisticReport queryHourClientReportByClient(String clientAppName,String hour ){
        TransactionStatisticReport report = clientTransactionDataQuery.queryHourClientReportByClient(GroupId, clientAppName, hour);
        return  report;
    }

    // "最近一小时" 的客户端“调用次数”下钻报表
    @RequestMapping(value = {"/paas/queryLastHourTransactionTypeCallTimesReportByClient"})
    public @ResponseBody TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByClient(String clientAppName,
                                                                                                        String serverAppName,
                                                                                                        String transactionTypeName){
//        Map<String, Object> map = null;
//        try {
//            map = this.objectMapper.readValue(datas, Map.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String clientAppName=map.get("clientAppName").toString();
//        String serverAppName = map.get("serverAppName").toString();
//        String transactionTypeName = map.get("transactionTypeName").toString();
//        String serverIpAddress = map.get("serverIpAddress").toString();

        TransactionCallTimesReport report = clientTransactionDataQuery.queryLastHourTransactionTypeCallTimesReportByClient(GroupId,clientAppName,serverAppName,transactionTypeName);
        return  report;
    }

    // "当天" 的客户端“调用次数”下钻报表
    @RequestMapping(value = {"/paas/queryTodayTransactionTypeCallTimesReportByClient"})
    public @ResponseBody TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByClient(String clientAppName,
                                                                                                     String serverAppName,
                                                                                                     String transactionTypeName){
//        Map<String, Object> map = null;
//        try {
//            map = this.objectMapper.readValue(datas, Map.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String clientAppName=map.get("clientAppName").toString();
//        String serverAppName = map.get("serverAppName").toString();
//        String transactionTypeName = map.get("transactionTypeName").toString();

        TransactionCallTimesReport report = clientTransactionDataQuery.queryTodayTransactionTypeCallTimesReportByClient(GroupId, clientAppName,serverAppName, transactionTypeName);
        return  report;
    }

    // "指定小时" 的客户端“调用次数”下钻报表
    @RequestMapping(value = {"/paas/queryHourTransactionTypeCallTimesReportByClient"})
    public @ResponseBody TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByClient(String clientAppName,
                                                                                                    String serverAppName,
                                                                                                    String transactionTypeName,
                                                                                                    String hour){
//        Map<String, Object> map = null;
//        try {
//            map = this.objectMapper.readValue(datas, Map.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String clientAppName=map.get("clientAppName").toString();
//        String serverAppName = map.get("serverAppName").toString();
//        String hour = map.get("hour").toString();
//        String transactionTypeName = map.get("transactionTypeName").toString();
//        String serverIpAddress = map.get("serverIpAddress").toString();

        TransactionCallTimesReport report = clientTransactionDataQuery.queryHourTransactionTypeCallTimesReportByClient(GroupId, clientAppName,serverAppName, hour, transactionTypeName);
        return report;
    }
}
