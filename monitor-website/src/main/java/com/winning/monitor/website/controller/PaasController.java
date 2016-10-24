package com.winning.monitor.website.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.winning.monitor.data.api.ITransactionDataQueryService;
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
import java.util.LinkedHashSet;
import java.util.Map;

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
    @RequestMapping(value = {"/paas/queryTransactionTypeList"})
    public @ResponseBody TransactionStatisticReport queryTransactionTypeList(String flname){
//        Session.set("serverAppName",flname);
//        Session.set("type","time1");

        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer(flname);
        return  report;
    }
}

