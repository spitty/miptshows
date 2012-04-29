/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ncmipt.miptshows.scheduler;

import org.ncmipt.miptshows.db.Updater;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author Vlad
 */
public class RunMeJob implements Job
{

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException
    {
        System.out.println("Hello!");
        //Refresher.updateDB("my_share");
    }

}
