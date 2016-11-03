package com.winning.monitor.website.controller;

import com.winning.monitor.data.api.IClientTransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashSet;

/**
 * Created by Evan on 2016/11/3.
 */
@Controller
public class ClientHistoryController {

    @Autowired
    private IClientTransactionDataQueryService ClientTransactionDataQuery;

//    public void setTransactionDataQuery(IClientTransactionDataQueryService ClientTransactionDataQuery) {
//        this.ClientTransactionDataQuery = ClientTransactionDataQuery;
//    }

    private String GroupId = "BI";

    /**
     * 返回所有的系统名称
     * @return
     */
    @RequestMapping(value = {"/paas/getAllClientNames"})
    @ResponseBody
    public LinkedHashSet<String> getAllClientNames(){
        LinkedHashSet<String> set =ClientTransactionDataQuery.getAllClientNames(GroupId);
        return set;
    }

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param clientAppName
     * @param date
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayClientReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryDayClientReportByClient(String clientAppName,String date){
        TransactionStatisticReport report = ClientTransactionDataQuery.queryDayClientReportByClient(GroupId,clientAppName,date);
        return report;
    }

    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param clientAppName
     * @param date
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekClientReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryWeekClientReportByClient(String clientAppName,String date){
        TransactionStatisticReport report = ClientTransactionDataQuery.queryWeekClientReportByClient(GroupId, clientAppName, date);
        return report;
    }

    /**
     * 获取指定的月TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param clientAppName
     * @param date
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthClientReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryMonthClientReportByClient(String clientAppName,String date){
        TransactionStatisticReport report = ClientTransactionDataQuery.queryMonthClientReportByClient(GroupId, clientAppName, date);
        return report;
    }

    /**
     * 获取指定天的TransactionType调用次数的结果集,不进行分页
     * @param clientAppName
     * @param serverAppName
     * @param date
     * @param transactionTypeName
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionTypeCallTimesReportByClient"})
    @ResponseBody
    public TransactionCallTimesReport queryDayTransactionTypeCallTimesReportByClient(String clientAppName,String serverAppName,String date,String transactionTypeName ){
        TransactionCallTimesReport report = ClientTransactionDataQuery.queryDayTransactionTypeCallTimesReportByClient(GroupId,clientAppName,serverAppName,date,transactionTypeName);
        return report;

    }
    /**
     * 获取指定周的TransactionType调用次数的结果集,不进行分页
     * @param clientAppName
     * @param serverAppName
     * @param date
     * @param transactionTypeName
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionTypeCallTimesReportByClient"})
    @ResponseBody
    public TransactionCallTimesReport queryWeekTransactionTypeCallTimesReportByClient(String clientAppName,String serverAppName,String date,String transactionTypeName ){
        TransactionCallTimesReport report = ClientTransactionDataQuery.queryWeekTransactionTypeCallTimesReportByClient(GroupId, clientAppName, serverAppName, date, transactionTypeName);
        return report;

    }
    /**
     * 获取指定月的TransactionType调用次数的结果集,不进行分页
     * @param clientAppName
     * @param serverAppName
     * @param date
     * @param transactionTypeName
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionTypeCallTimesReportByClient"})
    @ResponseBody
    public TransactionCallTimesReport queryMonthTransactionTypeCallTimesReportByClient(String clientAppName,String serverAppName,String date,String transactionTypeName ){
        TransactionCallTimesReport report = ClientTransactionDataQuery.queryMonthTransactionTypeCallTimesReportByClient(GroupId, clientAppName, serverAppName, date, transactionTypeName);
        return report;

    }



}
