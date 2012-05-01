package org.ncmipt.miptshows.scheduler;

import java.text.ParseException;
import org.apache.log4j.LogManager;
import org.ncmipt.miptshows.properties.PropertiesManager;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.RemoteMBeanScheduler;
import org.quartz.impl.RemoteScheduler;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 *
 * @author Vlad
 */
public class DataScheduler
{

//    private static final org.apache.log4j.Logger LOG = LogManager.getLogger(DataScheduler.class);
//    static Scheduler scheduler;
//
    static
    {
        System.out.println("Static block!!!!121212 "); 

        MyScheduler sch = new MyScheduler();

        //Create JobDetail
        JobDetailImpl job = new JobDetailImpl();
        JobKey key = JobKey.jobKey("key");

        job.setDescription("UpdateDatabase");
        job.setJobClass(RunMeJob.class);
        job.setKey(key);
        System.out.println("ANALIT");
        //Create Trigger
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName("SchedulerTrigger");
        trigger.setJobKey(key);
        System.out.println("middle");
//        StdSchedulerFactory factory = new StdSchedulerFactory();
        
        try
        { 
            System.out.println("-1");
//            String schedulerExpression = PropertiesManager.getSchedulerExpression();
            trigger.setCronExpression("0/5 * * * * ?");
            //@see http://en.wikipedia.org/wiki/CRON_expression#CRON_expression
            System.out.println("try0");

            System.out.println("factory done");
//            scheduler =  new StdScheduler(null) ; //.getScheduler();
//
//            System.out.println("try1");
//            scheduler.start();
//            System.out.println("try2");
//            scheduler.scheduleJob(job, trigger);


        } catch (Throwable ex)
        {
            System.out.println(ex.getClass());
        } finally
        {
            System.out.println("finally");
        }

    }
}
