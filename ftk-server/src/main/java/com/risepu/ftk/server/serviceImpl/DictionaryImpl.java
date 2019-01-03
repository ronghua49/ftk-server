package com.risepu.ftk.server.serviceImpl;    /*
 * @author  Administrator
 * @date 2019/1/3
 */

import com.risepu.ftk.server.domain.Dictionary;
import com.risepu.ftk.server.domain.DictionaryData;
import com.risepu.ftk.server.service.DictionaryService;
import com.risepu.ftk.utils.PageResult;
import net.lc4ever.framework.service.GenericCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author ronghaohua
 */
public class DictionaryImpl implements DictionaryService {


    @Autowired
    private GenericCrudService crudService;

    public void setCrudService(GenericCrudService crudService) {
        this.crudService = crudService;
    }

    /**
     * 根据参数查询行业分类列表
     *
     * @param key
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public PageResult<Dictionary> queryIndustryClassByParam(String key, Integer pageNo, Integer pageSize) {

        int firstIndex = pageNo*pageSize;
        int total = crudService.uniqueResultHql(Long.class, "select count(*) from Dictionary where name like ?1","%" + key + "%").intValue();
        List<Dictionary> dictionaryList = crudService.hql(Dictionary.class, firstIndex, pageSize, "from Dictionary where name like ?1 order by dictCode acs", "%" + key + "%");
        PageResult<Dictionary> pageResult = new PageResult<>();
        pageResult.setResultCode("SUCCESS");
        pageResult.setNumber(pageNo);
        pageResult.setSize(pageSize);
        pageResult.setTotalPages(total, pageSize);
        pageResult.setTotalElements(total);
        pageResult.setContent(dictionaryList);
        return null;
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
        return crudService.uniqueResultSql(Dictionary.class,"from Dictionary where dictCode =?1",dictCode);
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
        return crudService.uniqueResultSql(DictionaryData.class,"from DictionaryData where code =?1",code);
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
        data.setIdDelete(true);
        crudService.update(data);
    }
}
