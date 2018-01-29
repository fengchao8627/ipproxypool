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

public class CrawlerOfYaoYao extends WebCrawler {

    OkHttpClient client = new OkHttpClient();
    String [] urls = {
    "http://www.httpsdaili.com/?stype=1",
    };
    public CrawlerOfYaoYao() {
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
                        document.select("table[class=table table-striped] tr:nth-child(1)").remove();
                        Elements elements =
                                document.select(".odd");
                        for (Element element : elements) {
                            Elements tds = element.select(".style1");
                            String ip = tds.get(0).text();
                            Element portElement = element.select(".style2").get(0);
                            int port = Integer.valueOf(portElement.text().trim());
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
