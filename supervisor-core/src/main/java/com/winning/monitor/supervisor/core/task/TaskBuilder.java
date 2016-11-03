package com.winning.monitor.supervisor.core.task;

import java.util.Date;

public interface TaskBuilder {

    String getName();

    boolean buildDailyTask(String group, String name, String domain, Date period);

    boolean buildHourlyTask(String group, String name, String domain, Date period);

    boolean buildMonthlyTask(String group, String name, String domain, Date period);

    boolean buildWeeklyTask(String group, String name, String domain, Date period);

}
