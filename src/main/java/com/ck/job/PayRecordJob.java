package com.ck.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.ck.cst.SysCst;
import com.ck.svc.UserSvc;

@Component
@EnableScheduling
public class PayRecordJob {

    private static final Logger LOG = LoggerFactory.getLogger(PayRecordJob.class);
    
    private static final Logger LOG1 = LoggerFactory.getLogger(SysCst.COM_INTERFACE);
    
    
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    
    @Scheduled(cron = "0 0/50 * * * ?")
    public void start1() {
        LOG.info("PayRecordJob 启动1");
    }
    
    @Scheduled(cron = "0 0/50 * * * ?")
    public void start2() {
        LOG1.info("PayRecordJob 启动2");
    }
    
    @Autowired
    private UserSvc userSvc;
    
    @Scheduled(cron = "0/2 50 * * * ?")
    public void start3() {
        //LOG.info("PayRecordJob 启动2");
        //LOG1.info(MapperUtils.toJson(userSvc.queryUserList()));
        /**
        taskExecutor.execute(() -> {
             LOG.info("running...");
             try {
                 Thread.sleep(1500);
             } catch (Exception e) {
                 
             }
         });
        **/
    }
}
