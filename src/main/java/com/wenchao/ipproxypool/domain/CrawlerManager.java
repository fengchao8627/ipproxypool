package com.wenchao.ipproxypool.domain;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CrawlerManager {

   static LinkedBlockingDeque<WebCrawler> crawlerQueue = new LinkedBlockingDeque<>();
   static LinkedBlockingDeque<ProxyInfo> proxySeeds = new LinkedBlockingDeque<>();
   static LinkedBlockingDeque<ProxyInfo> activeProxys = new LinkedBlockingDeque<>();
   //todo  all crawler info should put into the property file
   static String[] crawlers = {
    "com.wenchao.ipproxypool.domain.CrawlerOf66IP",
    "com.wenchao.ipproxypool.domain.CrawlerOf31",
    "com.wenchao.ipproxypool.domain.CrawlerOfAllNet",
    "com.wenchao.ipproxypool.domain.CrawlerOfYaoYao",
    "com.wenchao.ipproxypool.domain.CrawlerOf181",
    "com.wenchao.ipproxypool.domain.CrawlerOfkaixindaili",
    "com.wenchao.ipproxypool.domain.CrawlerOfkuaidaili",
    "com.wenchao.ipproxypool.domain.CrawlerOf360",
    "com.wenchao.ipproxypool.domain.CrawlerOfxici",
   };
   private ExecutorService executorService = Executors.newFixedThreadPool(10);
   private ExecutorService activeExecutorService = Executors.newFixedThreadPool(200);
   static {
        Arrays.stream(crawlers).forEach(
               (String s) -> {
                   try {
                      Class<?> crawlerClass = Class.forName(s);
                      Constructor<?> crawlerConstructor =  crawlerClass.getConstructor();
                      WebCrawler crawler = (WebCrawler) crawlerConstructor.newInstance();
                      crawler.setProxySeeds(proxySeeds);
                      crawlerQueue.add(crawler);
                   } catch (ClassNotFoundException e) {
                       e.printStackTrace();
                   } catch (NoSuchMethodException e) {
                       e.printStackTrace();
                   } catch (IllegalAccessException e) {
                       e.printStackTrace();
                   } catch (InstantiationException e) {
                       e.printStackTrace();
                   } catch (InvocationTargetException e) {
                       e.printStackTrace();
                   }
               }
       );

   }

    public LinkedBlockingDeque<ProxyInfo> getProxySeeds() {
        return proxySeeds;
    }

    public void launch() throws InterruptedException {

    crawlerQueue.stream().forEach(s->executorService.execute(s));
    executorService.shutdown();
    executorService.awaitTermination(Long.MAX_VALUE,TimeUnit.SECONDS);
    while(proxySeeds.size()>0) {
        ActiveProxy activeProxy = new ActiveProxy(activeProxys, proxySeeds.poll());
        activeExecutorService.execute(activeProxy);
     }
        activeExecutorService.shutdown();
        activeExecutorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public static LinkedBlockingDeque<ProxyInfo> getActiveProxys() {
        return activeProxys;
    }

    public static void setActiveProxys(LinkedBlockingDeque<ProxyInfo> activeProxys) {
        CrawlerManager.activeProxys = activeProxys;
    }

}
