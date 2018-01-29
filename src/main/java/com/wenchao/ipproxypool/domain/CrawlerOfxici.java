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

public class CrawlerOfxici extends WebCrawler {

    OkHttpClient client = new OkHttpClient();
    String [] urls = {
    "http://www.xicidaili.com/nn/",
//    "http://www.xicidaili.com/nn/2",
//    "http://www.xicidaili.com/nn/3",
//    "http://www.xicidaili.com/nn/4",
    };
    public CrawlerOfxici() {
    }

    public Collection<ProxyInfo> extract() {

        ConcurrentLinkedQueue<ProxyInfo> crawlerIPs = new ConcurrentLinkedQueue<>();
        Arrays.stream(urls).forEach(s-> {
                    Document document = null;
                    try {
                        document = document(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (document != null) {
                        document.select("#ip_list tr").get(0).remove();
                        Elements elements =
                                document.select("#ip_list tr");
                        for (Element element : elements) {
                            Elements tds = element.select("td");
                            if(tds.size()==0){
                                continue;
                            }
                            String ip = tds.get(1).text();
                            int port = Integer.valueOf(tds.get(2).text());
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
                .header("User-Agent", "crawler")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            byte[] bytes = response.body().bytes();
            String docStr = new String(bytes,"UTF-8");
            Document document = Jsoup.parse(docStr);
            return document;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

}
