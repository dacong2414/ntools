package com.nuls.io.dao;

import java.util.List;

/**
 * @author hhu 【huan.hu@cnambition.com】
 * @version com.ambition.uis.dao, v 0.1 2017/11/3 10:44 hhu Exp $$
 */
public interface CrudDao {

    /**
     * 保存model
     *
     * @param model 传入的model
     */
    void save(Object model);

    /**
     * 更新model
     *
     * @param model 需要更新的持久化实体
     */
    void update(Object model);

    /**
     * 删除model
     *
     * @param model 传入的model
     */
    void delete(Object model);

    /**
     * get方法通过entityClasss以及主键id获取到对应的实体
     *
     * @param entityClass 持久化实体类
     * @param id          持久实体的唯一标识
     * @return 持久实体, 获取 {@code null} 没有找到
     */
    <T> T get(Class entityClass, Long id);

    /**
     * 合并model
     *
     * @param model 传入的model
     */
    void merge(Object model);

    /**
     * 将持久对象转换为托管对象
     *
     * @param object 持久对象
     */
    void evict(Object object);

    /**
     * 获取所有数据
     *
     * @param entityClass 持久对象
     * @return 对象全部集合
     */
    <T> List<T> findAll(Class entityClass);

    /**
     * 统计整个表
     *
     * @param entityClass 实体
     * @return 行数
     */
    Long count(Class entityClass);
}