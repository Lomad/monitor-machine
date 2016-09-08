package com.winning.monitor.agent.logging.message.internal;


import com.winning.monitor.agent.logging.message.LogMessage;
import com.winning.monitor.agent.logging.message.MessageTree;

public class DefaultMessageTree implements MessageTree {


    private String m_domain;

    private String m_hostName;

    private String m_ipAddress;

    private LogMessage m_message;

    private String m_messageId;

    private String m_parentMessageId;

    private String m_rootMessageId;

    private String m_sessionToken;

    private String m_threadGroupName;

    private String m_threadId;

    private String m_threadName;

    private boolean m_sample = true;

    @Override
    public MessageTree copy() {
        MessageTree tree = new DefaultMessageTree();

        tree.setDomain(m_domain);
        tree.setHostName(m_hostName);
        tree.setIpAddress(m_ipAddress);
        tree.setMessageId(m_messageId);
        tree.setParentMessageId(m_parentMessageId);
        tree.setRootMessageId(m_rootMessageId);
        tree.setSessionToken(m_sessionToken);
        tree.setThreadGroupName(m_threadGroupName);
        tree.setThreadId(m_threadId);
        tree.setThreadName(m_threadName);
        tree.setMessage(m_message);
        tree.setSample(m_sample);

        return tree;
    }


    @Override
    public String getDomain() {
        return m_domain;
    }

    @Override
    public void setDomain(String domain) {
        m_domain = domain;
    }

    @Override
    public String getHostName() {
        return m_hostName;
    }

    @Override
    public void setHostName(String hostName) {
        m_hostName = hostName;
    }

    @Override
    public String getIpAddress() {
        return m_ipAddress;
    }

    @Override
    public void setIpAddress(String ipAddress) {
        m_ipAddress = ipAddress;
    }

    @Override
    public LogMessage getMessage() {
        return m_message;
    }

    @Override
    public void setMessage(LogMessage message) {
        m_message = message;
    }

    @Override
    public String getMessageId() {
        return m_messageId;
    }

    @Override
    public void setMessageId(String messageId) {
        if (messageId != null && messageId.length() > 0) {
            m_messageId = messageId;
        }
    }

    @Override
    public String getParentMessageId() {
        return m_parentMessageId;
    }

    @Override
    public void setParentMessageId(String parentMessageId) {
        if (parentMessageId != null && parentMessageId.length() > 0) {
            m_parentMessageId = parentMessageId;
        }
    }

    @Override
    public String getRootMessageId() {
        return m_rootMessageId;
    }

    @Override
    public void setRootMessageId(String rootMessageId) {
        if (rootMessageId != null && rootMessageId.length() > 0) {
            m_rootMessageId = rootMessageId;
        }
    }

    @Override
    public String getSessionToken() {
        return m_sessionToken;
    }

    @Override
    public void setSessionToken(String sessionToken) {
        m_sessionToken = sessionToken;
    }

    @Override
    public String getThreadGroupName() {
        return m_threadGroupName;
    }

    @Override
    public void setThreadGroupName(String threadGroupName) {
        m_threadGroupName = threadGroupName;
    }

    @Override
    public String getThreadId() {
        return m_threadId;
    }

    @Override
    public void setThreadId(String threadId) {
        m_threadId = threadId;
    }

    @Override
    public String getThreadName() {
        return m_threadName;
    }

    @Override
    public void setThreadName(String threadName) {
        m_threadName = threadName;
    }

    @Override
    public boolean isSample() {
        return m_sample;
    }

    @Override
    public void setSample(boolean sample) {
        m_sample = sample;
    }


}
