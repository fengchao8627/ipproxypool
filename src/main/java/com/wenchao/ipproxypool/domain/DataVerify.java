package com.wenchao.ipproxypool.domain;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import com.wenchao.ipproxypool.repository.IpProxyRepository;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.LinkedBlockingDeque;

public class DataVerify implements Runnable{
    static final String sampleURL = "http://pl.ifeng.com/" ;

    protected ProxyInfo proxyInfo;
    protected LinkedBlockingDeque<ProxyInfo> collection =new LinkedBlockingDeque<>();

    @Autowired
    protected IpProxyRepository ipProxyRepository;
    public DataVerify( ProxyInfo proxyInfo ,LinkedBlockingDeque<ProxyInfo> proxyInfos) {
        this.proxyInfo = proxyInfo;
        this.collection = proxyInfos;
    }

    @Override
    public void run() {

            try {
                Document document = null;
                boolean active = true;
                System.out.println("verify");
                String url = proxyInfo.getIp();
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url, proxyInfo.getPort()));
                try {
//                    document =   Jsoup.connect(sampleURL).proxy(proxy).get();
//                    document =   Jsoup.connect(sampleURL).proxy(proxy).get();
                    Jsoup.parse(RequestUtil.request(sampleURL, proxy)) ;
                    document = Jsoup.parse(RequestUtil.request(sampleURL, proxy)) ;

                } catch (Exception e) {
                    System.out.println(proxyInfo+" "+"unuseful");
                    active = false;
                    collection.add(proxyInfo);
                }
                if (document != null) {
                    if (!(active && document.title().equals("凤凰评论-凤凰网"))) {
                        System.out.println(url + " " + proxy);
                        collection.add(proxyInfo);
                    }
                }



            }finally {
            }

    }


}
