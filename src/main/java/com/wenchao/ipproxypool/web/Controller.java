package com.wenchao.ipproxypool.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wenchao.ipproxypool.domain.ProxyInfo;
import com.wenchao.ipproxypool.repository.IpProxyRepository;

import java.util.ArrayList;
import java.util.List;
//todo 分页展示 redis cache  logger standard
@RestController
public class Controller {
    @Autowired
    IpProxyRepository ipProxyRepository;

    @RequestMapping("/")
    public Object home(){
       return ipProxyRepository.getAllFromMemory();
    }

    @RequestMapping("/insert")
    public List<ProxyInfo> insert(){
        ProxyInfo proxyInfo = new ProxyInfo();
        proxyInfo.setIp("10.10.0.1");
        proxyInfo.setPort(8989);

        ipProxyRepository.insert(proxyInfo);
        return ipProxyRepository.getAllEffective();
    }

    @RequestMapping("/save")
    public List<ProxyInfo> save(){
        ProxyInfo proxyInfo = new ProxyInfo();
        proxyInfo.setIp("10.10.0.1");
        proxyInfo.setPort(8982);

        ProxyInfo proxyInfo2 = new ProxyInfo();
        proxyInfo2.setIp("10.10.0.1");
        proxyInfo2.setPort(8980);

        List<ProxyInfo> proxyInfos = new ArrayList<>();

        ipProxyRepository.save(proxyInfos);
        return ipProxyRepository.getAllEffective();
    }


}
