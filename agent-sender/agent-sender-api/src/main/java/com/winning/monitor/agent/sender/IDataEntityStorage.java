package com.winning.monitor.agent.sender;

import com.winning.monitor.message.DataEntity;

import java.util.List;

/**
 * Created by nicholasyan on 16/9/6.
 */
public interface IDataEntityStorage<T extends DataEntity> {

    void initialize();

    boolean put(T data);

    List<T> get(int size);

    int remainSize();

    void shutdown();

}
