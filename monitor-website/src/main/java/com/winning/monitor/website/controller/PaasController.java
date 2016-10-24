package com.winning.monitor.website.controller;

import com.winning.monitor.data.api.ITransactionDataQueryService;
import com.winning.monitor.data.api.transaction.domain.TransactionStatisticReport;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashSet;

/**
 * Created by Admin on 2016/10/20.
 */
@Controller
public class PaasController {

    @Autowired
    private ITransactionDataQueryService transactionDataQuery;

    @RequestMapping(value = {"/paas/overview"})
    public ModelAndView overview() {
        return new ModelAndView("paas/overview");
    }
    @RequestMapping(value = {"/paas/serverrealtime"})
    public ModelAndView serverrealtime() {
        return new ModelAndView("paas/serverrealtime");
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
        TransactionStatisticReport report = transactionDataQuery.queryLastHourTransactionTypeReportByServer(flname);
        return  report;
    }
}
