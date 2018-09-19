package com.nuls.io.dao;

import java.util.List;

import com.nuls.io.model.entity.MonitorClientMapping;

public interface BaseConfigDao {

    /**
     * 我监控端监控了多少客户端
     * @param uId
     * @param roleNameInnerClient
     * @return
     */
    List<MonitorClientMapping> getClientByuId(Long uId, String roleNameInnerClient);

    /**
     *我客户端被多少监控端监控
     * @param uId
     * @param roleNameMonitorDclient
     * @return
     */
    List<MonitorClientMapping> getMonitorsByuId(Long uId, String roleNameMonitorDclient);

    /**
     * 获取基本配置baseconfig  公共配置commonconfig
     * @return
     */
    List<Object[]> getCommonConfig();

    /**
     * 获取MonitorClientMapping
     *
     * @param clientUid
     * @param monitorUid
     * @return
     */

    MonitorClientMapping getMonitorClientMappingByClientUidAndMonitorUid(Long clientUid,
                                                                         Long monitorUid);

    /**
     * 获取所有的监控关系
     *
     * @return
     */
    List<MonitorClientMapping> getMonitorRelationList();
}
