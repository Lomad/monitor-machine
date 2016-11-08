package com.winning.monitor.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionCallTimesReport;
import com.winning.monitor.data.api.transaction.domain.TransactionMessageList;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
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
    private String GroupId = "BI";

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
        String value=map.get("value").toString();
        String dateValue=map.get("dateValue").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("value",value);
        mv.addObject("dateValue",dateValue);
        return mv;
    }

    @RequestMapping(value = {"/paas/serverstephistory"})
    public ModelAndView serverstephistory(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }



        ModelAndView mv = new ModelAndView("paas/serverstephistory");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String value=map.get("value").toString();
        String historyPageType=map.get("historyPageType").toString();
        String dateValue=map.get("dateValue").toString();

        String clientAppName ="";
        Iterator key = map.keySet().iterator();
        while(key.hasNext()) {
            if ("clientAppName".equals(key.next())) {
                clientAppName = map.get("clientAppName").toString();
                mv.addObject("clientAppName", clientAppName);
            }
        }
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("value",value);
        mv.addObject("historyPageType",historyPageType);
        mv.addObject("dateValue",dateValue);
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

    @RequestMapping(value = {"/paas/clientsteprealtime"})
    public ModelAndView clientsteprealtime(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/clientsteprealtime");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("time").toString();
        String clientAppName = map.get("clientAppName").toString();

        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("time",time);
        mv.addObject("clientAppName",clientAppName);
        return mv;
    }

    @RequestMapping(value = {"/paas/serverdetailedhistory"})
    public ModelAndView serverdetailedhistory(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/serverdetailedhistory");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("value").toString();
        String clientAppName=map.get("clientAppName").toString();
        String clientIpAddress=map.get("clientIpAddress").toString();
        String status=map.get("status").toString();
        String dateValue=map.get("dateValue").toString();
        String historyPageType=map.get("historyPageType").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("value",time);
        mv.addObject("clientAppName",clientAppName);
        mv.addObject("clientIpAddress",clientIpAddress);
        mv.addObject("status",status);
        mv.addObject("dateValue",dateValue);
        mv.addObject("historyPageType",historyPageType);
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

    @RequestMapping(value = {"/paas/clientdetailedrealtime"})
    public ModelAndView clientdetailedrealtime(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/clientdetailedrealtime");
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

    @RequestMapping(value = {"/paas/clientdetailedhistory"})
    public ModelAndView clientdetailedhistory(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/clientdetailedhistory");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String time=map.get("value").toString();
        String clientAppName=map.get("clientAppName").toString();
        String clientIpAddress=map.get("clientIpAddress").toString();
        String status=map.get("status").toString();
        String historyPageType=map.get("historyPageType").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("value",time);
        mv.addObject("clientAppName",clientAppName);
        mv.addObject("clientIpAddress",clientIpAddress);
        mv.addObject("status",status);
        mv.addObject("historyPageType",historyPageType);
        return mv;
    }

    @RequestMapping(value = {"/paas/clientstephistory"})
    public ModelAndView clientstephistory(String datas) {
        Map<String, Object> map = null;
        try {
            map = this.objectMapper.readValue(datas, Map.class);
        } catch (IOException e) {
            //e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("paas/clientstephistory");
        String transactionTypeName=map.get("transactionTypeName").toString();
        String serverIpAddress=map.get("serverIpAddress").toString();
        String serverAppName=map.get("serverAppName").toString();
        String type=map.get("type").toString();
        String value=map.get("value").toString();
        String historyPageType=map.get("historyPageType").toString();
        mv.addObject("transactionTypeName",transactionTypeName);
        mv.addObject("serverIpAddress",serverIpAddress);
        mv.addObject("serverAppName",serverAppName);
        mv.addObject("type",type);
        mv.addObject("value",value);
        mv.addObject("historyPageType",historyPageType);
        return mv;
    }


    @RequestMapping(value = {"/paas/qeryAllDomain"})
    public @ResponseBody
    LinkedHashSet<String>  qeryAllDomain(){
        LinkedHashSet<String> set = transactionDataQuery.getAllServerAppNames(GroupId);
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
        LinkedHashSet<String> set = transactionDataQuery.getAllServerIpAddress(GroupId,serverAppName);
        return set;
    }
    /**
     * 获取最近一小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @return
     */
    @RequestMapping(value = {"/paas/queryTransactionTypeList"})
      public @ResponseBody TransactionStatisticReport queryTransactionTypeList(String flname){
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer(GroupId,flname);
        return  report;
    }

    /**
     * 获取指定小时的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @param time
     * @return
     */
    @RequestMapping(value = {"/paas/queryHourTransactionTypeReportByServer"})
      public @ResponseBody TransactionStatisticReport queryHourTransactionTypeReportByServer(String flname,String time){
        TransactionStatisticReport report = transactionDataQuery.queryHourTransactionTypeReportByServer(GroupId,flname, time);
        return  report;
    }

    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @param date
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionTypeReportBySaerver"})
    public @ResponseBody TransactionStatisticReport queryDayTransactionTypeReportBySaerver(String flname,String date){
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByServer(GroupId,flname,date);
        return  report;
    }



    /**
     * 获取当天的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @return
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionTypeReportByServer"})
    public @ResponseBody TransactionStatisticReport queryTodayTransactionTypeReportByServer(String flname){
        TransactionStatisticReport report = transactionDataQuery.queryTodayTransactionTypeReportByServer(GroupId,flname);
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
        TransactionStatisticReport report =transactionDataQuery.queryLastHourTransactionTypeReportByClient(GroupId,serverAppName, transactionTypeName, serverIpAddress);
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
        TransactionStatisticReport report =transactionDataQuery.queryTodayTransactionTypeReportByClient(GroupId,serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /***
     * 获取指定小时的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryHourTransactionTypeReportByClient"})
    public @ResponseBody TransactionStatisticReport queryHourTransactionTypeReportByClient(String serverAppName,
                                                                                            String transactionTypeName,
                                                                                            String serverIpAddress,
                                                                                            String time){
        TransactionStatisticReport report =transactionDataQuery.queryHourTransactionTypeReportByClient(GroupId,serverAppName,time, transactionTypeName, serverIpAddress);
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
        TransactionCallTimesReport report =transactionDataQuery.queryLastHourTransactionTypeCallTimesReportByServer(GroupId,serverAppName, transactionTypeName, serverIpAddress);
        return  report;
    }

    /**
     * 获取指定一小时的TransactionType调用次数的结果集,不进行分页
     * @param serverAppName
     * @param hour
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryHourTransactionTypeCallTimesReportByServer"})
    public @ResponseBody TransactionCallTimesReport queryHourTransactionTypeCallTimesReportByServer(String serverAppName,
                                                                                                    String hour,
                                                                                                        String transactionTypeName,
                                                                                                        String serverIpAddress){
        TransactionCallTimesReport report =transactionDataQuery.queryHourTransactionTypeCallTimesReportByServer(GroupId,serverAppName, hour,transactionTypeName, serverIpAddress);
        return report;
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
        TransactionCallTimesReport report =transactionDataQuery.queryTodayTransactionTypeCallTimesReportByServer(GroupId,serverAppName, transactionTypeName, serverIpAddress);
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
                                                                                               String serverIpAddress,String clientAppName){
        System.out.println("ClientAppName:"+ clientAppName);
        TransactionStatisticReport report =transactionDataQuery.queryLastHourTransactionNameReportByServer(GroupId,serverAppName, transactionTypeName, serverIpAddress,clientAppName);
        return  report;
    }

    /**
     * 获取当天的TransactionName服务步骤统计结果不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionNameReportByServer"})
    public @ResponseBody TransactionStatisticReport queryTodayTransactionNameReportByServer(String serverAppName,
                                                                                               String transactionTypeName,
                                                                                               String serverIpAddress,String clientAppName){
        System.out.println("ClientAppName:"+ clientAppName);
        TransactionStatisticReport report =transactionDataQuery.queryTodayTransactionNameReportByServer(GroupId,serverAppName, transactionTypeName, serverIpAddress,clientAppName);
        return  report;
    }


    /**
     * 获取指定小时的TransactionName服务步骤统计结果不进行分页
     * @param serverAppName
     * @param transactionTypeName
     * @param time
     * @param serverIpAddress
     * @return 统计数据结果集
     */
    @RequestMapping(value = {"/paas/queryHourTransactionNameReportByServer"})
    public @ResponseBody TransactionStatisticReport queryHourTransactionNameReportByServer(String serverAppName,
                                                                                               String transactionTypeName,
                                                                                               String serverIpAddress,
                                                                                               String time,String clientAppName){
//        System.out.println(serverAppName+"--"+time+"--"+transactionTypeName+"--"+serverIpAddress);
//        System.out.println("------------------------------------");
        System.out.println("ClientAppName:"+ clientAppName);
        System.out.println("ServerIpAddress:"+ serverIpAddress);
        TransactionStatisticReport report =transactionDataQuery.queryHourTransactionNameReportByServer(GroupId,serverAppName,time,transactionTypeName,serverIpAddress,clientAppName);
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
        TransactionMessageList report = transactionDataQuery.queryLastHourTransactionMessageList(GroupId,serverAppName, transactionTypeName,transactionName, serverIpAddress,clientAppName,clientIpAddress,status,startIndex,pageSize,orderBy);
        return  report;
    }

    /**
     * 获取当天内的调用消息明细记录
     * @param datas
     * @return
     */
    @RequestMapping(value = {"/paas/queryTodayTransactionMessageList"})
    public @ResponseBody TransactionMessageList queryTodayTransactionMessageList(String datas){
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
        TransactionMessageList report = transactionDataQuery.queryTodayTransactionMessageList(GroupId,serverAppName, transactionTypeName, transactionName, serverIpAddress, clientAppName, clientIpAddress, status, startIndex, pageSize, orderBy);
        return  report;
    }

    /**
     * 获取指定小时内的调用消息明细记录
     * @param datas
     * @return
     */
    @RequestMapping(value = {"/paas/queryHourTransactionMessageList"})
    public @ResponseBody TransactionMessageList queryHourTransactionMessageList(String datas){
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
        String time=map.get("time").toString();
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
//        System.out.println("serverAppName="+serverAppName+"time="+time+"transactionTypeName="+transactionTypeName+"transactionName="+transactionName+"serverIpAddress="+ serverIpAddress+"clientAppName="+clientAppName+"clientIpAddress="+clientIpAddress+"status="+status+"startIndex="+startIndex+"pageSize="+pageSize+"orderBy="+orderBy);
        TransactionMessageList report = transactionDataQuery.queryHourTransactionMessageList(GroupId,serverAppName,time,transactionTypeName,transactionName, serverIpAddress,clientAppName,clientIpAddress,status,startIndex,pageSize,orderBy);
        return  report;
    }

    //day
    /**
     * 获取指定日期的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @param date  指定日期,格式为 yyyy-MM-dd
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionTypeReportByServer"})
    @ResponseBody
    public TransactionStatisticReport queryDayTransactionTypeReportByServer(String flname,String date){

        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByServer(GroupId,flname,date);
        return report;
    }

    /**
     * 获取指定日期的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionTypeReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryDayTransactionTypeReportByClient(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionTypeReportByClient(GroupId,flname,date,transactionTypeName,serverIpAddress);
        return report;
    }

    /**
     * 获取指定日期的TransactionType调用次数的结果集,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionTypeCallTimesReportByServer"})
    @ResponseBody
    public TransactionCallTimesReport queryDayTransactionTypeCallTimesReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionCallTimesReport report = transactionDataQuery.queryDayTransactionTypeCallTimesReportByServer(GroupId,flname,date,transactionTypeName,serverIpAddress);
        return report;
    }

    /**
     * 获取指定天的TransactionName服务步骤统计结果不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionNameReportByServer"})
    @ResponseBody
    public TransactionStatisticReport queryDayTransactionNameReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress,String clientAppName){
        TransactionStatisticReport report = transactionDataQuery.queryDayTransactionNameReportByServer(GroupId,flname,date,transactionTypeName,serverIpAddress,clientAppName);
        return report;
    }

    /**
     * 获取指定日期内的调用消息明细记录
     * @param datas
     * @return
     */
    @RequestMapping(value = {"/paas/queryDayTransactionMessageList"})
    @ResponseBody
    public TransactionMessageList queryDayTransactionMessageList(String datas){
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
        String date=map.get("date").toString();
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
//        System.out.println(serverAppName+"--"+date+"--"+transactionTypeName+"--"+transactionName+"--"+serverIpAddress+"--"+clientAppName+"--"+clientIpAddress+"--"+status+"--"+startIndex+"--"+pageSize+"--"+orderBy);
        TransactionMessageList report = transactionDataQuery.queryDayTransactionMessageList(GroupId,serverAppName,date,transactionTypeName,transactionName,serverIpAddress,clientAppName,clientIpAddress,status,startIndex,pageSize,orderBy);
        return report;
    }
    //week
    /**
     * 获取指定周的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @param date  指定周的第一天日期,格式为 yyyy-MM-dd
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionTypeReportByServer"})
    @ResponseBody
    public TransactionStatisticReport queryWeekTransactionTypeReportByServer(String flname,String date){
//        System.out.println("week"+flname+"---"+date);
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByServer(GroupId,flname,date);
        return report;
    }

    /**
     * 获取指定周的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionTypeReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryWeekTransactionTypeReportByClient(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionTypeReportByClient(GroupId,flname, date, transactionTypeName, serverIpAddress);
        return report;
    }

    /**
     * 获取指定周的TransactionType调用次数的结果集,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionTypeCallTimesReportByServer"})
    @ResponseBody
    public TransactionCallTimesReport queryWeekTransactionTypeCallTimesReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionCallTimesReport report = transactionDataQuery.queryWeekTransactionTypeCallTimesReportByServer(GroupId,flname, date, transactionTypeName, serverIpAddress);
        return report;
    }

    /**
     * 获取指定周的TransactionName服务步骤统计结果不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionNameReportByServer"})
    @ResponseBody
    public TransactionStatisticReport queryWeekTransactionNameReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress,String clientAppName){
        TransactionStatisticReport report = transactionDataQuery.queryWeekTransactionNameReportByServer(GroupId,flname, date, transactionTypeName, serverIpAddress,clientAppName);
        return report;
    }

    /**
     * 获取指定周内的调用消息明细记录
     * @param datas
     * @return
     */
    @RequestMapping(value = {"/paas/queryWeekTransactionMessageList"})
    @ResponseBody
    public TransactionMessageList queryWeekTransactionMessageList(String datas){
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
        String date=map.get("date").toString();
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
        TransactionMessageList report = transactionDataQuery.queryWeekTransactionMessageList(GroupId,serverAppName, date, transactionTypeName, transactionName, serverIpAddress, clientAppName, clientIpAddress, status, startIndex, pageSize, orderBy);
        return report;
    }

    //month

    /**
     * 获取指定月的TransactionType服务统计结果,根据服务端IP进行分组,不进行分页
     * @param flname
     * @param date 指定月份的第一条日期,格式为 yyyy-MM-dd
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionTypeReportByServer"})
    public @ResponseBody TransactionStatisticReport queryMonthTransactionTypeReportByServer(String flname,String date){
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionTypeReportByServer(GroupId,flname, date);
        return  report;
    }

    /**
     * 获取指定月的TransactionType服务对应的消费者统计结果,根据客户端应用名称进行分组,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionTypeReportByClient"})
    @ResponseBody
    public TransactionStatisticReport queryMonthTransactionTypeReportByClient(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionTypeReportByClient(GroupId,flname, date, transactionTypeName, serverIpAddress);
        return report;
    }

    /**
     * 获取指定月的TransactionType调用次数的结果集,不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionTypeCallTimesReportByServer"})
    @ResponseBody
    public TransactionCallTimesReport queryMonthTransactionTypeCallTimesReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress){
        TransactionCallTimesReport report = transactionDataQuery.queryMonthTransactionTypeCallTimesReportByServer(GroupId,flname, date, transactionTypeName, serverIpAddress);
        return report;
    }

    /**
     * 获取指定月的TransactionName服务步骤统计结果不进行分页
     * @param flname
     * @param date
     * @param transactionTypeName
     * @param serverIpAddress
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionNameReportByServer"})
    @ResponseBody
    public TransactionStatisticReport queryMonthTransactionNameReportByServer(String flname,String date,String transactionTypeName,String serverIpAddress,String clientAppName){
        TransactionStatisticReport report = transactionDataQuery.queryMonthTransactionNameReportByServer(GroupId,flname, date, transactionTypeName, serverIpAddress,clientAppName);
        return report;
    }

    /**
     * 获取指定月内的调用消息明细记录
     * @param datas
     * @return
     */
    @RequestMapping(value = {"/paas/queryMonthTransactionMessageList"})
    @ResponseBody
    public TransactionMessageList queryMonthTransactionMessageList(String datas){
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
        String date=map.get("date").toString();
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
        TransactionMessageList report = transactionDataQuery.queryMonthTransactionMessageList(GroupId,serverAppName, date, transactionTypeName, transactionName, serverIpAddress, clientAppName, clientIpAddress, status, startIndex, pageSize, orderBy);
        return report;
    }


}

