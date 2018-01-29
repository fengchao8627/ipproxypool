package com.wenchao.ipproxypool.domain;

import java.util.Collection;
import java.util.concurrent.locks.Lock;

public abstract class WebCrawler implements Runnable, Extractor {


    protected Collection<ProxyInfo> proxySeeds;

    protected String url;

    protected Lock lock;


    public WebCrawler(Collection<ProxyInfo> proxySeeds, String url) {

        this.proxySeeds = proxySeeds;
        this.url = url;
    }

    public WebCrawler() {
    }

    public void run() {
        proxySeeds.addAll(extract());
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Collection<ProxyInfo> getProxySeeds() {
        return proxySeeds;
    }

    public void setProxySeeds(Collection<ProxyInfo> proxySeeds) {
        this.proxySeeds = proxySeeds;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
