
package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DocumentNumber;
import com.risepu.ftk.web.m.dto.DocumentRequest;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/report")
public class ReportController implements ReportApi {


    @Autowired
    private GenericCrudService crudService;

    @Autowired
    private OrganizationService organizationService;

    @Override
    public ResponseEntity<Response<List>> getCount(Integer startYear, Integer endYear) {
        List list = new ArrayList();
        if (startYear == null && endYear == null) {
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            list = crudService.hql(DocumentNumber.class, "SELECT COUNT(1) AS number,year(createTimestamp) as year,MONTH(createTimestamp) as month FROM ProofDocument where number is not null and year(createTimestamp) = ?1 GROUP BY MONTH(createTimestamp) ORDER BY MONTH(createTimestamp)", year);
            Long total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) = ?1", year);
            list.add(total);
        }
        if (startYear == null && endYear != null) {
            list = crudService.hql(DocumentNumber.class, "SELECT COUNT(1) AS number,year(createTimestamp) as year,MONTH(createTimestamp) as month FROM ProofDocument where number is not null and year(createTimestamp) <= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", endYear);
            Long total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) <= ?1", endYear);
            list.add(total);
        }
        if (startYear != null && endYear == null) {
            list = crudService.hql(DocumentNumber.class, "SELECT COUNT(1) AS number,year(createTimestamp) as year,MONTH(createTimestamp) as month FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear);
            Long total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1", startYear);
            list.add(total);
        }
        if (startYear != null && endYear != null) {
            list = crudService.hql(DocumentNumber.class, "SELECT COUNT(1) AS number,year(createTimestamp) as year,MONTH(createTimestamp) as month FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 and year(createTimestamp) <= ?2 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear, endYear);
            Long total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 and year(createTimestamp) <= ?2", startYear, endYear);
            list.add(total);
        }
        return ResponseEntity.ok(Response.succeed(list));
    }

    @Override
    public ResponseEntity<Response<PageResult>> getDocument(Integer pageNo, Integer pageSize, String organization, String createTime, String number, String type) {
        Integer firstIndex = pageNo * pageSize;
        String hql1 = "select a.name,a.id,b.createTimestamp,b.chainHash,b.personalUser,b.number from Organization a,ProofDocument b where a.id=b.organization";
        String hql2 = "select c.name from Template c,ProofDocument d where c.id=d.template";
        if (StringUtils.isNotEmpty(organization)) {
            hql1 += " and a.name like '%" + organization + "%'";
        }
        if (StringUtils.isNotEmpty(createTime)) {

        }
        if (StringUtils.isNotEmpty(number)) {
            hql1 += " and b.number like '%" + number + "%'";
        }
        if (StringUtils.isNotEmpty(type)) {
            hql1 += " and a.code = '" + type + "'";
        }
//        String hql = "(" + hql1 + ") e" + " left join " + "(" + hql2 + ") f on e.b.id=f.d.id";
        String hql = "select a.name,a.id,b.createTimestamp,b.chainHash,b.personalUser,b.number.c.name from Organization a,ProofDocument b,Template c where a.id=b.organization and c.id=b.template";
        List list = crudService.hql(firstIndex, pageSize, hql);
        List<DocumentRequest> list1 = crudService.hql(DocumentRequest.class, hql);
        PageResult<DocumentRequest> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(list1.size(), pageSize);
        pageResult.setTotalElements(list1.size());
        pageResult.setContent(list);
        return ResponseEntity.ok(Response.succeed(pageResult));
    }


    @Override
    public ResponseEntity<Response<PageResult<OrganizationStream>>> queryRegOrganization(String orgName, String legalPerson, String industry, Integer pageNo, Integer pageSize, String startTime, String endTime, Integer state) {
        Map<String, Object> map = new HashMap<>();

        try {
            orgName = new String(orgName.getBytes("ISO-8859-1"), "utf-8");
            legalPerson = new String(legalPerson.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        map.put("orgName", orgName);
        map.put("legalPerson", legalPerson);
        map.put("industry", industry);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("state", state);
        PageResult<OrganizationStream> pageResult = organizationService.findOrgRegStreamByMap(map, pageNo, pageSize);

        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<PageResult<RegisterUserReport>>> queryRegUser(String userType, Integer pageNo, Integer pageSize, String startTime, String endTime) {
        Map<String, Object> map = new HashMap<>();
        map.put("userType", userType);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        PageResult<RegisterUserReport> pageResult = organizationService.findRegUserByMap(map, pageNo, pageSize);

        return ResponseEntity.ok(Response.succeed(pageResult));
    }
}
