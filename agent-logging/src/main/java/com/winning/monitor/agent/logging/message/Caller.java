package com.winning.monitor.agent.logging.message;

/**
 * Created by nicholasyan on 16/10/19.
 */
public class Caller {

    private String name = "";
    private String ip = "";
    private String type = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return this.name + "-" + this.ip + "-" + this.type;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
