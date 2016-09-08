package com.winning.monitor.message.collector;

import com.winning.monitor.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nicholasyan on 16/9/5.
 */
public class CollectorMessage implements Message {

    public static final String TYPE_NAME = "Collector";

    private List<CollectData> datas = new ArrayList<CollectData>();

    public List<CollectData> getDatas() {
        return datas;
    }

    public void setDatas(List<CollectData> datas) {
        this.datas = datas;
    }

    public void addCollectDatas(List<CollectData> collectDatas) {
        this.datas.addAll(collectDatas);
    }

    public void addCollectData(CollectData collectData) {
        this.datas.add(collectData);
    }
}
