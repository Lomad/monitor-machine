package com.winning.monitor.supervisor.core.message.handle;

import com.winning.monitor.message.Message;

/**
 * Created by nicholasyan on 16/9/9.
 */
public interface MessageHandlerManager {

    void handle(Message message);

}
