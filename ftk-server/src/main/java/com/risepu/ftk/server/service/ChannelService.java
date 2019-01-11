package com.risepu.ftk.server.service;    /*
 * @author  ronghaohua
 * @date 2019/1/11
 */

import com.risepu.ftk.server.domain.Channel;
import com.risepu.ftk.utils.PageResult;

import java.io.UnsupportedEncodingException;
import java.util.List;


public interface ChannelService {

    /**
     * 根据参数查询渠道列表
     * @param channelName
     * @param contactPerson
     * @param createTimestamp
     * @param pageNo
     * @param pageSize
     * @return
     */
    PageResult<Channel> queryChannel(String channelName, String contactPerson, String createTimestamp, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException;

    /**
     * 获得所有的渠道
     * @return
     */
    List<Channel> getAll();

    /**
     * 添加渠道
     * @param channel
     */
    void save(Channel channel);

    /**
     * 修改
     * @param channel
     */
    void edit(Channel channel);

    /**
     * 查询渠道
     * @param id
     * @return
     */
    Channel queryChannelById(Long id);

    /**
     * 根据邀请码查询
     * @param inviteCode
     * @return
     */
    Channel queryChannelByInviteCode(String inviteCode);
}
