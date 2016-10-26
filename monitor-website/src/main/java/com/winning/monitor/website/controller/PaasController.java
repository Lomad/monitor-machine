package com.winning.monitor.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import com.winning.monitor.website.infrastructure.Session;
import com.winning.monitor.website.model.fifter;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

/**
 * Created by Admin on 2016/10/20.
 */
@Controller
public class PaasController {

    @Autowired
    private ITransactionDataQueryService transactionDataQuery;
    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping(value = {"/paas/overview"})
    public ModelAndView overview() {
        return new ModelAndView("paas/overview");
    }
    @RequestMapping(value = {"/paas/serverrealtime"})
    public ModelAndView serverrealtime() {
        return new ModelAndView("paas/serverrealtime");
    }
    @RequestMapping(value = {"/paas/serversysrealtime"})
    public ModelAndView serversysrealtime(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/serversysrealtime");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("time").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("time",time);
        return mv;
    }
    @RequestMapping(value = {"/paas/serversyshistory"})
    public ModelAndView serversyshistory(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/serversyshistory");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        return mv;
    }
    @RequestMapping(value = {"/paas/serversteprealtime"})
    public ModelAndView serversteprealtime(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/serversteprealtime");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("time").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("time",time);
        return mv;
    }
    @RequestMapping(value = {"/paas/serverdetailedrealtime"})
    public ModelAndView serverdetailedrealtime(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/serverdetailedrealtime");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("time").toString();
        String clientAppName=map.get("clientAppName").toString();
        String clientIpAddress=map.get("clientIpAddress").toString();
        String status=map.get("status").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("time",time);
        mv.addObject("clientAppName",clientAppName);
        mv.addObject("clientIpAddress",clientIpAddress);
        mv.addObject("status",status);
        return mv;
    }
    @RequestMapping(value = {"/paas/clientrealtime"})
    public ModelAndView clientrealtime() {
        return new ModelAndView("paas/clientrealtime");
    }
    @RequestMapping(value = {"/paas/serverhistory"})
    public ModelAndView serverhistory() {
        return new ModelAndView("paas/serverhistory");
    }
    @RequestMapping(value = {"/paas/clienthistory"})
    public ModelAndView clienthistory() {
        return new ModelAndView("paas/clienthistory");
    }
    @RequestMapping(value = {"/paas/qeryAllDomain"})
    public @ResponseBody
    LinkedHashSet<String>  qeryAllDomain(){
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames();
        return set;
    }

    /**
     * 获取所有的应用服务系统对应的IP地址
     * @param serverAppName
     * @return
     */
    @RequestMapping(value = {"/paas/getAllServerIpAddress"})
    public @ResponseBody
    LinkedHashSet<String>  getAllServerIpAddress(String serverAppName){
        LinkedHashSet<String> set = transactionDataQuery.getAllServerIpAddress(serverAppName);
        return set;
    }
    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @return
     */
    @RequestMapping(value = {"/paas/queryTransactionTypeList"})
    public @ResponseBody TransactionStatisticReport queryTransactionTypeList(String flname){
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer(flname);
        return  report;
    }

    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @return
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionTypeReportByServer"})
    public @ResponseBody TransactionStatisticReport queryTodayTransactionTypeReportByServer(String flname){
        TransactionStatisticReport report = transactionDataQuery.queryTodayTransactionTypeReportByServer(flname);
        return  report;
    }

    /**获取最近一小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     *
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryLastHourTransactionTypeReportByClient"})
    public @ResponseBody TransactionStatisticReport queryLastHourTransactionTypeReportByClient(String serverAppName,
                                                                                               String transactionTypeName,
                                                                                               String serverIpAddress){
        TransactionStatisticReport report =transactionDataQuery.queryLastHourTransactionTypeReportByClient(serverAppName,transactionTypeName,serverIpAddress);
        return  report;
    }

    /**
     * 获取当天的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionTypeReportByClient"})
    public @ResponseBody TransactionStatisticReport queryTodayTransactionTypeReportByClient(String serverAppName,
                                                                                               String transactionTypeName,
                                                                                               String serverIpAddress){
        TransactionStatisticReport report =transactionDataQuery.queryTodayTransactionTypeReportByClient(serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /**
     * 获取最近一小时的TransactionType调用次数的结果集,不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return 调用次数结果集, 返回对象中durations的总长度为60, Key值为0-59,表示一小时从第0分钟到第59分钟的每分钟调用次数
     */
    @RequestMapping(value = {"/paas/queryLastHourTransactionTypeCallTimesReportByServer"})
    public @ResponseBody TransactionCallTimesReport queryLastHourTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                            String transactionTypeName,
                                                                                            String serverIpAddress){
        TransactionCallTimesReport report =transactionDataQuery.queryLastHourTransactionTypeCallTimesReportByServer(serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /**
     * 获取当天的TransactionType调用次数的结果集,不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return 调用次数结果集, 返回对象中durations的总长度为24, Key值为0-23,表示一天从0点到23点的每小时调用次数
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionTypeCallTimesReportByServer"})
    public @ResponseBody TransactionCallTimesReport queryTodayTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                                        String transactionTypeName,
                                                                                                        String serverIpAddress){
        TransactionCallTimesReport report =transactionDataQuery.queryTodayTransactionTypeCallTimesReportByServer(serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /**
     * 获取最近一小时的TransactionName服务步骤统计结果不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryLastHourTransactionNameReportByServer"})
    public @ResponseBody TransactionStatisticReport queryLastHourTransactionNameReportByServer(String serverAppName,
                                                                                            String transactionTypeName,
                                                                                            String serverIpAddress){
        TransactionStatisticReport report =transactionDataQuery.queryLastHourTransactionNameReportByServer(serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /**获取最近一小时内的调用消息明细记录
     *
     * @return
     */
    @RequestMapping(value = {"/paas/queryLastHourTransactionMessageList"})
    public @ResponseBody TransactionMessageList queryLastHourTransactionMessageList(String datas){
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        String serverAppName=map.get("serverAppName").toString();
        String transactionTypeName=map.get("transactionTypeName").toString();
        String transactionName="";//map.get("transactionName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String clientAppName=map.get("clientAppName").toString();
        String clientIpAddress=map.get("clientIpAddress").toString();
        String status=map.get("status").toString();
        int orderNum =Integer.parseInt(map.get("ordernum").toString());
        String orderValue =map.get("ordervalue").toString();
        int startIndex=Integer.parseInt(map.get("start").toString());
        int pageSize=Integer.parseInt(map.get("pageSize").toString());
        String[] linkkey={"transactionTypeName","serverIpAddress","clientAppName","clientIpAddress","duration","status","time"};
        LinkedHashMap<String, String> orderBy = new LinkedHashMap<String, String>();
        if(orderValue.equals("desc")){
            orderValue="DESC";
        }
        else if(orderValue.equals("asc")){
            orderValue="ASC";
        }
        orderBy.put(linkkey[orderNum],orderValue);
        TransactionMessageList report = transactionDataQuery.queryLastHourTransactionMessageList(serverAppName, transactionTypeName,transactionName, serverIpAddress,clientAppName,clientIpAddress,status,startIndex,pageSize,orderBy);
        return  report;
    }
}

