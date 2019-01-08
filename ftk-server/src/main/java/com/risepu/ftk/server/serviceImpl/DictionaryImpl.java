package com.risepu.ftk.server.serviceImpl;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.server.service.DictionaryService;
import com.risepu.ftk.utils.PageResult;
import net.lc4ever.framework.service.GenericCrudService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ronghaohua
 */
@Service
public class DictionaryImpl implements DictionaryService {


    @Autowired
    private GenericCrudService crudService;

    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    /**
     * 根据参数查询行业分类列表
     *
     * @param map
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<Dictionary> queryIndustryClassByParam(Map<String,Object> map, Integer pageNo, Integer pageSize) {

        String dictCode = (String) map.get("dictCode");
        String name = (String) map.get("name");

        int firstIndex = pageNo*pageSize;
        String hql="from Dictionary where 1=1 ";
        String prefix = "select count(*) ";
        if(StringUtils.isNotEmpty(dictCode)){
            hql+="and dictCode = '"+dictCode+"'";
        }
        if(StringUtils.isNotEmpty(name)){
            hql+=" and name like '%"+name+"%'";
        }

        int total = crudService.uniqueResultHql(Long.class, prefix+hql).intValue();
        List<Dictionary> dictionaryList = crudService.hql(Dictionary.class, firstIndex, pageSize, hql);
        PageResult<Dictionary> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(dictionaryList);
        return pageResult;
    }

    /**
     * 添加行业分类
     *
     * @param dictionary
     */
    @Override
    public void addIndustryClass(Dictionary dictionary) {
        crudService.save(dictionary);
    }

    /**
     * 修改行业分类
     *
     * @param dictionary
     */
    @Override
    public void updataIndustryClass(Dictionary dictionary) {
        crudService.update(dictionary);
    }

    /**
     * 根据行业编码查询行业分类
     *
     * @param dictCode
     * @return
     */
    @Override
    public Dictionary findIndustryClassByCode(String dictCode) {
        return crudService.uniqueResultHql(Dictionary.class,"from Dictionary where dictCode =?1",dictCode);
    }

    /**
     * 根据id查询行业分类
     *
     * @param id
     * @return
     */
    @Override
    public Dictionary findIndustryClassById(Long id) {
        return crudService.get(Dictionary.class,id);
    }

    /**
     * 根据父类id和参数查询行业详情分类
     *
     * @param key
     * @param dictId 行业分类id
     * @return
     */
    @Override
    public List<DictionaryData> queryIndustryDetailByParam(String key, Integer dictId) {
        return crudService.hql(DictionaryData.class,"from DictionaryData where dictId =?1 and dictdataName like ?2",dictId.longValue(),"%"+key+"%");
    }

    /**
     * 根据父id查询行业详情
     *
     * @param dictId 行业分类字典id
     * @return
     */
    @Override
    public List<DictionaryData> queryAllData(Integer dictId) {
        return crudService.hql(DictionaryData.class,"from DictionaryData where dictId =?1",dictId);
    }

    /**
     * 增加行业
     *
     * @param dictionaryData 行业数据
     */
    @Override
    public void addIndustry(DictionaryData dictionaryData) {
        crudService.save(dictionaryData);
    }

    /**
     * 根据行业编号查询行业
     *
     * @param code 行业编号
     * @return
     */
    @Override
    public DictionaryData findIndustryByCode(String code) {
        return crudService.uniqueResultHql(DictionaryData.class,"from DictionaryData where code =?1",code);
    }

    /**
     * 修改行业
     *
     * @param dictionaryData
     */
    @Override
    public void editIndustry(DictionaryData dictionaryData) {
        crudService.update(dictionaryData);
    }

    /**
     * 根据id 修改行业为删除状态
     *
     * @param l
     */
    @Override
    public void delIndustryById(long l) {
        DictionaryData data = crudService.get(DictionaryData.class, l);

        crudService.delete(data);
    }

    /**
     * 查询所有行业分类
     *
     * @return
     */
    @Override
    public List<Dictionary> findAllIndustry() {
        return crudService.hql(Dictionary.class,"from Dictionary");
    }

    /**
     * 查询所有二行业分类
     *
     * @return
     */
    @Override
    public List<DictionaryData> queryAllDataList() {
        return crudService.hql(DictionaryData.class,"from DictionaryData");
    }

    /**
     * 根据父行业分类code 查询子类集合
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<DictionaryData> findIndustrysByDictCode(String dictCode) {
        return crudService.hql(DictionaryData.class,"from DictionaryData where dictId in (select id from Dictionary where dictCode = ?1) ",dictCode);
    }

    /**
     * 删除一级菜单
     *
     * @param dictCode
     */
    @Override
    public void delClass(String dictCode) {
        Dictionary dictionary = crudService.uniqueResultHql(Dictionary.class, "from Dictionary where dictCode =?1", dictCode);
        crudService.delete(dictionary);
    }

    /**
     * 根据id查询 data
     *
     * @param id
     * @return
     */
    @Override
    public DictionaryData findIndustryById(Integer id) {
        return crudService.get(DictionaryData.class,id.longValue());
    }

    /**
     * 根据参数参数查询二级行业
     *
     * @param pageNo
     * @param pageSize
     * @param code
     * @param name
     * @return
     */
    @Override
    public PageResult<DictionaryData> findIndustryDataPageByParam(Integer pageNo, Integer pageSize, String code, String name,Long dictId) {
        Integer firstIndex = pageNo * pageSize;
        String sql = "from DictionaryData where dictId = " + dictId;
        if (StringUtils.isNotEmpty(code)) {
            sql += " and code = " + code;
        }
        if (StringUtils.isNotEmpty(name)) {
            sql += " and dictdataName like '%" + name + "%'";
        }

        List<DictionaryData> list = crudService.hql(DictionaryData.class, firstIndex, pageSize, sql);
        List<DictionaryData> dataList = crudService.hql(DictionaryData.class, sql);
        PageResult<DictionaryData> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(dataList.size(), pageSize);
        pageResult.setTotalElements(dataList.size());
        pageResult.setContent(list);
        return pageResult;
    }
}
