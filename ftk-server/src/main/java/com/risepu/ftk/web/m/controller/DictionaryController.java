package com.risepu.ftk.web.m.controller;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.server.service.DictionaryService;
import com.risepu.ftk.utils.PageResult;
import com.risepu.ftk.web.api.Response;
import com.risepu.ftk.web.m.dto.CodeAndName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ronghaohua
 */
@RestController
public class DictionaryController implements DictionaryApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private DictionaryService dictionaryService;


    @Override
    public ResponseEntity<Response<PageResult<Dictionary>>> findAllPage(String dictCode,String name, Integer pageNo, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("dictCode",dictCode);
        map.put("name",name);

        PageResult<Dictionary> pageResult =  dictionaryService.queryIndustryClassByParam(map,pageNo,pageSize);

        return ResponseEntity.ok(Response.succeed(pageResult));
    }

    @Override
    public ResponseEntity<Response<List<CodeAndName>>> findAll() {
        List<Dictionary> dictionaryList = dictionaryService.findAllIndustry();
        List<CodeAndName> codeAndNames = new ArrayList<>();
        for(Dictionary dict: dictionaryList){
            CodeAndName codeAndName = new CodeAndName();
            codeAndName.setCode(dict.getDictCode());
            codeAndName.setName(dict.getName());
            codeAndNames.add(codeAndName);
        }

        return ResponseEntity.ok(Response.succeed(codeAndNames));
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
    public ResponseEntity<Response<Dictionary>> queryClass(String dictCode) {
        Dictionary dictionary = dictionaryService.findIndustryClassByCode(dictCode);

        return ResponseEntity.ok(Response.succeed(dictionary));
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
    public ResponseEntity<Response<String>> delClass(String dictCode) {
        List<DictionaryData> dataList = dictionaryService.findIndustrysByDictCode(dictCode);
        if(dataList!=null&&!dataList.isEmpty()){
            return  ResponseEntity.ok(Response.failed(400,"该行业下存在二级菜单，不得删除！"));
        }
        dictionaryService.delClass(dictCode);
        return ResponseEntity.ok(Response.succeed("success"));
    }

    @Override
    public ResponseEntity<Response<List<CodeAndName>>> allSecondclass() {
        List<DictionaryData>  dataList = dictionaryService.queryAllDataList();
        List<CodeAndName> codeAndNames = new ArrayList<>();
        for(DictionaryData data: dataList){
            CodeAndName codeAndName = new CodeAndName();
            codeAndName.setCode(data.getCode());
            codeAndName.setName(data.getDictdataName());
            codeAndNames.add(codeAndName);
        }
        return ResponseEntity.ok(Response.succeed(codeAndNames));
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
