/**
 *    Copyright 2015-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.wenchao.ipproxypool.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.wenchao.ipproxypool.domain.ProxyInfo;

import java.util.List;
import java.util.Set;

/**
 * @author Eduardo Macarron
 */
@Mapper
public interface ProxyInfoMapper {

	List<ProxyInfo> getAllEffective();
	List<ProxyInfo> getAll();

	void insert(@Param("ipProxyInfo") ProxyInfo proxyInfo);

	void delete(@Param("list") List<Integer> list);

	void deleteById(@Param("id")  Integer id);

    void insertAll(@Param("collection")Set<ProxyInfo> proxyInfoSet);
}
