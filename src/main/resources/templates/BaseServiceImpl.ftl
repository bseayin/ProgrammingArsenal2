package ${basePackage};

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author  ${author}
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected BaseDao<T> baseDao;

    @Override
    public T findById(Serializable id) {
        Optional<T> entity = baseDao.findById(id);
        return entity.orElse(null);
    }

    @Override
    public T findOne(Specification<T> specification) {
        Optional<T> entity = baseDao.findOne(specification);
        return entity.orElse(null);
    }

    @Override
    public <S> S findBySql(String sql, Class<S> clazz) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(sql);
        List list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        if (em.isOpen()) {
            em.close();
        }
        if (list != null && !list.isEmpty()) {
            Object object = list.get(0);
            return JSONUtil.toBean(JSONUtil.toJsonStr(object), clazz);
        }
        return null;
    }

    @Override
    public List<T> findAll() {
        return baseDao.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return baseDao.findAll(sort);
    }

    @Override
    public List<T> findAllByIds(Iterable<Serializable> ids) {
        return baseDao.findAllById(ids);
    }

    @Override
    public List<T> findAll(Example<T> example) {
        return baseDao.findAll(example);
    }

    @Override
    public List<T> findAll(Specification<T> specification) {
        return baseDao.findAll(specification);
    }

    @Override
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return baseDao.findAll(specification, sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return baseDao.findAll(pageable);
    }

    @Override
    public Page<T> findAll(Example<T> example, Pageable pageable) {
        return baseDao.findAll(example, pageable);
    }

    @Override
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return baseDao.findAll(specification, pageable);
    }

    @Override
    public <S> List<S> findAllBySql(String sql, Class<S> clazz) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(sql);
        List list = query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).getResultList();
        if (em.isOpen()) {
            em.close();
        }
        if (list != null && !list.isEmpty()) {
            if (clazz != null) {
                return JSONUtil.toList(JSONUtil.toJsonStr(list),clazz);
            } else {
                return list;
            }
        }
        return new ArrayList<>();
    }

    @Override
    public <S> List<S> findAllByHql(String hql, JSONObject params, Class<S> clazz, Integer currPage, Integer pageSize) {
        Query query = entityManager.createQuery(hql);
        List list;
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (currPage != null && pageSize != null) {
            query.setFirstResult((currPage - 1) * pageSize);
            query.setMaxResults(pageSize);
        }
        list = query.getResultList();
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        if (list != null && !list.isEmpty()) {
            if (clazz != null) {
                return JSONUtil.toList(JSONUtil.toJsonStr(list),clazz);
            } else {
                return list;
            }
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void executeSql(String sql) {
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(sql);
        query.executeUpdate();
        if (em.isOpen()) {
            em.close();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public T save(T entity) {
        return baseDao.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<T> saveAll(Iterable<T> entities) {
        return baseDao.saveAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteById(Serializable id) {
        baseDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll(Iterable<T> entities) {
        baseDao.deleteAll(entities);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteAll() {
        baseDao.deleteAll();
    }

    @Override
    public boolean existsById(Serializable id) {
        return baseDao.existsById(id);
    }

    @Override
    public long count() {
        return baseDao.count();
    }

    @Override
    public long count(Specification<T> specification) {
        return baseDao.count(specification);
    }

    @Override
    public long countBySql(String sql) {
        long count;
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery(sql);
        Object obj = query.unwrap(NativeQueryImpl.class).getSingleResult();
        if (obj instanceof BigInteger) {
            count = ((BigInteger) obj).longValue();
        } else if (obj instanceof BigDecimal) {
            count = ((BigDecimal) obj).longValue();
        } else {
            count = (Long) obj;
        }
        if (em.isOpen()) {
            em.close();
        }
        return count;
    }

    @Override
    public long countByHql(String hql, JSONObject params) {
        long count;
        Query query = entityManager.createQuery(hql);
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        Object obj = query.getSingleResult();
        if (obj instanceof BigDecimal) {
            count = ((BigDecimal) obj).intValue();
        } else {
            count = Long.valueOf(String.valueOf(obj));
        }
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
        return count;
    }

    private EntityManager getEntityManager() {
        return entityManager.getEntityManagerFactory().createEntityManager();
    }

}
