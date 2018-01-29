package com.wenchao.ipproxypool.domain;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CrawlerOf66IP extends WebCrawler {

    OkHttpClient client = new OkHttpClient();
    String [] urls = {
            "http://www.66ip.cn/areaindex_1/1.html",
            "http://www.66ip.cn/areaindex_2/1.html",
            "http://www.66ip.cn/areaindex_3/1.html",
            "http://www.66ip.cn/areaindex_4/1.html",
            "http://www.66ip.cn/areaindex_5/1.html",
            "http://www.66ip.cn/areaindex_10/1.html",
            "http://www.66ip.cn/areaindex_33/1.html",
            "http://www.66ip.cn/areaindex_29/1.html",
            "http://www.66ip.cn/areaindex_21/1.html",
            "http://www.66ip.cn/areaindex_15/1.html",
            "http://www.66ip.cn/areaindex_16/1.html",
            "http://www.66ip.cn/areaindex_18/1.html",
            "http://www.66ip.cn/areaindex_19/1.html",

    };
    public CrawlerOf66IP() {
    }

    public Collection<ProxyInfo> extract() {

        ConcurrentLinkedQueue<ProxyInfo> crawlerIPs = new ConcurrentLinkedQueue<>();
        Arrays.stream(urls).forEach(s-> {
            Document document = null;
            try {
                document = Jsoup.parse(RequestUtil.request(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
            document.select("table[bordercolor=#6699ff] tr:nth-child(1)").remove();
            Elements elements =
                    document.select("table[bordercolor=#6699ff] tr").remove();
            for(Element element:elements){
                Elements tds = element.select("td");
                String ip = tds.get(0).text();
                int port = Integer.valueOf(tds.get(1).text()) ;
                ProxyInfo proxyInfo  = new ProxyInfo();
                proxyInfo.setIp(ip);
                proxyInfo.setPort(port);
                proxyInfo.setProxyType(Proxy.Type.HTTP);
                crawlerIPs.add(proxyInfo);
            }
           }

        );
        return crawlerIPs;
    }

    public Document document (String url) {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "OkHttp Headers.java")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            byte[] bytes = response.body().bytes();
            String docStr = new String(bytes,"GB2312");
            Document document = Jsoup.parse(docStr);
            return document;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }
}
