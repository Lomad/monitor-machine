package com.winning.monitor.superisor.consumer.logging.transaction.dto;

import com.winning.monitor.superisor.consumer.logging.transaction.entity.Machine;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionName;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionReport;
import com.winning.monitor.superisor.consumer.logging.transaction.entity.TransactionType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasyan on 16/9/12.
 */
public class TransactionReportConverter {

    public static DateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static TransactionReportDTO toTransactionReportDTO(TransactionReport transactionReport) {
        TransactionReportDTO transactionReportDTO = new TransactionReportDTO();
        transactionReportDTO.setId(transactionReport.getId());
        transactionReportDTO.setDomain(transactionReport.getDomain());
        List<String> ips = new ArrayList<>(transactionReport.getIps());
        transactionReportDTO.setIps(ips);
        transactionReportDTO.setStartTime(simpleDateTimeFormat.format(transactionReport.getStartTime()));
        transactionReportDTO.setEndTime(simpleDateTimeFormat.format(transactionReport.getEndTime()));

        List<TransactionMachineDTO> machineList = new ArrayList<>();
        for (Machine machine : transactionReport.getMachines().values()) {
            TransactionMachineDTO machineDTO = toTransactionMachineDTO(machine);
            machineList.add(machineDTO);
        }
        transactionReportDTO.setMachines(machineList);
        return transactionReportDTO;
    }

    public static TransactionMachineDTO toTransactionMachineDTO(Machine machine) {
        TransactionMachineDTO transactionMachineDTO = new TransactionMachineDTO();
        transactionMachineDTO.setIp(machine.getIp());
        List<TransactionTypeDTO> typeDTOList = new ArrayList<>();
        for (TransactionType transactionType : machine.getTypes().values()) {
            typeDTOList.add(toTransactionTypeDTO(transactionType));
        }
        transactionMachineDTO.setTransactionTypes(typeDTOList);
        return transactionMachineDTO;
    }

    public static TransactionTypeDTO toTransactionTypeDTO(TransactionType transactionType) {
        TransactionTypeDTO transactionTypeDTO = new TransactionTypeDTO();
        transactionTypeDTO.setId(transactionType.getId());
        transactionTypeDTO.setTotalCount(transactionType.getTotalCount());
        transactionTypeDTO.setFailCount(transactionType.getFailCount());
        transactionTypeDTO.setMin(transactionType.getMin());
        transactionTypeDTO.setMax(transactionType.getMax());
        transactionTypeDTO.setSum(transactionType.getSum());
        transactionTypeDTO.setSum2(transactionType.getSum2());
        transactionTypeDTO.setRange2s(transactionType.getRange2s());
        transactionTypeDTO.setAllDurations(transactionType.getAllDurations());
        List<TransactionNameDTO> nameDTOList = new ArrayList<>();
        for (TransactionName transactionName : transactionType.getNames().values()) {
            nameDTOList.add(toTransactionNameDTO(transactionName));
        }
        transactionTypeDTO.setTransactionNames(nameDTOList);
        return transactionTypeDTO;
    }

    public static TransactionNameDTO toTransactionNameDTO(TransactionName transactionName) {
        TransactionNameDTO transactionNameDTO = new TransactionNameDTO();
        transactionNameDTO.setId(transactionName.getId());
        transactionNameDTO.setTotalCount(transactionName.getTotalCount());
        transactionNameDTO.setFailCount(transactionName.getFailCount());
        transactionNameDTO.setMin(transactionName.getMin());
        transactionNameDTO.setMax(transactionName.getMax());
        transactionNameDTO.setSum(transactionName.getSum());
        transactionNameDTO.setSum2(transactionName.getSum2());
        transactionNameDTO.setRanges(transactionName.getRanges());
        transactionNameDTO.setDurations(transactionName.getDurations());
        transactionNameDTO.setAllDurations(transactionName.getAllDurations());
        return transactionNameDTO;
    }


}
