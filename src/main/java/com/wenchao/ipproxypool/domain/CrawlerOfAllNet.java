package com.wenchao.ipproxypool.domain;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.Proxy;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

public class CrawlerOfAllNet extends WebCrawler {

    OkHttpClient client = new OkHttpClient();
    String [] urls = {
    "http://www.goubanjia.com/free/gngn/index.shtml",
    };
    public CrawlerOfAllNet() {
    }

    public Collection<ProxyInfo> extract() {

        ConcurrentLinkedQueue<ProxyInfo> crawlerIPs = new ConcurrentLinkedQueue<>();
        Arrays.stream(urls).forEach(s-> {
                    Document document = null;

                        document = document(s);
                    if (document != null) {
                        document.select("#list table tr").get(0).remove();
                        Elements elements =
                                document.select("#list table tr");
                        for (Element element : elements) {
                            Element constructIpElement = element.select(".ip").get(0);
                            Elements portElement = constructIpElement.select(".port");
                            String portStr = portElement.attr("class").split("port")[1].trim();
                            constructIpElement.select("p[style=display: none;]").remove();
                            constructIpElement.select("p[style=display:none;]").remove();
                            System.out.println(constructIpElement.text());
                            String ipAndPort = accumStr((Node) constructIpElement);
                            String ip = ipAndPort.split(":")[0];
                            int port =parsePort( portStr);
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
                .header("User-Agent", "Mozilla/5.0")
                .header("Cookie","JSESSIONID=912706E05AE079369F0DFAD434AAE6E7")
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

    public String accumStr(Node nodes){
        StringBuffer accum = new StringBuffer();
        for(int i= 0 ;i<nodes.childNodeSize();i++){
            Node node =  nodes.childNode(i);
            if(node instanceof TextNode){
                 accum.append(((TextNode) node).getWholeText().trim());
            }else {
                accum.append(accumStr(node)) ;
            }
        }
        return accum.toString();
    }

    private static int parsePort(String inputPort){
        String target = "ABCDEFGHIZ";
        char[] portChar = inputPort.toCharArray();
        StringBuffer accum = new StringBuffer();
        for(char c: portChar){
            int index = target.indexOf(c);
            accum.append(index);
        }
        return  Integer.parseInt(accum.toString())/8;

    }

}
