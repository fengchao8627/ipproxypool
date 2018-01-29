package com.wenchao.ipproxypool.domain;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class DataManage {
    private ExecutorService executorService = Executors.newFixedThreadPool(200);

    private LinkedBlockingDeque<ProxyInfo> proxyInfoQueue = new LinkedBlockingDeque<>();

    private LinkedBlockingDeque<ProxyInfo> unusefulProxyQueue = new LinkedBlockingDeque<>();

    public DataManage(List<ProxyInfo> proxyInfos){
        this.proxyInfoQueue = new LinkedBlockingDeque<>(proxyInfos);

    }

    public void launch() throws InterruptedException {

        while(proxyInfoQueue.size()>0) {

           DataVerify dataVerify = new DataVerify(proxyInfoQueue.poll(), unusefulProxyQueue);
            executorService.execute(dataVerify);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    public LinkedBlockingDeque<ProxyInfo> getUnusefulProxyQueue() {
        return unusefulProxyQueue;
    }

    public void setUnusefulProxyQueue(LinkedBlockingDeque<ProxyInfo> unusefulProxyQueue) {
        this.unusefulProxyQueue = unusefulProxyQueue;
    }
}
