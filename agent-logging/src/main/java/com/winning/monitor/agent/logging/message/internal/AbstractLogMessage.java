package com.winning.monitor.agent.logging.message.internal;

import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.utils.MilliSecondTimer;

/**
 * Created by nicholasyan on 16/9/8.
 */
public abstract class AbstractLogMessage implements LogMessage {
    private String type;

    private String name;

    private String status = "unset";

    private long timestampInMillis;

    private CharSequence data;

    private boolean completed;

    public AbstractLogMessage(String type, String name) {
        this.type = String.valueOf(type);
        this.name = String.valueOf(name);
        this.timestampInMillis = MilliSecondTimer.currentTimeMillis();
    }

    @Override
    public void addData(String keyValuePairs) {
        if (this.data == null) {
            this.data = keyValuePairs;
        } else if (this.data instanceof StringBuilder) {
            ((StringBuilder) this.data).append('&').append(keyValuePairs);
        } else {
            StringBuilder sb = new StringBuilder(this.data.length() + keyValuePairs.length() + 16);

            sb.append(this.data).append('&');
            sb.append(keyValuePairs);
            this.data = sb;
        }
    }

    @Override
    public void addData(String key, Object value) {
        if (this.data instanceof StringBuilder) {
            ((StringBuilder) this.data).append('&').append(key).append('=').append(value);
        } else {
            String str = String.valueOf(value);
            int old = this.data == null ? 0 : this.data.length();
            StringBuilder sb = new StringBuilder(old + key.length() + str.length() + 16);

            if (this.data != null) {
                sb.append(this.data).append('&');
            }

            sb.append(key).append('=').append(str);
            this.data = sb;
        }
    }

    @Override
    public CharSequence getData() {
        if (this.data == null) {
            return "";
        } else {
            return this.data;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public void setStatus(Throwable e) {
        this.status = e.getClass().getName();
    }

    @Override
    public long getTimestamp() {
        return this.timestampInMillis;
    }

    public void setTimestamp(long timestamp) {
        this.timestampInMillis = timestamp;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean isCompleted() {
        return this.completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean isSuccess() {
        return LogMessage.SUCCESS.equals(this.status);
    }


}