package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/11
 */

import com.risepu.ftk.server.domain.Channel;
import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.service.ChannelService;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ChannelController implements  ChannelApi{


    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private ChannelService channelService;


    @Override
    public ResponseEntity<Response<PageResult<Channel>>> queryAllAdvice(String channelName, String contactPerson, Integer pageNo, Integer pageSize, String createTimestamp) throws UnsupportedEncodingException {
        PageResult<Channel> pageResult = channelService.queryChannel(channelName,contactPerson,createTimestamp,pageNo,pageSize);

        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<String>> addChannel(Channel channel) {
       List<Channel> channelList = channelService.getAll();
        Set<String> inviteCode = new HashSet<>();
        for(Channel channel1:channelList){
            inviteCode.add(channel1.getInviteCode());
        }
        String code = getInviteCode(inviteCode);
        channel.setInviteCode(code);

        if(channelService.queryChannelByChannelCode(channel.getChannelCode()) !=null){
            return ResponseEntity.ok(Response.failed(400,"渠道编号不得重复"));
        }

        if(channelService.queryChannelByName(channel.getChannelName())!=null){
            return ResponseEntity.ok(Response.failed(400,"渠道名称不得重复"));
        }
        channelService.save(channel);

        return ResponseEntity.ok(Response.succeed("添加渠道成功"));
    }

    @Override
    public ResponseEntity<Response<String>> editChannel(Channel channel) {


        Channel channel1 = channelService.queryChannelByChannelCode(channel.getChannelCode());
        if( channel1!=null&&!channel.getId().equals(channel1.getId())){
            return ResponseEntity.ok(Response.failed(400,"渠道编号不得重复"));
        }

        Channel channel2 = channelService.queryChannelByName(channel.getChannelName());
        if(channel2!=null&&!channel2.getId().equals(channel.getId())){
            return ResponseEntity.ok(Response.failed(400,"渠道名称不得重复"));
        }

        Channel currChannel = channelService.queryChannelById(channel.getId());
        String channelName = currChannel.getChannelName();
        List<OrganizationStream> streams = organizationService.findAuthStreamByChannelName(channelName);
        for(OrganizationStream stream:streams){
            stream.setChannalName(channel.getChannelName());
            organizationService.updateOrgStream(stream);
        }
        currChannel.setChannelCode(channel.getChannelCode());
        currChannel.setChannelName(channel.getChannelName());
        currChannel.setContactPerson(channel.getContactPerson());
        currChannel.setRemark(channel.getRemark());
        currChannel.setTel(channel.getTel());

        channelService.edit(currChannel);
        return ResponseEntity.ok(Response.succeed("修改渠道成功"));
    }

    @Override
    public ResponseEntity<Response<Channel>> queryOne(Long id) {
        Channel channel = channelService.queryChannelById(id);
        return ResponseEntity.ok(Response.succeed(channel));
    }

    private String getInviteCode(Set<String> codeSet) {

        int s = (int) (Math.random() * 9000) + 1000;
        String code = String.valueOf(s);
        String s1 = String.valueOf(code.charAt(0));
        String s2 = String.valueOf(code.charAt(1));
        String s3 = String.valueOf(code.charAt(2));
        String s4 = String.valueOf(code.charAt(3));

        while(codeSet.contains(code)||s1.equals(s2)||s2.equals(s3)||s3.equals(s4)||(s1.equals("0")&&s2.equals("0"))){
            s=(int) (Math.random() * 9000) + 1000;
        }
      return  String.valueOf(s);

    }


}
