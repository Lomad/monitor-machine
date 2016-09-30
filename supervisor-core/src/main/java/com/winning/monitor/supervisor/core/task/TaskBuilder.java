package com.winning.monitor.supervisor.core.task;

import java.util.Date;

public interface TaskBuilder {

    String getName();

    boolean buildDailyTask(String name, String domain, Date period);

    boolean buildHourlyTask(String name, String domain, Date period);

    boolean buildMonthlyTask(String name, String domain, Date period);

    boolean buildWeeklyTask(String name, String domain, Date period);

}
