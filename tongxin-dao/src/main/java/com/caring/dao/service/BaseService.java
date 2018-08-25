package com.caring.dao.service;

import com.caring.dao.config.DaoUtils;
import com.caring.dao.model.query.Page;
import com.caring.dao.model.query.PageFilter;
import com.caring.dao.model.query.PageParam;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author james
 */
public abstract class BaseService {

    protected final Logger LOG = LoggerFactory.getLogger(this.getClass());
    protected EntityManager entityManager;
    protected DozerBeanMapper daoDozerBeanMapper;

    @PersistenceContext
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    protected void setDozerBeanMapper(DozerBeanMapper daoDozerBeanMapper) {
        this.daoDozerBeanMapper = daoDozerBeanMapper;
    }

    protected static boolean map(DozerBeanMapper daoDozerBeanMapper, Object source, Object dest) {
        if (daoDozerBeanMapper != null && source != null && dest != null) {
            daoDozerBeanMapper.map(source, dest);
            return true;
        }
        return false;
    }

    protected String toConstGender(String wechatGender) {
        return "男".equals(wechatGender) ? "male" : "female";
    }

    protected String toJson(Object obj) {
        return DaoUtils.toJson(obj);
    }

    protected <T> Page<T> executePageQuery(PageParam pageParam, StringBuilder SQL, StringBuilder SQLcount) {
        return executePageQuery(pageParam, SQL, SQLcount, null);
    }

    protected <T> Page<T> executePageQuery(PageParam pageParam, StringBuilder SQL, StringBuilder SQLcount, StringBuilder ORDERby) {
        LOG.debug("executePageQuery::PageParam: {}", toJson(pageParam));
        if (pageParam != null && pageParam.getSize() > 0) {
            PageFilter filter = buildFilter(pageParam, SQL, SQLcount);
            Query query = buildQuery(filter, SQL, ORDERby);
            Query queryTotal = buildQuery(filter, SQLcount, null);
            // Pagesize
            query.setMaxResults(pageParam.getSize());
            // Offset
            query.setFirstResult(pageParam.getNumber() * pageParam.getSize());
            List<T> content = query.getResultList();
            long dataTotal = (long) queryTotal.getSingleResult();
            long pageTotal = (dataTotal / pageParam.getSize()) + (dataTotal % pageParam.getSize() > 0 ? 1 : 0);
            Page<T> page = new Page<>();
            page.setContent(content);
            page.setNumber(pageParam.getNumber());
            page.setSize(content != null ? content.size() : 0);
            page.setTotal(dataTotal);
            page.setPages(pageTotal);
            return page;
        } else {
            PageFilter filter = buildFilter(pageParam, SQL, SQLcount);
            Query query = buildQuery(filter, SQL, ORDERby);
            return Page.create(query.getResultList());
        }
    }

    protected <T> PageFilter buildFilter(PageParam pageParam, StringBuilder SQL, StringBuilder SQLcount) {
        PageFilter pageFilter = null;
        if (pageParam != null && pageParam.getFilter() != null) {
            pageFilter = (PageFilter) pageParam.getFilter();
            if (pageFilter.buildFilter().hasExternalFilter()) {
                // Need check SQL, SQLcount in different if statements
                if (SQL != null) {
                    LOG.debug("Build SQL filter");
                    String filter = pageFilter.getExternalFilter();
                    if (SQL.indexOf(" WHERE ") > 0 && !filter.contains(" WHERE ")) {
                        SQL.append(" AND ").append(filter);
                    } else if (!filter.contains(" WHERE ")) {
                        SQL.append(" WHERE ").append(filter);
                    } else {
                        SQL.append(filter);
                    }
                }
                if (SQLcount != null) {
                    LOG.debug("Build SQLcount filter");
                    String filter = pageFilter.getExternalFilter();
                    if (SQLcount.indexOf(" WHERE ") > 0 && !filter.contains(" WHERE ")) {
                        SQLcount.append(" AND ").append(filter);
                    } else if (!filter.contains(" WHERE ")) {
                        SQLcount.append(" WHERE ").append(filter);
                    } else {
                        SQLcount.append(filter);
                    }
                }
            }
        }
        return pageFilter;
    }

    private Query buildQuery(PageFilter filter, StringBuilder strQuery, StringBuilder ORDERby) {
        if (ORDERby != null && ORDERby.length() > 0) {
            strQuery.append(" ").append(ORDERby);
        }
        return filter == null ? entityManager.createQuery(strQuery.toString())
               : filter.fillQueryParameters(entityManager.createQuery(strQuery.toString()));
    }
}
