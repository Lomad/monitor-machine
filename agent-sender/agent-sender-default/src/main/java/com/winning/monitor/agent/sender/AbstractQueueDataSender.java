package com.winning.monitor.agent.sender;

import com.winning.monitor.message.DataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicholasyan on 16/9/6.
 */
public abstract class AbstractQueueDataSender<T extends DataEntity> implements Runnable {

    private final int MAX_ENTITIES;
    private final BlockingQueue<T> dataEntityQueue;
    private Thread currentThread;
    private volatile boolean active = true;

    public AbstractQueueDataSender() {
        this(5000, 20);
    }

    public AbstractQueueDataSender(int maxQueueSize, int maxEntities) {
        this.dataEntityQueue = new ArrayBlockingQueue<T>(maxQueueSize);
        this.MAX_ENTITIES = maxEntities;
    }

    public void start() {
        if (currentThread != null)
            return;

        this.active = true;
        this.currentThread = new Thread(this);
        this.currentThread.setDaemon(true);
        this.currentThread.start();

    }

    @Override
    public void run() {
        while (true) {
            if (active == false && dataEntityQueue.size() == 0) {
                return;
            }
            try {
                int maxSize = MAX_ENTITIES;
                List<T> dataEntities = new ArrayList<>();

                while (dataEntityQueue.size() > 0 && maxSize > 0) {
                    T entity = dataEntityQueue.poll(5, TimeUnit.MILLISECONDS);
                    dataEntities.add(entity);
                    maxSize--;
                }

                if (!dataEntities.isEmpty()) {
                    // TODO: 16/9/6 加入监控运行时间
                    boolean success = false;

                    try {
                        //组装数据并发送
                        success = doSendDataEntities(dataEntities);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }

                    if (!success) {

                    }
                } else {
                    Thread.sleep(5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean put(T dataEntity) {
        if (!this.active)
            return false;

        boolean result = true;
        try {
            result = this.dataEntityQueue.offer(dataEntity, 5, TimeUnit.MILLISECONDS);
            return result;
        } catch (Exception e) {
            // TODO: 16/9/6 使用监控日志记录错误
            e.printStackTrace();
        }
        return false;
    }

    public abstract boolean doSendDataEntities(List<T> dataEntities);

    public void stop() {
        this.active = false;
        this.currentThread = null;
    }

}
