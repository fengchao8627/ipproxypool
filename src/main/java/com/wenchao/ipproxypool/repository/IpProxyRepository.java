package com.wenchao.ipproxypool.repository;

import com.wenchao.ipproxypool.domain.ProxyInfo;

import java.util.Collection;
import java.util.List;

public interface IpProxyRepository {

    void insert(ProxyInfo proxyInfo);

    void save(Collection<ProxyInfo> proxyInfos);

    List<ProxyInfo> getAllEffective();

    List<ProxyInfo> getAll();

    Collection<ProxyInfo> getAllFromMemory();

    void delete(ProxyInfo proxyInfo);

    public void delete(Collection<ProxyInfo> proxyInfos);

    void deleteFromMemory(ProxyInfo proxyInfo);

    public void saveIntoMemory(Collection<ProxyInfo> proxyInfos);

    public void batchDeleteFromMemory(Collection<ProxyInfo> proxyInfos);


}
