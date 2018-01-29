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

public class CrawlerOfkuaidaili extends WebCrawler {

    OkHttpClient client = new OkHttpClient();
    String [] urls = {
    "https://www.kuaidaili.com/free/",
    };
    public CrawlerOfkuaidaili() {
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
                    if (document != null) {
                        document.select("thead").remove();
                        Elements elements =
                                document.select("table[class=table table-bordered table-striped] tr");
                        for (Element element : elements) {
                            Elements tds = element.select("td");
                            String ip = tds.get(0).text();
                            int port = Integer.valueOf(tds.get(1).text());
                            ProxyInfo proxyInfo = new ProxyInfo();
                            proxyInfo.setIp(ip);
                            proxyInfo.setPort(port);
                            proxyInfo.setProxyType(Proxy.Type.HTTP);
                            crawlerIPs.add(proxyInfo);
                        }
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
