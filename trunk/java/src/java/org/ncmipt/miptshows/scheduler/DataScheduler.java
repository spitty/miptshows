package org.ncmipt.miptshows.scheduler;

import java.text.ParseException;
import org.apache.log4j.LogManager;
import org.ncmipt.miptshows.properties.PropertiesManager;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

/**
 *
 * @author Vlad
 */
public class DataScheduler
{

    private static final org.apache.log4j.Logger LOG = LogManager.getLogger(DataScheduler.class);

    static
    {
        System.out.println("Static block!");
        main(new String[0]);
    }

    public static void main(String[] args)
    {
        //Create JobDetail
        JobDetailImpl job = new JobDetailImpl();
        JobKey key = JobKey.jobKey("key");

        job.setDescription("UpdateDatabase");
        job.setJobClass(RunMeJob.class);
        job.setKey(key);

        //Create Trigger
        CronTriggerImpl trigger = new CronTriggerImpl();
        trigger.setName("SchedulerTrigger");
        trigger.setJobKey(key);
        try
        {
            String schedulerExpression = PropertiesManager.getSchedulerExpression();
            trigger.setCronExpression(schedulerExpression);
            //@see http://en.wikipedia.org/wiki/CRON_expression#CRON_expression
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex)
        {
            LOG.error("Can't create scheduler", ex);
        } catch (ParseException ex)
        {
            LOG.error("Can't parse CronTrigger expression", ex);
        }



    }
}
