package com.huzihan.web.async;

import org.apache.commons.lang3.RandomStringUtils;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

@RestController
public class AsyncController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MockQueue mockQueue;

    @Autowired
    private DeferredResultHolder deferredResultHolder;

    @RequestMapping("/order")
    public Callable<String> order() {
        logger.info("主线程开始");
        Callable<String> result = new Callable<String>() {
            @Override
            public String call() throws Exception {
                logger.info("副线程开始");
                TimeUnit.SECONDS.sleep(1);
                logger.info("副线程结束");
                return "seccuss";
            }
        };
        logger.info("主线程结束");
        return result;
    }

    @RequestMapping("/order2")
    public DeferredResult<String> oder2(){
        logger.info("主线程开始");

        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);

        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResultHolder.getMap().put(orderNumber,deferredResult);

        logger.info("主线程结束");
        return deferredResult;
    }

}
