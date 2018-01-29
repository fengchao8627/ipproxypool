package com.wenchao.ipproxypool.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wenchao.ipproxypool.domain.ProxyInfo;
import com.wenchao.ipproxypool.mapper.ProxyInfoMapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

@Repository
public class IpProxyRepositoryImpl implements IpProxyRepository{

    @Autowired
    ProxyInfoMapper proxyInfoMapper ;

    protected LinkedBlockingQueue<ProxyInfo> collections = new LinkedBlockingQueue<>();

    @Override
    public void insert(ProxyInfo proxyInfo) {
        proxyInfoMapper.insert(proxyInfo);
    }

    public void insertIntoMemory(ProxyInfo proxyInfo){
        collections.add(proxyInfo);
    }

    @Override
    public List<ProxyInfo> getAllEffective() {
        return proxyInfoMapper.getAllEffective();
    }

    @Override
    public List<ProxyInfo> getAll() {
        return proxyInfoMapper.getAll();
    }
    @Override
    public Collection<ProxyInfo> getAllFromMemory() {
        return collections;
    }

    @Override
    public void delete(ProxyInfo proxyInfo) {
        proxyInfoMapper.deleteById(proxyInfo.getId());
    }
    @Override
    public void deleteFromMemory(ProxyInfo proxyInfo) {
        collections.remove(proxyInfo);
    }

    @Override
    public void delete(Collection<ProxyInfo> proxyInfos) {
               if(proxyInfos.size()<=0){return;}
                proxyInfoMapper.delete(proxyInfos.stream().map(s->s.getId()).collect(Collectors.toList()));

    }
    @Override
    public void batchDeleteFromMemory(Collection<ProxyInfo> proxyInfos) {
      proxyInfos.stream().forEach(s->collections.remove(s));

    }

    @Override
    public void save(Collection<ProxyInfo> proxyInfos) {
        List<ProxyInfo> proxyInfoList = getAll();
        Set<ProxyInfo> proxyInfoSet= proxyInfos.stream().filter(s->!proxyInfoList.contains(s)).collect(Collectors.toSet());
        proxyInfoMapper.insertAll(proxyInfoSet);
    }
    @Override
    public void saveIntoMemory(Collection<ProxyInfo> proxyInfos) {
        Collection<ProxyInfo> proxyInfoList = getAllFromMemory();
        Set<ProxyInfo> proxyInfoSet= proxyInfos.stream().filter(s->!proxyInfoList.contains(s)).collect(Collectors.toSet());
        collections.addAll(proxyInfoSet);
    }
}
