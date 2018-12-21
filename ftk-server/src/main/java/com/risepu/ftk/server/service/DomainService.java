package com.risepu.ftk.server.service;

import java.util.List;

import com.risepu.ftk.server.domain.Domain;

/**
 * @author L-heng
 */
public interface DomainService {
    /**
     * 新增模板数据
     */
    Long add(Domain domain);

    /**
     * 物理删除模板数据
     */
    void deleteById(Long id);

    /**
     * 逻辑删除模板数据
     */
    void updateState(Long id);

    /**
     * 修改模板数据
     */
    void update(Domain domain);

    /**
     * 根据id查找模板数据
     */
    Domain selectById(Long id);

    /**
     * 根据模板id查询符合条件的所有模板数据
     */
    List<Domain> selectByTemplate(Long templateId);

    /**
     * 查找所有模板数据
     */
    List<Domain> selectAll();

    /**
     * 根据code查找模板数据
     */
    Domain selectByCode(String code);

    /**
     * @param firstIndex
     * @param pageSize
     * @return
     */
    List getAnyDomain(Integer firstIndex, Integer pageSize,String hql);

    /**
     *
     * @param hql
     * @return
     */
    List<Domain> getDomains(String hql);
}
