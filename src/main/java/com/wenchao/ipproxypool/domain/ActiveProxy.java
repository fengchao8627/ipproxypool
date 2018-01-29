package com.wenchao.ipproxypool.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;

public class ActiveProxy implements Runnable {
    static final String sampleURL = "http://pl.ifeng.com/";
    protected LinkedBlockingDeque<ProxyInfo> collection;

    protected ProxyInfo proxyInfo;
    protected Lock lock;

    public ActiveProxy(LinkedBlockingDeque<ProxyInfo> collection, ProxyInfo proxyInfo) {
        this.collection = collection;
        this.proxyInfo = proxyInfo;
    }

    @Override
    public void run() {

        try {
            boolean active = true;
            Document document = null;
            //todo log
            System.out.println("validate");
            String url = proxyInfo.getIp();
            Proxy proxy = new Proxy(proxyInfo.getProxyType(), new InetSocketAddress(url, proxyInfo.getPort()));
            try {
                Jsoup.parse(RequestUtil.request(sampleURL, proxy));
                document = Jsoup.parse(RequestUtil.request(sampleURL, proxy));
            } catch (Exception e) {
                active = false;
            }
            if (document != null) {
                if (active && document.title().equals("凤凰评论-凤凰网")) {
                    //todo log
                    System.out.println(url + " " + proxyInfo.getPort());
                    collection.add(proxyInfo);
                }
            }
        } finally {
        }

    }

    public LinkedBlockingDeque<ProxyInfo> getCollection() {
        return collection;
    }

    public void setCollection(LinkedBlockingDeque<ProxyInfo> collection) {
        this.collection = collection;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }
}
