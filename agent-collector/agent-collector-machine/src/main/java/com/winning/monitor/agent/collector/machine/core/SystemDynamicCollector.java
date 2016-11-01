package com.winning.monitor.agent.collector.machine.core;

import com.winning.monitor.agent.collector.api.core.IDataCollector;
import com.winning.monitor.agent.collector.api.entity.CollectData;
import org.hyperic.sigar.*;

/**
 * Created by nicholasyan on 16/8/26.
 */
public class SystemDynamicCollector implements IDataCollector {

    private Sigar sigar = new Sigar();

    @Override
    public CollectData collect() {
        CollectData collectData = new CollectData("SystemDynamic");

        this.collectCPU(collectData);
        this.collectMemory(collectData);

        return collectData;
    }


    private void collectCPU(CollectData collectData) {
        try {
            CpuInfo infos[] = sigar.getCpuInfoList();
            CpuPerc cpuList[] = sigar.getCpuPercList();
            double totalUsed = 0, userUsed = 0;

            for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用
                CpuPerc cpuPerc = cpuList[i];
                totalUsed = totalUsed + cpuPerc.getCombined();
                userUsed = userUsed + cpuPerc.getUser();
            }

            collectData.put("cpuTotalUsed", Math.ceil(totalUsed * 100 / infos.length));
            collectData.put("cpuUserUserd", Math.ceil(userUsed * 100 / infos.length));

        } catch (SigarException e) {
            e.printStackTrace();
        }
    }

    private void collectMemory(CollectData collectData) {
        try {
            Mem mem = sigar.getMem();
//            Swap swap = sigar.getSwap();

            collectData.put("memUsed", mem.getUsed() / 1024L / 1024);
            collectData.put("memFree", mem.getFree() / 1024L / 1024);
//            collectData.put("swpMUsed", swap.getUsed() / 1024L / 1024);
//            collectData.put("swpMFree", swap.getFree() / 1024L / 1024);
//            collectData.put("swpMPageIn", swap.getPageIn() / 1024L / 1024);
//            collectData.put("swpMPageOut", swap.getPageOut() / 1024L / 1024);
        } catch (SigarException e) {
            e.printStackTrace();
        }
    }


}