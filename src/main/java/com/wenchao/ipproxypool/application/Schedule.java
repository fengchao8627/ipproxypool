package com.wenchao.ipproxypool.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.wenchao.ipproxypool.domain.DataManage;
import com.wenchao.ipproxypool.domain.ProxyInfo;
import com.wenchao.ipproxypool.repository.IpProxyRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class Schedule {
    @Autowired
    IpProxyRepository ipProxyRepository;

    @Scheduled(fixedDelay = 2*100)
    public void launchs() throws InterruptedException {
        Collection<ProxyInfo> proxyInfos =ipProxyRepository.getAllFromMemory();
        if(proxyInfos.size()<=0){
            return ;
        }
        DataManage dataManage = new DataManage(proxyInfos.stream().collect(Collectors.toList()));
        dataManage.launch();
        ipProxyRepository.batchDeleteFromMemory(dataManage.getUnusefulProxyQueue());
        System.out.println("delete all");


    }
}
