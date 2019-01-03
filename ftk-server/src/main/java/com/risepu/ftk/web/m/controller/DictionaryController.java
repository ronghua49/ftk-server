package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.server.service.DictionaryService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * @author ronghaohua
 */
public class DictionaryController implements DictionaryApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private DictionaryService dictionaryService;


    @Override
    public ResponseEntity<Response<PageResult<Dictionary>>> findAll(String key, Integer pageNo, Integer pageSize) {

        PageResult<Dictionary> pageResult =  dictionaryService.queryIndustryClassByParam(key,pageNo,pageSize);


        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<String>> addClass(Dictionary dictionary) {

        Dictionary dictionary2 = dictionaryService.findIndustryClassByCode(dictionary.getDictCode());
        if(dictionary2!=null){
            return  ResponseEntity.ok(Response.failed(400,"此行业编号已经存在不得重复！"));
        }
        dictionaryService.addIndustryClass(dictionary);
        return  ResponseEntity.ok(Response.succeed("添加成功"));
    }

    @Override
    public ResponseEntity<Response<String>> editClass(Dictionary dictionary) {
        Dictionary dictionary2 = dictionaryService.findIndustryClassByCode(dictionary.getDictCode());

        /* 修改时行业分类编号不得和其他重复 **/
        if(dictionary2!=null&&!dictionary2.getId().equals(dictionary.getId())){
            return  ResponseEntity.ok(Response.failed(400,"此行业分类编号已经存在不得重复！"));
        }
        dictionaryService.updataIndustryClass(dictionary);
        return  ResponseEntity.ok(Response.succeed("修改成功"));
    }

    @Override
    public ResponseEntity<Response<List<DictionaryData>>> detail(String key, Integer dictId) {

       List<DictionaryData>  dataList = dictionaryService.queryIndustryDetailByParam(key,dictId);
        return  ResponseEntity.ok(Response.succeed(dataList));
    }

    @Override
    public ResponseEntity<Response<String>> addIndustry(DictionaryData dictionaryData) {
        dictionaryService.addIndustry(dictionaryData);
        return ResponseEntity.ok(Response.succeed("添加成功"));
    }

    @Override
    public ResponseEntity<Response<String>> editIndustry(DictionaryData dictionaryData) {
        DictionaryData data = dictionaryService.findIndustryByCode(dictionaryData.getCode());
        /** 修改行业编号不得重复*/
        if(data!=null&&!data.getId().equals(dictionaryData.getId())){
            return  ResponseEntity.ok(Response.failed(400,"此行业编号已经存在不得重复！"));
        }
        dictionaryService.editIndustry(dictionaryData);
        return ResponseEntity.ok(Response.succeed("修改成功"));
    }

    @Override
    public ResponseEntity<Response<String>> delIndustry(Integer id) {

        dictionaryService.delIndustryById(id.longValue());
        return ResponseEntity.ok(Response.succeed("删除成功"));
    }

}
