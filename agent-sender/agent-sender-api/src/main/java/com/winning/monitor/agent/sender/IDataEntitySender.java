package com.winning.monitor.agent.sender;

import com.winning.monitor.message.DataEntity;

/**
 * Created by nicholasyan on 16/9/6.
 */
public interface IDataEntitySender {

    boolean send(DataEntity data);

}
