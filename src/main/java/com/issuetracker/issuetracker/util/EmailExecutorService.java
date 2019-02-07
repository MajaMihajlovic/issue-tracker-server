package com.issuetracker.issuetracker.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailExecutorService {

    private static ExecutorService emailExecutor ;

    public static ExecutorService getEmailExecuto(){
        if(emailExecutor==null)
            emailExecutor=Executors.newCachedThreadPool();
        return emailExecutor;
    }
}
