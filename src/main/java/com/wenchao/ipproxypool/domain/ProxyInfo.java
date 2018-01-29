package com.wenchao.ipproxypool.domain;

import java.net.Proxy;

public class ProxyInfo {

    int id ;

    String ip;

    int port;

    Proxy.Type proxyType;




    public ProxyInfo(String ip, int port, Proxy.Type proxyType, int responseSeconds) {
        this.ip = ip;
        this.port = port;
        this.proxyType = proxyType;
    }

    public ProxyInfo() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Proxy.Type getProxyType() {
        return proxyType;
    }

    public void setProxyType(Proxy.Type proxyType) {
        this.proxyType = proxyType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProxyInfo proxyInfo = (ProxyInfo) o;

        if (port != proxyInfo.port) return false;
        return ip != null ? ip.equals(proxyInfo.ip) : proxyInfo.ip == null;
    }

    @Override
    public int hashCode() {
        int result = ip != null ? ip.hashCode() : 0;
        result = 31 * result + port;
        return result;
    }

    @Override
    public String toString() {
        return "{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
