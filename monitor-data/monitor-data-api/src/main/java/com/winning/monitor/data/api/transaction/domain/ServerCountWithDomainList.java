package com.winning.monitor.data.api.transaction.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sao something on 2016/11/25.
 */
public class ServerCountWithDomainList {
    private List<ServerCountWithDomain> serverCountWithDomains = new ArrayList<ServerCountWithDomain>();

    public void addCountMessage(ServerCountWithDomain serverCountWithDomain){
        this.serverCountWithDomains.add(serverCountWithDomain);
    }

    public List<ServerCountWithDomain> getServerCountWithDomain() {
        return serverCountWithDomains;
    }

    public void setServerCountWithDomain(List<ServerCountWithDomain> serverCountWithDomains) {
        this.serverCountWithDomains = serverCountWithDomains;
    }
}
