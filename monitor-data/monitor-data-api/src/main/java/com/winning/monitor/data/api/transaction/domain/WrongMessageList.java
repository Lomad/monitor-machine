package com.winning.monitor.data.api.transaction.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sao something on 2016/11/29.
 */
public class WrongMessageList {
    List<WrongMessage> wrongMessages = new ArrayList<>();

    public List<WrongMessage> getWrongMessages() {
        return wrongMessages;
    }

    public void setWrongMessages(List<WrongMessage> wrongMessages) {
        this.wrongMessages = wrongMessages;
    }

    public void addWrongMessage(WrongMessage wrongMessage){
        this.wrongMessages.add(wrongMessage);
    }
}
