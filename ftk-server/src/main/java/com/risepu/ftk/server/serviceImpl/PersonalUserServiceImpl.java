package com.risepu.ftk.server.serviceImpl;

import com.risepu.ftk.server.domain.AuthorizationStream;
import com.risepu.ftk.server.domain.Organization;
import com.risepu.ftk.server.domain.PersonalUser;
import com.risepu.ftk.server.service.PersonalUserService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.p.dto.AuthHistoryInfo;
import net.lc4ever.framework.service.GenericCrudService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * @author ronghaohua
 */
@Service
public class PersonalUserServiceImpl implements PersonalUserService {

    @Autowired
    private GenericCrudService crudService;



    @Override
    public void savePersonUser(PersonalUser user) {
         crudService.save(user);
    }

    @Override
    public void update(AuthorizationStream authStream) {
        crudService.update(authStream);
    }

    @Override
    public PageResult<AuthHistoryInfo> queryHistoryByParam(String key, Integer pageNo, Integer pageSize, String personId) {

        List<AuthHistoryInfo> historyList = new ArrayList<>();
        String sql = "select a.AUTH_STATE,a.MODIFY_TIMESTAMP,o.ADDRESS,o.`NAME`,o.TEL from FTK_AUTHORIZATION_STREAM a, FTK_ORGANIZATION o where a.ORG_ID=o.ORGANIZATION and a.AUTH_STATE in (1,2) and a.PERSON_ID = ? and o.`NAME` like ? ORDER BY a.MODIFY_TIMESTAMP DESC";
        String sql2 = "select count(*) from FTK_AUTHORIZATION_STREAM a, FTK_ORGANIZATION o where a.ORG_ID=o.ORGANIZATION and a.AUTH_STATE in (1,2) and a.PERSON_ID = ? and o.`NAME` like ?";
        BigInteger bigInteger = (BigInteger) crudService.uniqueResultSql(sql2, personId, "%" + key + "%");

        int total = bigInteger.intValue();
        List<?> objects = crudService.sql(pageNo * pageSize, pageSize, sql, personId, "%" + key + "%");
        for (int i = 0; i < objects.size(); i++) {
            AuthHistoryInfo info = new AuthHistoryInfo();
            Object[] object = (Object[]) objects.get(i);
            info.setState((Integer) object[0]);
            info.setModifyTimestamp((Date) object[1]);
            info.setAddress((String) object[2]);
            info.setName((String) object[3]);
            info.setTel((String) object[4]);
            historyList.add(info);
        }
        PageResult<AuthHistoryInfo> page = new PageResult<>();
        page.setTotalElements(total);
        page.setContent(historyList);

        return page;
    }

    @Override
    public Map<Long, String> findNewRequestByCardNo(String cardNo) {
        /** 只查询 10 分钟之内的 最近扫描单据的一个企业id */
        DateTime dateTime = new DateTime();
        DateTime minusTime = dateTime.minusMinutes(10);
        Date time = minusTime.toDate();
        List<AuthorizationStream> streams = crudService.hql(AuthorizationStream.class, "from AuthorizationStream where personId =?1 and authState=0 and  createTimestamp > ?2 order by createTimestamp desc", cardNo, time);
        if (streams != null && !streams.isEmpty()) {
            Map<Long, String> map = new HashMap<>();
            for(AuthorizationStream stream:streams){
                map.put(stream.getId(),crudService.uniqueResultByProperty(Organization.class, "id", stream.getOrgId()).getName());
            }
            return map;
        }
        return null;
    }

    @Override
    public AuthorizationStream findAuthorizationStreamById(long streamId) {
        return crudService.uniqueResultByProperty(AuthorizationStream.class, "id", streamId);
    }

    @Override
    public PersonalUser findUserByNo(String cardNo,String phone) {
        return crudService.uniqueResultHql(PersonalUser.class, "from PersonalUser where id.id =?1 and id.mobile =?2", cardNo,phone);
    }
}
