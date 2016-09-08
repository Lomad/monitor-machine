package com.winning.monitor.agent.core.sender;


import com.winning.monitor.agent.sender.IDataEntitySenderTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasyan on 16/9/7.
 */
public class DataEntitySenderTaskManager {

    private final DataSenderContext dataSenderContext;
    private final List<IDataEntitySenderTask> senderTasks = new ArrayList<>();

    public DataEntitySenderTaskManager(DataSenderContext dataSenderContext,
                                       List<IDataEntitySenderTask> tasks) {
        this.dataSenderContext = dataSenderContext;
        this.senderTasks.addAll(tasks);
    }


    public void initialze() {
        for (IDataEntitySenderTask senderTask : senderTasks) {
            senderTask.initialize(dataSenderContext.getMessageTransport());
        }
    }

    public void start() {
        for (IDataEntitySenderTask senderTask : senderTasks) {
            senderTask.start();
        }
    }

    public void shutdown() {
        for (IDataEntitySenderTask senderTask : senderTasks) {
            senderTask.shutdown();
        }
    }


}
