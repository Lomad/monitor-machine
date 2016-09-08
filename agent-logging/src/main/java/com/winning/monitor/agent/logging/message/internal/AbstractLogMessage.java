package com.winning.monitor.agent.logging.message.internal;

import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.utils.MilliSecondTimer;

/**
 * Created by nicholasyan on 16/9/8.
 */
public abstract class AbstractLogMessage implements LogMessage {
    private String m_type;

    private String m_name;

    private String m_status = "unset";

    private long m_timestampInMillis;

    private CharSequence m_data;

    private boolean m_completed;

    public AbstractLogMessage(String type, String name) {
        m_type = String.valueOf(type);
        m_name = String.valueOf(name);
        m_timestampInMillis = MilliSecondTimer.currentTimeMillis();
    }

    @Override
    public void addData(String keyValuePairs) {
        if (m_data == null) {
            m_data = keyValuePairs;
        } else if (m_data instanceof StringBuilder) {
            ((StringBuilder) m_data).append('&').append(keyValuePairs);
        } else {
            StringBuilder sb = new StringBuilder(m_data.length() + keyValuePairs.length() + 16);

            sb.append(m_data).append('&');
            sb.append(keyValuePairs);
            m_data = sb;
        }
    }

    @Override
    public void addData(String key, Object value) {
        if (m_data instanceof StringBuilder) {
            ((StringBuilder) m_data).append('&').append(key).append('=').append(value);
        } else {
            String str = String.valueOf(value);
            int old = m_data == null ? 0 : m_data.length();
            StringBuilder sb = new StringBuilder(old + key.length() + str.length() + 16);

            if (m_data != null) {
                sb.append(m_data).append('&');
            }

            sb.append(key).append('=').append(str);
            m_data = sb;
        }
    }

    @Override
    public CharSequence getData() {
        if (m_data == null) {
            return "";
        } else {
            return m_data;
        }
    }

    @Override
    public String getName() {
        return m_name;
    }

    public void setName(String name) {
        m_name = name;
    }

    @Override
    public String getStatus() {
        return m_status;
    }

    @Override
    public void setStatus(String status) {
        m_status = status;
    }

    @Override
    public void setStatus(Throwable e) {
        m_status = e.getClass().getName();
    }

    @Override
    public long getTimestamp() {
        return m_timestampInMillis;
    }

    public void setTimestamp(long timestamp) {
        m_timestampInMillis = timestamp;
    }

    @Override
    public String getType() {
        return m_type;
    }

    public void setType(String type) {
        m_type = type;
    }

    @Override
    public boolean isCompleted() {
        return m_completed;
    }

    public void setCompleted(boolean completed) {
        m_completed = completed;
    }

    @Override
    public boolean isSuccess() {
        return LogMessage.SUCCESS.equals(m_status);
    }


}