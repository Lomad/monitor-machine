package com.winning.monitor.superisor.consumer.logging.transaction.entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class Machine {
    private String m_ip;

    private Map<String, TransactionType> m_types = new LinkedHashMap<String, TransactionType>();

    public Machine() {
    }

    public Machine(String ip) {
        m_ip = ip;
    }


    public Machine addType(TransactionType type) {
        m_types.put(type.getId(), type);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Machine) {
            Machine _o = (Machine) obj;

            if (!m_ip.equals(_o.getIp())) {
                return false;
            }

            return true;
        }

        return false;
    }

    public TransactionType findType(String id) {
        return m_types.get(id);
    }

    public TransactionType findOrCreateType(String id) {
        TransactionType type = m_types.get(id);

        if (type == null) {
            synchronized (m_types) {
                type = m_types.get(id);

                if (type == null) {
                    type = new TransactionType(id);
                    m_types.put(id, type);
                }
            }
        }

        return type;
    }

    public String getIp() {
        return m_ip;
    }

    public Machine setIp(String ip) {
        m_ip = ip;
        return this;
    }

    public Map<String, TransactionType> getTypes() {
        return m_types;
    }

    @Override
    public int hashCode() {
        int hash = 0;

        hash = hash * 31 + (m_ip == null ? 0 : m_ip.hashCode());

        return hash;
    }

    public TransactionType removeType(String id) {
        return m_types.remove(id);
    }

}
