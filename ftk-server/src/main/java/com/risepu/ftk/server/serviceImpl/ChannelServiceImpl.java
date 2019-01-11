package com.risepu.ftk.server.serviceImpl;    /*
 * @author  Administrator
 * @date 2019/1/11
 */

import com.risepu.ftk.server.domain.Channel;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.service.ChannelService;
import com.risepu.ftk.utils.PageResult;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ChannelServiceImpl implements ChannelService {

    private GenericCrudService crudService;

    @Autowired
    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    /**
     * 根据参数查询渠道列表
     *
     * @param channelName
     * @param contactPerson
     * @param createTimestamp
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<Channel> queryChannel(String channelName, String contactPerson, String createTimestamp, Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
        Integer firstIndex = (pageNo) * pageSize;

        String hql = "from Channel where 1=1 ";
        String hql1 = " order by createTimestamp desc";
        String hql2 = "select count(*) ";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String nextDate = null;
        if (StringUtils.isNotEmpty(createTimestamp)) {
            try {
                Date next = DateFormatter.startOfDay(DateFormatter.nextDay(format.parse(createTimestamp)));
                nextDate = format.format(next);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotEmpty(channelName)) {
            channelName = new String(channelName.getBytes("ISO8859-1"), "utf-8");
            hql += "and channelName like '%" + channelName + "%'";
        }

        if (StringUtils.isNotEmpty(contactPerson)) {
            contactPerson = new String(contactPerson.getBytes("ISO8859-1"), "utf-8");
            hql += " and contactPerson like '%" + contactPerson + "%'";
        }

        if (StringUtils.isNotEmpty(createTimestamp)) {
            hql += " and createTimestamp  between '" + createTimestamp + "' and '" + nextDate + "'";
        }
        List<Channel> channels = crudService.hql(Channel.class, firstIndex, pageSize, hql + hql1);
        int total = crudService.uniqueResultHql(Long.class, hql2 + hql).intValue();
        PageResult<Channel> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(channels);
        return pageResult;
    }

    /**
     * 获得所有的渠道
     *
     * @return
     */
    @Override
    public List<Channel> getAll() {
        return crudService.hql(Channel.class,"from Channel");
    }

    /**
     * 添加渠道
     *
     * @param channel
     */
    @Override
    public void save(Channel channel) {
        crudService.save(channel);
    }

    /**
     * 修改
     *
     * @param channel
     */
    @Override
    public void edit(Channel channel) {
        crudService.update(channel);
    }

    /**
     * 查询渠道
     *
     * @param id
     * @return
     */
    @Override
    public Channel queryChannelById(Long id) {
        return crudService.get(Channel.class,id);
    }

    /**
     * 根据邀请码查询
     *
     * @param inviteCode
     * @return
     */
    @Override
    public Channel queryChannelByInviteCode(String inviteCode) {
        return crudService.uniqueResultSql(Channel.class,"from Channel where inviteCode =?1 ",inviteCode);
    }
}
