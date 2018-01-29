package com.wenchao.ipproxypool.application;

import com.wenchao.ipproxypool.domain.CrawlerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.wenchao.ipproxypool.repository.IpProxyRepository;

import java.util.stream.Collectors;

@Component
public class ScheduleService {
    @Autowired
    IpProxyRepository ipProxyRepository;

    @Scheduled(fixedDelay = 1000*60*10)
    public void launch() throws InterruptedException {
        System.err.println("coming ");
        CrawlerManager crawlerManager = new CrawlerManager();

        crawlerManager.launch();

        System.out.println("current  ip  size "+crawlerManager.getActiveProxys().size());
        System.out.println(crawlerManager.getActiveProxys());
        System.err.println("come out");

        ipProxyRepository.saveIntoMemory(crawlerManager.getActiveProxys().stream().collect(Collectors.toSet()));
    }


}
