
package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DocumentNumber;
import com.risepu.ftk.web.m.dto.DocumentRequest;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    public ResponseEntity<Response<PageResult>> getCount(Integer pageNo, Integer pageSize, Integer startYear, Integer endYear) {
        Integer firstIndex = pageNo * pageSize;
        if (startYear != null && endYear != null && startYear > endYear) {
            return ResponseEntity.ok(Response.failed(400, "开始年度不能大于结束年度！"));
        }
        List<DocumentNumber> list = new ArrayList();
        List<DocumentNumber> list1 = new ArrayList();
        Long total = Long.parseLong("0");
        if (startYear == null && endYear == null) {
            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            list = crudService.hql(DocumentNumber.class, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) = ?1 GROUP BY MONTH(createTimestamp) ORDER BY MONTH(createTimestamp)", year);
            list1 = crudService.hql(DocumentNumber.class, firstIndex, pageSize, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) = ?1 GROUP BY MONTH(createTimestamp) ORDER BY MONTH(createTimestamp)", year);
            total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) = ?1", year);
        }
        if (startYear == null && endYear != null) {
            list = crudService.hql(DocumentNumber.class, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) <= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", endYear);
            list1 = crudService.hql(DocumentNumber.class, firstIndex, pageSize, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) <= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", endYear);
            total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) <= ?1", endYear);
        }
        if (startYear != null && endYear == null) {
            list = crudService.hql(DocumentNumber.class, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear);
            list1 = crudService.hql(DocumentNumber.class, firstIndex, pageSize, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear);
            total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1", startYear);
        }
        if (startYear != null && endYear != null) {
            list = crudService.hql(DocumentNumber.class, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 and year(createTimestamp) <= ?2 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear, endYear);
            list1 = crudService.hql(DocumentNumber.class, firstIndex, pageSize, "SELECT new com.risepu.ftk.web.m.dto.DocumentNumber (year(createTimestamp) as year,MONTH(createTimestamp) as month,COUNT(1) AS number) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 and year(createTimestamp) <= ?2 GROUP BY MONTH(createTimestamp) ORDER BY year(createTimestamp),MONTH(createTimestamp)", startYear, endYear);
            total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) >= ?1 and year(createTimestamp) <= ?2", startYear, endYear);
        }
        PageResult<DocumentNumber> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(list.size(), pageSize);
        pageResult.setTotalElements(list.size());
        pageResult.setContent(list1);
        pageResult.setTotal(total);
        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<PageResult>> getDocument(Integer pageNo, Integer pageSize, String organization, String createTime, String number, String templateType) throws UnsupportedEncodingException, ParseException {
        Integer firstIndex = pageNo * pageSize;
        String hql = "select new com.risepu.ftk.web.m.dto.DocumentRequest (a.name as organizationName,a.id as organizationCode,a.code as type,c.name as documentType,b.createTimestamp as time,b.number as number,b.personalUser as idCard,b.chainHash as chainHash) from Organization a,ProofDocument b,Template c where a.id=b.organization and c.id=b.template";
        if (StringUtils.isNotEmpty(organization)) {
            organization = new String(organization.getBytes("ISO8859-1"), "utf-8");
            hql += " and a.name like '%" + organization + "%'";
        }
        if (StringUtils.isNotEmpty(createTime)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(createTime);
            date = DateFormatter.startOfDay(DateFormatter.nextDay(date));
            String endDate = formatter.format(date);
            hql += " and b.createTimestamp >= '" + createTime + "' and b.createTimestamp < '" + endDate + "'";
        }
        if (StringUtils.isNotEmpty(number)) {
            hql += " and b.number like '%" + number + "%'";
        }
        if (StringUtils.isNotEmpty(templateType)) {
            hql += " and c.code = '" + templateType + "'";
        }
        List<DocumentRequest> list = crudService.hql(DocumentRequest.class, firstIndex, pageSize, hql);
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
