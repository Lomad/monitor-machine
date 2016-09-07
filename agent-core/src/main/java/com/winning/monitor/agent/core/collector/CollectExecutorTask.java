package com.winning.monitor.agent.core.collector;

import com.winning.monitor.agent.collector.api.executor.DataCollectExecutor;
import com.winning.monitor.agent.sender.ICollectDataSender;
import com.winning.monitor.message.collector.CollectData;

/**
 * Created by nicholasyan on 16/9/5.
 */
public class CollectExecutorTask implements Runnable {

    private final DataCollectExecutor executor;
    private final ICollectDataSender collectDataSender;

    private boolean running = false;

    public CollectExecutorTask(DataCollectExecutor executor, ICollectDataSender collectDataSender) {
        this.executor = executor;
        this.collectDataSender = collectDataSender;
    }

    public DataCollectExecutor getDataCollectExecutor() {
        return this.executor;
    }

    public boolean isRunning() {
        return this.running;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            if (System.currentTimeMillis() < this.executor.getNextCollectTime())
                return;

            this.running = true;

            CollectData collectData = this.executor.collect();
            // 发送收集数据
            this.collectDataSender.send(collectData);

        } catch (Exception e) {

        } finally {
            this.running = false;
        }
    }
}
