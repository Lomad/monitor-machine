package com.winning.monitor.superisor.consumer.collector;

import com.winning.monitor.agent.collector.api.entity.CollectData;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by nicholasyan on 16/10/14.
 */
public class DefaultCollectDataAnalyzeQueue
        implements ICollectDataAnalyzeQueue, Runnable {

    protected final LinkedBlockingQueue<CollectData> queue;
    protected final ICollectDataAnalyzer collectDataAnalyzer;
    protected final Thread thread;
    private boolean active = true;

    public DefaultCollectDataAnalyzeQueue(ICollectDataAnalyzer collectDataAnalyzer) {
        this(1000, collectDataAnalyzer);
    }

    public DefaultCollectDataAnalyzeQueue(int size, ICollectDataAnalyzer collectDataAnalyzer) {
        this.queue = new LinkedBlockingQueue<>(size);
        this.collectDataAnalyzer = collectDataAnalyzer;

        this.thread = new Thread(this);
        this.thread.setDaemon(true);
        this.thread.start();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shutdown();
            }
        }));
    }

    @Override
    public void offer(CollectData collectData) {
        this.queue.offer(collectData);
    }

    @Override
    public String getCollectTypeName() {
        return this.collectDataAnalyzer.getCollectType();
    }

    protected CollectData poll() {
        try {
            return queue.poll(5, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public void run() {
        while (active) {
            while (this.queue.size() > 0) {
                //当队列中有数据时
                try {
                    CollectData collectData = this.poll();
                    this.collectDataAnalyzer.analyze(collectData);
                } catch (Throwable e) {

                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public void shutdown() {
        this.active = false;
    }
}
