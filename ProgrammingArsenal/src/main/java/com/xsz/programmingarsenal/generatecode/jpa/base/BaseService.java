package com.xsz.programmingarsenal.generatecode.jpa.base;

import cn.hutool.json.JSONObject;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.List;

/**
 * 通用接口
 *
 * @author  Brad
 */
public interface BaseService<T> {

    /**
     * 根据主键查询，返回单个对象
     *
     * @param id
     * @return
     */
    T findById(Serializable id);

    /**
     * 根据条件查询，返回单个对象
     *
     * @param specification
     * @return
     */
    T findOne(Specification<T> specification);

    /**
     * 根据Sql查询，返回单个对象
     *
     * @param sql
     * @param clazz
     * @param <S>
     * @return
     */
    <S> S findBySql(String sql, Class<S> clazz);

    /**
     * 查询所有记录
     *
     * @return
     */
    List<T> findAll();

    /**
     * 查询所有记录，排序
     *
     * @return
     */
    List<T> findAll(Sort sort);

    /**
     * 根据主键集合获取记录
     *
     * @param ids
     * @return
     */
    List<T> findAllByIds(Iterable<Serializable> ids);

    /**
     * 根据查询条件获取记录
     *
     * @param example
     * @return
     */
    List<T> findAll(Example<T> example);

    /**
     * 根据查询条件获取记录
     *
     * @param specification
     * @return
     */
    List<T> findAll(Specification<T> specification);

    /**
     * 根据查询条件获取记录，排序
     *
     * @param specification
     * @return
     */
    List<T> findAll(Specification<T> specification, Sort sort);

    /**
     * 分页查询所有记录
     *
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * 根据条件分页获取记录
     *
     * @param example
     * @param pageable
     * @return
     */
    Page<T> findAll(Example<T> example, Pageable pageable);

    /**
     * 根据条件分页获取记录
     *
     * @param specification
     * @param pageable
     * @return
     */
    Page<T> findAll(Specification<T> specification, Pageable pageable);

    /**
     * 根据Sql获取记录
     *
     * @param sql
     * @param clazz
     * @param <S>
     * @return
     */
    <S> List<S> findAllBySql(String sql, Class<S> clazz);

    /**
     * 根据hql获取记录
     *
     * @param hql
     * @param params
     * @param currPage
     * @param pageSize
     * @return
     */
    <S> List<S> findAllByHql(String hql, JSONObject params, Class<S> clazz, Integer currPage, Integer pageSize);

    /**
     * 执行sql
     *
     * @param sql
     */
    void executeSql(String sql);

    /**
     * 保存
     *
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * 批量保存
     *
     * @param entities
     * @return
     */
    List<T> saveAll(Iterable<T> entities);

    /**
     * 根据ID删除
     *
     * @param id
     */
    void deleteById(Serializable id);

    /**
     * 根据实体删除
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * 根据实体集合删除
     *
     * @param entities
     */
    void deleteAll(Iterable<T> entities);

    /**
     * 删除所有记录
     */
    void deleteAll();

    /**
     * 判断ID对应记录是否存在
     *
     * @param id
     * @return
     */
    boolean existsById(Serializable id);

    /**
     * 统计所有记录
     *
     * @return
     */
    long count();

    /**
     * 根据条件统计记录
     *
     * @param specification
     * @return
     */
    long count(Specification<T> specification);

    /**
     * 根据sql执行查询统计
     *
     * @param sql
     * @return
     */
    long countBySql(String sql);

    /**
     * 根据hql执行查询统计
     *
     * @param hql
     * @param params
     * @return
     */
    long countByHql(String hql, JSONObject params);

}
