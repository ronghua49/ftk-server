package com.risepu.ftk.web.m.controller;

import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.DocumentNumber;
import net.lc4ever.framework.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author L-heng
 */
@Controller
@RequestMapping("/api/report")
public class ReportController implements ReportApi {
    @Autowired
    private GenericCrudService crudService;

    @Override
    public ResponseEntity<Response<List>> getCount(Integer year) {
        List list = crudService.hql(DocumentNumber.class, "SELECT COUNT(1) AS number,MONTH(createTimestamp) as time FROM ProofDocument where number is not null and year(createTimestamp) = ?1 GROUP BY MONTH(createTimestamp) ORDER BY MONTH(createTimestamp)", year);
        Long total = (Long) crudService.uniqueResultHql("SELECT COUNT(1) FROM ProofDocument where number is not null and year(createTimestamp) = ?1", year);
        list.add(total);
        return ResponseEntity.ok(Response.succeed(list));
    }
}
