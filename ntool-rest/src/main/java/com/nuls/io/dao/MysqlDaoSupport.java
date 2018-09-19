package com.nuls.io.dao;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BasicType;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.nuls.io.model.common.IGlobalConstant;
import com.nuls.io.model.common.OperatorEnum;
import com.nuls.io.model.common.SimpleCriteria;
import com.nuls.io.utils.ObjectUtil;

/**
 * Dao的基础类(所有的dao都继承了它)
 * @author ambition
 * @version $Id: MysqlDaoSupport.java, v 0.1 2016年5月25日 上午10:30:15 ambition Exp $
 */
@Slf4j
@Component
public class MysqlDaoSupport implements CrudDao {
    @Autowired
    public HibernateTemplate     hibernateTemplate;

    private static final int     ORDER_BY_ASC          = 1;
    private static final int     ORDER_BY_DESC         = 2;
    private static final String  ORDER_BY_ASC_STRING   = "ASC";
    private static final String  ORDER_BY_DESC_STRING  = "DESC";

    private static final boolean NEED_ORDER_BY_KEYWORD = true;

    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }

    public MysqlDaoSupport(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public MysqlDaoSupport() {
    }

    /**
     * 保存model
     *
     * @param model 传入的model
     */
    public void save(Object model) {
        hibernateTemplate.save(model);
    }

    /**
     * 更新model
     * @param model 需要更新的持久化实体
     */
    public void update(Object model) {
        hibernateTemplate.update(model);
    }

    /**
     * 更新model
     * @param model 需要更新的持久化实体
     */
    public void saveOrUpdate(Object model) {
        hibernateTemplate.saveOrUpdate(model);
    }

    /**
     * 删除model
     * @param model 传入的model
     */
    public void delete(Object model) {
        hibernateTemplate.delete(model);
        hibernateTemplate.flush();
    }

    /**
     * get方法通过entityClasss以及主键id获取到对应的实体
     * @param entityClass 持久化实体类
     * @param id 持久实体的唯一标识
     * @return 持久实体, 获取 {@code null} 没有找到
     */
   public <T> T get(Class entityClass, Long id) {
        return null;
    }

    /**
     * 合并model
     * @param model 传入的model
     */
    public void merge(Object model) {
        hibernateTemplate.merge(model);
    }

    /**
     * 查询sql，查询list集合
     * 参数使用values、types
     * 不用分页查询时：pageSize pageIndex 设置为-1 IGlobalConstant.NO_PAGE_QUERY
     * class User{
     *     int id;
     *     String name;
     *     boolean isUpdate;
     *     Date born;
     * }
     * SELECT * FROM user WHERE id=? and name=? and bord between ? and ?
     * values : [1, "222", new Date(), new Date()]
     * types : [IntegerType, StringType,DateType, DateType]
     * @param entityClass 持久化实体类
     * @param sql 查询语句
     * @param pageSize 分页信息 分页大小
     * @param pageIndex 分页信息 起始位置
     * @param values 查询条件值
     * @param types  查询条件值类型  org.hibernate.type包下IntegerType，StringType，DateType，DoubleType
     * @param <T> 返回实体
     * @param orderDesc 降序
     * @param orderAsc 升序
     * @return 实体集合
     */
    public <T> ArrayList<T> sqlExecuteList(final Class entityClass, final String sql,
                                           final int pageSize, final int pageIndex,
                                           final Object[] values, final Type[] types,
                                           final String[] orderDesc, final String[] orderAsc) {

        return hibernateTemplate.execute(new HibernateCallback<ArrayList<T>>() {
            public ArrayList<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (pageSize > 0 && pageIndex > -1) {
                    query.setFirstResult(pageIndex * pageSize);
                    query.setMaxResults(pageSize);
                }
                if (values != null && types != null) {
                    query.setParameters(values, types);
                }
                if (orderAsc != null) {
                    sqlOrderBy(orderAsc, ORDER_BY_ASC);
                }
                if (orderDesc != null) {
                    sqlOrderBy(orderDesc, ORDER_BY_DESC);
                }
                if (entityClass != null) {
                    query.addEntity(entityClass);
                }
                List list = query.list();
                return ObjectUtil.typeConversion(list);
            }
        });
    }

    /**
     * 查询sql，查询list集合
     * 参数使用keys, values
     * 不用分页查询时：pageSize pageIndex 设置为-1 IGlobalConstant.NO_PAGE_QUERY
     *class User{
     *     int id;
     *     String name;
     *     boolean isUpdate;
     *     Date born;
     * }
     * SELECT * FROM user WHERE id=:id and name=:name and isUpdate=isUpdate and born=:born
     * keys: ["id", "name", "isUpdate", "born"]
     * values : [1, "222", true, new Date()]
     * @param entityClass 持久化实体类
     * @param sql 查询语句
     * @param pageSize 分页信息 分页大小
     * @param pageIndex 分页信息 起始位置
     * @param keys  查询条件键
     * @param values 查询条件值
     * @param <T> 返回实体
     * @return 实体集合
     */
    public <T> ArrayList<T> sqlExecuteList(final Class entityClass, final String sql,
                                           final int pageSize, final int pageIndex,
                                           final String[] keys, final Object[] values) {

        return hibernateTemplate.execute(new HibernateCallback<ArrayList<T>>() {
            public ArrayList<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (pageSize > 0 && pageIndex > -1) {
                    query.setFirstResult(pageIndex * pageSize);
                    query.setMaxResults(pageSize);
                }
                if (values != null && keys != null && values.length == keys.length) {
                    fillCondition(query, keys, values);
                }
                if (entityClass != null) {
                    query.addEntity(entityClass);
                }
                List list = query.list();
                return ObjectUtil.typeConversion(list);
            }
        });
    }

    /**
     * SQL方式查询实体集合，不进行排序查询
     * 注：无条件全表查询时慎用
     * @see #sqlExecuteList(Class, String, int, int, String[], Object[], Type[], String[], String[])
     */
    public <T> ArrayList<T> sqlExecuteList(Class entityClass, String sql, int pageSize,
                                           int pageIndex) {
        return sqlExecuteList(entityClass, sql, pageSize, pageIndex, null, null, null, null, null);
    }

    /**
     * SQL方式查询实体集合
     * 参数使用keys, values， types
     * 不用分页查询时：pageSize pageIndex 设置为-1 IGlobalConstant.NO_PAGE_QUERY
     *  Table User{
     *     id BigInteger;
     *     name VarChar2(20);
     *     born Date;
     * }
     * @param entityClass 持久化实体类
     * @param sql 查询语句
     * @param pageSize 分页信息 分页大小
     * @param pageIndex 分页信息 起始位置
     * @param keys  查询条件键
     * @param values 查询条件值
     * @param types  查询条件值类型  org.hibernate.type包下IntegerType，StringType，DateType，DoubleType
     * @param <T> 返回实体 entityClass为空时，默认返回形式：[[列1数据, 列2数据, 列3数据], [列1数据, 列2数据, 列3数据]]
     * @return 实体集合
     */
    public <T> ArrayList<T> sqlExecuteList(final Class entityClass, final String sql,
                                           final int pageSize, final int pageIndex,
                                           final String[] keys, final Object[] values,
                                           final Type[] types, final String[] orderDesc,
                                           final String[] orderAsc) {
        return hibernateTemplate.execute(new HibernateCallback<ArrayList<T>>() {
            public ArrayList<T> doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session
                    .createSQLQuery(appendOrderByToSQL(sql, orderDesc, orderAsc));
                if (pageSize > 0 && pageIndex > -1) {
                    query.setFirstResult(pageIndex * pageSize);
                    query.setMaxResults(pageSize);
                }
                fillCondition(query, keys, values, types);
                if (entityClass != null) {
                    query.addEntity(entityClass);
                }
                return ObjectUtil.typeConversion(query.list());
            }
        });
    }

    /**
     * 查询sql，查询list集合
     * 不用分页查询时：pageSize pageIndex 设置为-1 IGlobalConstant.NO_PAGE_QUERY
     * class User{
     *     int id;
     *     String name;
     * }
     * values : [1, "222"]
     * keys : ["id", "name"]
     * @param entityClass 持久化实体类
     * @param pageSize 分页信息 分页大小
     * @param pageIndex 分页信息 起始位置
     * @param values 查询条件值
     * @param keys  查询key 实体的属性名
     * @param <T> 返回实体
     * @param orderDesc 降序
     * @param orderAsc 升序
     * @return 实体集合
     */
    public <T> ArrayList<T> criteriaExecuteList(final Class entityClass, final int pageSize,
                                                final int pageIndex, final String[] keys,
                                                final Object[] values, final String[] orderDesc,
                                                final String[] orderAsc) {
        return hibernateTemplate.execute(new HibernateCallback<ArrayList<T>>() {
            public ArrayList<T> doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(entityClass);
                if (pageSize > 0 && pageIndex > -1) {
                    criteria.setFirstResult(pageSize * (pageIndex));
                    criteria.setMaxResults(pageSize);
                }
                if (values != null && keys != null && keys.length == values.length) {
                    criteriaWhereCondition(criteria, keys, values);
                }
                if (orderDesc != null) {
                    criteriaOrderBy(criteria, orderDesc, ORDER_BY_DESC);
                }
                if (orderAsc != null) {
                    criteriaOrderBy(criteria, orderAsc, ORDER_BY_ASC);
                }
                return ObjectUtil.typeConversion(criteria.list());
            }
        });
    }

    /**
     * SQL方式根据条件统计集合的总条数
     * tableName "user"
     * keys ["age"]
     * values [11]
     * 结果：SELECT * FROM user WHERE age = 11
     * @param tableName 数据库表名
     * @param keys 查询键（数据库表列名）
     * @param values 查询值
     * @param types
     * @return 成功与否
     */
    public Long sqlExecuteCount(final String tableName, final String[] keys, final Object[] values,
                                final Type[] types) {
        return hibernateTemplate.execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT COUNT(1) FROM ").append(tableName);
                if (keys != null) {
                    sql.append(sqlWhereCondition(keys));
                }
                Query query = session.createSQLQuery(sql.toString());
                fillCondition(query, keys, values, types);
                BigInteger big = ObjectUtil.typeConversion(query.uniqueResult());
                return big.longValue();
            }
        });
    }

    /**
     * 查询sql,查询单实体
     * 不用分页查询时：pageSize pageIndex 设置为-1 IGlobalConstant.NO_PAGE_QUERY
     * @param entityClass 持久化实体类
     * @param sql 查询语句
     * @param keys  查询条件值类型  org.hibernate.type包下IntegerType，StringType，DateType，DoubleType
     * @param values 查询条件值
     * @return 返回实体
     */
    public <T> T sqlExecuteUniqueResult(final Class entityClass, final String sql,
                                        final String[] keys, final Object[] values) {
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                if (values != null && keys != null && values.length == keys.length) {
                    fillCondition(query, keys, values);
                }
                if (entityClass != null) {
                    query.addEntity(entityClass);
                }
                return ObjectUtil.typeConversion(query.uniqueResult());
            }
        });
    }

    /**
     * 查询sql
     * class User{
     *     int id;
     *     String name;
     *     int age;
     * }
     * values : [1, "22", 11]
     * keys : ["id", "name", age]
     * @param entityClass 持久化实体类
     * @param keys  查询key 实体的属性名
     * @param values 查询条件值
     * @param <T> 返回实体
     * @return 实体集合
     */
    public <T> T criteriaExecuteUniqueResult(final Class entityClass, final String[] keys,
                                             final Object[] values) {
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(entityClass);
                if (values != null && keys != null) {
                    for (int i = 0; i < keys.length; i++) {
                        criteria.add(Restrictions.eq(keys[i], values[i]));
                    }
                }
                return ObjectUtil.typeConversion(criteria.uniqueResult());
            }
        });
    }

    /**
     * 更新对象
     * UPDATE USER SET AGE=? WHERE ID = ?
     * values [22, 1]
     * types [LongType, LongType]
     * @param sql sql语句
     * @param values 查询条件值
     * @param types 查询条件值类型  org.hibernate.type包下IntegerType，StringType，DateType，DoubleType
     * @return 成功与否
     */
    public Boolean sqlExecuteUpdate(final String sql, final Object[] values, final Type[] types) {

        return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = session.createSQLQuery(sql);
                if (values != null && types != null && types.length == values.length) {
                    query.setParameters(values, types);
                }
                int result = query.executeUpdate();
                return result > 0;
            }
        });
    }

    /**
     * 更新对象
     * UPDATE USER SET AGE=:age WHERE ID =:id
     * key ["age", "id"]
     * values [22, 1]
     * @param sql sql语句
     * @param keys 查询条件键
     * @param values 查询条件值
     * @return 成功与否
     */
    public Boolean sqlExecuteUpdate(final String sql, final String[] keys, final Object[] values) {

        return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = session.createSQLQuery(sql);
                if (values != null && keys != null && keys.length == values.length) {
                    fillCondition(query, keys, values);
                }
                int result = query.executeUpdate();
                return result > 0;
            }
        });
    }

    /**
     * 批量更新对象
     * 批量是通过where条件in来更新多条记录
     * DELETE FROM USER WHERE ID in (:id) AND age =:age
     * String[] paramKeys {"age"}
     * Object[] paramValues {12}
     * String[] paramListKeys {"id"}
     * Object[][] paramListValues [[1, 2, 3]]
     * @param sql sql语句
     * @param paramKeys 查询set 键
     * @param paramValues 查询set 值
     * @param paramListKeys 查询条件键
     * @param paramListValues 查询条件值
     * @return 成功与否
     */
    public Boolean sqlBatchUpdate(final String sql, final String[] paramKeys,
                                  final Object[] paramValues, final String[] paramListKeys,
                                  final Object[][] paramListValues) {
        return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            public Boolean doInHibernate(Session session) throws HibernateException {
                Query query = session.createSQLQuery(sql);
                if (paramKeys != null && paramValues != null
                    && paramKeys.length == paramValues.length) {
                    fillCondition(query, paramKeys, paramValues);
                }
                if (paramListKeys != null && paramListValues != null
                    && paramListKeys.length == paramListValues.length) {
                    fillListCondition(query, paramListKeys, paramListValues);
                }
                int result = query.executeUpdate();
                return result > 0;
            }
        });
    }

    /**
     * 根据条件统计集合的总条数
     * tableName "user"
     * keys ["age"]
     * values [11]
     * 结果：SELECT * FROM user WHERE age = 11
     * @param tableName 数据库表名
     * @param keys 查询键（数据库表列名）
     * @param values 查询值
     * @return 成功与否
     */
    public Long sqlExecuteCount(final String tableName, final String[] keys, final Object[] values) {

        return hibernateTemplate.execute(new HibernateCallback<Long>() {
            public Long doInHibernate(Session session) throws HibernateException {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT COUNT(1) FROM ").append(tableName);
                if (keys != null) {
                    sql.append(sqlWhereCondition(keys));
                }
                Query query = session.createSQLQuery(sql.toString());
                if (values != null) {
                    fillCondition(query, values);
                }
                BigInteger big = ObjectUtil.typeConversion(query.uniqueResult());
                return big.longValue();
            }
        });
    }

    /**
     * 拼装where语句
     * @param keys 键
     * @return where部分语句
     */
    private String sqlWhereCondition(String[] keys) {
        StringBuilder where = new StringBuilder();
        where.append(" WHERE ");
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            if (i > 0) {
                where.append(" AND ");
            }
            where.append(key).append("=?");
        }
        return where.toString();
    }

    /**
     * 填充where值
     * @param  query 查询对象
     * @param values 值
     */
    private void fillCondition(Query query, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
    }

    /**
     * 填充where值
     * @param  query 查询对象
     * @param keys 键
     * @param values 值
     */
    private void fillCondition(Query query, String[] keys, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            query.setParameter(keys[i], values[i]);
        }
    }

    /**
     * 填充where值
     * @param  query 查询对象
     * @param keys 键
     * @param values 值
     */
    private void fillListCondition(Query query, String[] keys, Object[][] values) {
        for (int i = 0; i < values.length; i++) {
            query.setParameterList(keys[i], values[i]);
        }
    }

    /**
     * sql 方式拼装排序
     * @param keys 排序的key
     * @param orderBy 排序类型
     */
    private String sqlOrderBy(String[] keys, int orderBy) {
        StringBuilder order = new StringBuilder();
        order.append(" ORDER BY ");
        for (int i = 0; i < keys.length; i++) {
            switch (orderBy) {
                case ORDER_BY_ASC:
                    order.append(keys[i]).append(ORDER_BY_ASC_STRING);
                    break;
                case ORDER_BY_DESC:
                    order.append(keys[i]).append(ORDER_BY_DESC_STRING);
                    break;
            }
            if (i == keys.length - 1) {
                order.append(", ");
            }
        }
        return order.toString();
    }

    /**
     * criteria 方式拼装where条件
     * @param criteria
     * @param keys
     * @param values
     */
    private void criteriaWhereCondition(Criteria criteria, String[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            criteria.add(Restrictions.eq(keys[i], values[i]));
        }
    }

    /**
     * criteria 方式拼装排序
     * @param criteria
     * @param keys 排序的key
     * @param orderBy 排序类型
     */
    private void criteriaOrderBy(Criteria criteria, String[] keys, int orderBy) {
        for (int i = 0; i < keys.length; i++) {
            switch (orderBy) {
                case ORDER_BY_ASC:
                    criteria.addOrder(Order.asc(keys[i]));
                    break;
                case ORDER_BY_DESC:
                    criteria.addOrder(Order.desc(keys[i]));
                    break;
            }
        }

    }

    /**
     * 为sql添加order by排序
     * @param sql sql语句
     * @param desc 降序
     * @param asc 升序
     * @return 生成sql
     */
    private String appendOrderByToSQL(String sql, String[] desc, String[] asc) {
        boolean needKeyWord = NEED_ORDER_BY_KEYWORD;
        if (asc != null) {
            sql = sql.concat(sqlOrderBy(asc, ORDER_BY_ASC, needKeyWord));
            needKeyWord = !NEED_ORDER_BY_KEYWORD;
        }
        if (desc != null) {
            sql = sql.concat(sqlOrderBy(desc, ORDER_BY_DESC, needKeyWord));
        }
        return sql;
    }

    /**
     * 填充where值
     * @param  query 查询对象
     * @param keys 键
     * @param values 值
     */
    private void fillCondition(Query query, String[] keys, Object[] values, Type[] types) {
        if (ArrayUtils.isNotEmpty(keys) && ArrayUtils.isNotEmpty(types)) {
            fillConditionByKeyValueType(query, keys, values, types);
        } else if (ArrayUtils.isNotEmpty(keys)) {
            fillConditionByKeyValue(query, keys, values);
        } else if (ArrayUtils.isNotEmpty(types)) {
            fillConditionByValueType(query, values, types);
        } else if (ArrayUtils.isNotEmpty(values)) {
            fillConditionByValue(query, values);
        }
    }

    /**
     * sql 方式拼装排序
     * @param keys 排序的key
     * @param orderBy 排序类型
     */
    private String sqlOrderBy(String[] keys, int orderBy, boolean needKeyWord) {
        StringBuilder order = new StringBuilder();
        if (needKeyWord) {
            order.append(" ORDER BY ");
        }
        for (int i = 0; i < keys.length; i++) {
            switch (orderBy) {
                case ORDER_BY_ASC:
                    order.append(keys[i]).append(ORDER_BY_ASC_STRING);
                    break;
                case ORDER_BY_DESC:
                    order.append(keys[i]).append(ORDER_BY_DESC_STRING);
                    break;
                default:
                    break;
            }
            if (i == keys.length - 1) {
                order.append(", ");
            }
        }
        return order.toString();
    }

    /**
     * 填充? :key 类型值
     */
    private void fillConditionByKeyValueType(Query query, String[] keys, Object[] values,
                                             Type[] types) {
        for (int i = 0; i < keys.length; i++) {
            query.setParameter(keys[i], values[i], types[i]);
        }
    }

    /**
     * 填充? :key 类型值
     */
    private void fillConditionByKeyValue(Query query, String[] keys, Object[] values) {
        for (int i = 0; i < keys.length; i++) {
            query.setParameter(keys[i], values[i]);
        }
    }

    /**
     * 填充? :key 类型值
     */
    private void fillConditionByValueType(Query query, Object[] values, Type[] types) {
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i], types[i]);
        }
    }

    /**
     * 填充? :key 类型值
     */
    private void fillConditionByValue(Query query, Object[] values) {
        for (int i = 0; i < values.length; i++) {
            query.setParameter(i, values[i]);
        }
    }

    /**
     * HQL方式查询，主要应对缓存中存在该主键的实体又想查出数据封装到一个新对象
     * table user(user_name varchar(255), age int(3))
     * User{
     * String userName;
     * int age;
     * }
     * select userName, age from user;
     *
     * @param entityClass 持久化实体类
     * @param sql         查询语句
     * @return 返回实体 entityClass为空时，默认返回形式：[列1数据, 列2数据, 列3数据]
     */
    public <T> T sqlNewObjectQuery(final Class entityClass, final String sql) {
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            @Override
            public T doInHibernate(Session session) {
                SQLQuery query = session.createSQLQuery(sql);
                if (entityClass != null) {
                    queryAddScalar(query, entityClass);
                    query.setResultTransformer(Transformers.aliasToBean(entityClass));
                }
                return ObjectUtil.typeConversion(query.list().get(0));
            }
        });
    }

    /**
     * 添加上别名映射
     *
     * @param query       查询对象
     * @param entityClass 实体
     */
    private void queryAddScalar(SQLQuery query, Class entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        if (fields != null) {
            for (Field field : fields) {
                Column column = field.getAnnotation(Column.class);
                if (column != null) {
                    query.addScalar(field.getName(), transToBasicType(field.getType()));
                }
            }
        }
    }

    /**
     * 列类型
     *
     * @param typeClass 类型
     * @return Type
     */
    private BasicType transToBasicType(Class typeClass) {
        typeClass.getSimpleName();
        switch (typeClass.getSimpleName()) {
            case "String":
                return StandardBasicTypes.STRING;
            case "Long":
                return StandardBasicTypes.LONG;
            case "Integer":
                return StandardBasicTypes.INTEGER;
            case "Date":
                return StandardBasicTypes.DATE;
            default:
                return null;
        }
    }

    /**
     * 执行sql DDl操作
     *
     * @param sql 完整的DDlsql
     * @return 是否执行成功
     */
    public Boolean sqlExecuteUpdate(final String sql) {
        return hibernateTemplate.execute(new HibernateCallback<Boolean>() {
            @Override
            public Boolean doInHibernate(Session session) {
                Query query = session.createSQLQuery(sql);
                return query.executeUpdate() > 0;
            }
        });
    }

    public <T> List<T> findAll(Class entityClass) {
        return criteriaExecuteList(entityClass);
    }

    /**
     * Criteria不分页和排序的全表查询方式获取实体集合（慎用）
     *
     * @see #criteriaExecuteList(Class, int, int, String[], Object[], String[], String[])
     */
    public <T> List<T> criteriaExecuteList(Class entityClass) {
        return criteriaExecuteList(entityClass, IGlobalConstant.NO_PAGE_QUERY,
            IGlobalConstant.NO_PAGE_QUERY, null, null, null, null);
    }

    @Override
    public void evict(Object object) {
        hibernateTemplate.evict(object);
    }

    @Override
    public Long count(Class entityClass) {
        return criteriaCount(new SimpleCriteria(entityClass));
    }

    /**
     * Criteria不排序的查询方式获取实体集合
     */
    Long criteriaCount(final SimpleCriteria simpleCriteria) {
        return hibernateTemplate.execute(new HibernateCallback<Long>() {
            @Override
            public Long doInHibernate(Session session) {
                Criteria criteria = session.createCriteria(simpleCriteria.getPerClass());
                criteria.setProjection(Projections.rowCount());
                criteriaCondition(criteria, simpleCriteria);
                return ObjectUtil.typeConversion(criteria.uniqueResult());
            }
        });
    }

    /**
     * criteria 方式拼装where条件
     *
     * @param criteria hibernate查询实例
     * @param simple   查询条件
     */
    private <T> void criteriaCondition(Criteria criteria, SimpleCriteria<T> simple) {
        if (simple.getKey() != null && simple.getValue() != null && simple.getOperator() != null) {
            for (int i = 0, counter = 0; i < simple.getOperator().size(); i++) {
                counter = dependOnEnum(criteria, simple.getOperator().get(counter),
                    simple.getKey(), simple.getValue(), counter);
            }
        } else if (simple.getKey() != null && simple.getValue() != null) {
            for (int i = 0; i < simple.getKey().size(); i++) {
                criteria.add(Restrictions.eq(simple.getKey().get(i), simple.getValue().get(i)));
            }
        }
    }

    /**
     * 根据表达式赋值
     *
     * @param criteria 构造
     * @param opt      表达式
     * @param key      key集合
     * @param val      value集合
     * @param counter  计数器
     * @return 增长后的计数器
     */
    private Integer dependOnEnum(Criteria criteria, OperatorEnum opt, List<String> key,
                                 List<Object> val, int counter) {
        switch (opt) {
            case EQ:
                criteria.add(Restrictions.eq(key.get(counter), val.get(counter)));
                break;
            case NE:
                criteria.add(Restrictions.ne(key.get(counter), val.get(counter)));
                break;
            case LT:
                criteria.add(Restrictions.lt(key.get(counter), val.get(counter)));
                break;
            case LE:
                criteria.add(Restrictions.le(key.get(counter), val.get(counter)));
                break;
            case GT:
                criteria.add(Restrictions.gt(key.get(counter), val.get(counter)));
                break;
            case GE:
                criteria.add(Restrictions.ge(key.get(counter), val.get(counter)));
                break;
            case IN:
                criteria.add(Restrictions.in(key.get(counter), (Collection) val.get(counter)));
                break;
            case EXACT_LIKE:
                criteria.add(Restrictions.like(key.get(counter), val.get(counter)));
                break;
            case END_LIKE:
                criteria.add(Restrictions.like(key.get(counter), String.valueOf(val.get(counter)),
                    MatchMode.END));
                break;
            case START_LIKE:
                criteria.add(Restrictions.like(key.get(counter), String.valueOf(val.get(counter)),
                    MatchMode.START));
                break;
            case ANYWHERE_LIKE:
                criteria.add(Restrictions.like(key.get(counter), String.valueOf(val.get(counter)),
                    MatchMode.ANYWHERE));
                break;
            case NULL:
                criteria.add(Restrictions.isNull(key.get(counter)));
                break;
            case NOT_NULL:
                criteria.add(Restrictions.isNotNull(key.get(counter)));
                break;
            case BETWEEN:
                criteria.add(Restrictions.between(key.get(counter), val.get(counter),
                    val.get(counter + 1)));
                return counter + 2;
            default:
               // log.error("传入参数有误，[键{},表达式{},值{}]", key.get(counter), opt.value(), val.get(counter));
        }
        return ++counter;
    }

}
