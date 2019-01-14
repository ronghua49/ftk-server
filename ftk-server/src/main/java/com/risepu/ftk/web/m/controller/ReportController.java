
package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DocumentNumber;
import com.risepu.ftk.web.m.dto.DocumentRequest;
import com.risepu.ftk.web.m.util.ExcelExportUtil;
import net.lc4ever.framework.format.DateFormatter;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 证明统计汇总表
     *
     * @return 模板JavaBean
     */
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

    /**
     * 企业单据统计明细表
     *
     * @return 模板JavaBean
     */
    @Override
    public ResponseEntity<Response<PageResult>> getDocument(Integer pageNo, Integer pageSize, String organization, String createTime, String number, String templateType, String channelName) throws UnsupportedEncodingException, ParseException {
        Integer firstIndex = pageNo * pageSize;
        List<DocumentRequest> list = new ArrayList<>();
        List<DocumentRequest> list1 = new ArrayList<>();
        String hql = "select new com.risepu.ftk.web.m.dto.DocumentRequest (a.name as organizationName,a.id as organizationCode,a.code as type,c.name as documentType,b.createTimestamp as time,b.number as number,b.personalUser as idCard,b.chainHash as chainHash,e.channelName as channelName) from Organization a,ProofDocument b,Template c,OrganizationUser d,Channel e where a.id=b.organization and c.id=b.template and d.organizationId =a.id and d.inviteCode = e.inviteCode and b.number is not null";
        if (StringUtils.isNotEmpty(organization)) {
            organization = organization.trim();
            organization = new String(organization.getBytes("ISO8859-1"), "utf-8");
            hql += " and a.name like '%" + organization + "%'";
        }
        if (StringUtils.isNotEmpty(channelName)) {
            channelName = channelName.trim();
            channelName = new String(channelName.getBytes("ISO8859-1"), "utf-8");
            hql += " and e.channelName like '%" + channelName + "%'";
        }

        if (StringUtils.isNotEmpty(number)) {
            number = number.trim();
            hql += " and b.number like '%" + number + "%'";
        }
        if (StringUtils.isNotEmpty(templateType)) {
            templateType = templateType.trim();
            hql += " and c.code = '" + templateType + "'";
        }
        if (StringUtils.isNotEmpty(createTime)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(createTime);
            Date date1 = DateFormatter.startOfDay(DateFormatter.nextDay(date));
            hql += " and b.createTimestamp >= ?1 and b.createTimestamp < ?2";
            list = crudService.hql(DocumentRequest.class, firstIndex, pageSize, hql + " order by time desc", date, date1);
            list1 = crudService.hql(DocumentRequest.class, hql, date, date1);
        } else {
            list = crudService.hql(DocumentRequest.class, firstIndex, pageSize, hql + " order by time desc");
            list1 = crudService.hql(DocumentRequest.class, hql);
        }
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
    public ResponseEntity<Response<PageResult<OrganizationStream>>> queryRegOrganization(String orgName, String legalPerson, String industry, Integer pageNo, Integer pageSize, String startTime, String endTime, Integer state) throws UnsupportedEncodingException {
        Map<String, Object> map = new HashMap<>();
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

    @Override
    public void exportDocument(HttpServletResponse response) {

        List<List<String>> data = new ArrayList<List<String>>();
        String hql = "select new com.risepu.ftk.web.m.dto.DocumentRequest (a.name as organizationName,a.id as organizationCode,a.code as type,c.name as documentType,b.createTimestamp as time,b.number as number,b.personalUser as idCard,b.chainHash as chainHash,e.channelName as channelName) from Organization a,ProofDocument b,Template c,OrganizationUser d,Channel e where a.id=b.organization and c.id=b.template and d.organizationId =a.id and d.inviteCode = e.inviteCode order by b.createTimestamp desc";
        List<DocumentRequest> docList = crudService.hql(DocumentRequest.class, hql);
        for (int i = 0; i < docList.size(); i++) {
            List<String> br = new ArrayList<>();
            DocumentRequest doc = docList.get(i);
            br.add(doc.getOrganizationName());
            br.add(doc.getOrganizationCode());
            br.add(doc.getType());
            br.add(doc.getDocumentType());
            br.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(doc.getTime()));
            br.add(doc.getNumber());
            br.add(doc.getIdCard());
            br.add(doc.getChannelName());
            br.add(doc.getChainHash());
            data.add(br);
        }
        String[] tableName = {"企业名称", "社会信用代码", "行业类别", "单据类型", "生成日期", "单据编码", "身份证号码", "渠道名称", "区块链存证编码"};
        ExcelExportUtil.download(response, "企业单据统计明细表", "企业单据统计明细表", tableName, data);
    }
}
