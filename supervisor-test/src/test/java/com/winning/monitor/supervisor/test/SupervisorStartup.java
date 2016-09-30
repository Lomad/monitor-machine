package com.winning.monitor.supervisor.test;

import com.winning.monitor.supervisor.core.SpringContainer;

/**
 * Created by nicholasyan on 16/9/9.
 */
public class SupervisorStartup {


    public static void main(String[] args) {
        new SpringContainer().startAndWait();
    }


}
