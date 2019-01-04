package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.OrganizationStream;
import com.risepu.ftk.server.domain.RegisterUserReport;
import com.risepu.ftk.server.service.OrganizationService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ronghaohua
 */
@RestController
public class ReportController implements ReportControllerApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private OrganizationService organizationService;





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
        map.put("legalPerson",legalPerson);
        map.put("industry",industry);
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
