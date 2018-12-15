/*
 * MIT License
 *
 * Copyright (c) 2008-2017 q-wang, &lt;apeidou@gmail.com&gt;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.lc4ever.framework.dao.hibernate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.NotImplementedException;
import org.hibernate.Cache;
import org.hibernate.Hibernate;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateCallback;

import net.lc4ever.framework.dao.GenericDao;
import net.lc4ever.framework.domain.BaseEntity;

/**
 *
 * @revision $Revision:$
 * @author <a href="mailto:apeidou@gmail.com">Q-Wang</a>
 */
public class GenericDaoHibernate implements GenericDao {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected SessionFactory sessionFactory;

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * @see GenericDao#list(java.lang.Class)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> List<E> list(final Class<E> clazz) {
		logger.trace("Listing All Entries for Class:{}.", clazz.getName());
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		return getSession().createQuery(query).getResultList();
		//		return getSession().createCriteria(clazz).list();
	}

	/**
	 * @see GenericDao#get(java.lang.Class, java.io.Serializable)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> E get(final Class<E> clazz, final ID id) {
		logger.trace("Getting Entry for Class:{}, using Id:{}.", clazz.getName(), id);
		return getSession().get(clazz, id);
	}

	/**
	 * @see GenericDao#delete(sinonet.framework.bean.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void delete(final E entity) {
		logger.trace("Deleting Entry for Class:{}, using Id:{}.", entity.getClass().getName(), entity.getId());
		getSession().delete(entity);
	}

	/**
	 * @see GenericDao#save(sinonet.framework.bean.BaseEntity)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> ID save(final E entity) {
		logger.trace("Saving Entry for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
		return (ID) getSession().save(entity);
	}

	/**
	 * @see GenericDao#update(sinonet.framework.bean.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void update(final E entity) {
		logger.trace("Updating Entry for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
		getSession().update(entity);
	}

	/**
	 * @see GenericDao#saveOrUpdate(sinonet.framework.bean.BaseEntity)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void saveOrUpdate(final E entity) {
		logger.trace("Saving Or Updating for Class:{}, Id:{}.", entity.getClass().getName(), entity.getId());
		getSession().saveOrUpdate(entity);
	}

	/**
	 * @see GenericDao#count(java.lang.Class)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> long count(final Class<E> clazz) {
		logger.trace("Counting Entries for Class:{}.", clazz.getName());
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		query.select(builder.count(query.from(clazz)));
		return getSession().createQuery(query).getSingleResult();
	}

	/**
	 * Support {@link Limition}.
	 * @see GenericDao#hql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> hql(final String hql, final Object... args) {
		logger.trace("HQL query, hql:[{}], args count:{}.", hql, args == null ? 0 : args.length);
		Query<?> query = getSession().createQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.list();
	}

	/**
	 * @see GenericDao#hql(long, long, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> hql(final long firstResult, final long maxResults, final String hql, final Object... args) {
		logger.trace("HQL query, hql:[{}], args count:{}.", hql, args == null ? 0 : args.length);
		Query<?> query = getSession().createQuery(hql);
		query.setFirstResult((int) firstResult);
		query.setMaxResults((int) maxResults);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.list();
	}

	/**
	 * @see GenericDao#hql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> hql(final Class<T> expectType, final String hql, final Object... args) {
		return (List<T>) hql(hql, args);
	}

	/**
	 * @see GenericDao#hql(java.lang.Class, long, long, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> hql(final Class<T> expectType, final long firstResult, final long maxResults, final String hql, final Object... args) {
		return (List<T>) hql(firstResult, maxResults, hql, args);
	}

	/**
	 * Support {@link Limition}
	 * @see GenericDao#sql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> sql(final String sql, final Object... args) {
		return sql((ResultTransformer) null, sql, args);
	}

	/**
	 * @see GenericDao#sql(long, long, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> sql(final long firstResult, final long maxResults, final String sql, final Object... args) {
		return sql((ResultTransformer) null, firstResult, maxResults, sql, args);
	}

	/**
	 * @see GenericDao#sql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> sql(final Class<T> expectType, final String sql, final Object... args) {
		return (List<T>) sql(sql, args);
	}

	/**
	 * @see GenericDao#sql(java.lang.Class, long, long, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> sql(final Class<T> expectType, final long firstResult, final long maxResults, final String sql, final Object... args) {
		return (List<T>) sql(firstResult, maxResults, sql, args);
	}

	/**
	 * @see GenericDao#uniqueResultHql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object uniqueResultHql(final String hql, final Object... args) {
		logger.trace("HQL unique query, hql:[{}], args count:{}.", hql, args == null ? 0 : args.length);
		Query<?> query = getSession().createQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.uniqueResult();
	}

	/**
	 * @see GenericDao#uniqueResultHql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T uniqueResultHql(final Class<T> expectType, final String hql, final Object... args) {
		return (T) uniqueResultHql(hql, args);
	}

	/**
	 * @see GenericDao#uniqueResultByProperties(java.lang.Class, java.lang.String[], java.lang.Object[])
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> E uniqueResultByProperties(final Class<E> clazz, final String[] properties, final Object[] args) {
		if (properties == null || args == null) {
			throw new NullPointerException("argument properties and args must not be null.");
		}
		if (properties.length != args.length) {
			throw new IllegalArgumentException("argument properties.length must equals args.length.");
		}
		logger.trace("UniqueResultByProperties: properties:{}", properties, null);

		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> root = query.from(clazz);

		Predicate[] predicates = new Predicate[properties.length];
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			if (property == null) {
				throw new NullPointerException("properties[" + i + "] must not be null.");
			}
			Object arg = args[i];
			predicates[i] = arg == null ? builder.isNotNull(root.get(property)) : builder.equal(root.get(property), arg);
		}
		query.where(builder.and(predicates));
		return getSession().createQuery(query).uniqueResult();
	}

	/**
	 * @see GenericDao#uniqueResultByProperty(java.lang.Class, java.lang.String, java.lang.Object)
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> E uniqueResultByProperty(final Class<E> clazz, final String property, final Object arg) {
		return uniqueResultByProperties(clazz, new String[] { property }, new Object[] { arg });
	}

	/**
	 * @see GenericDao#queryByProperties(java.lang.Class, java.lang.String[], java.lang.Object[], org.hibernate.criterion.Order[])
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperties(final Class<E> clazz, final String[] properties, final Object[] args, final Order... orders) {
		if (properties == null || args == null) {
			throw new NullPointerException("argument properties and args must not be null.");
		}
		if (properties.length != args.length) {
			throw new IllegalArgumentException("argument properties.length must equals args.length.");
		}
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> root = query.from(clazz);

		Predicate[] predicates = new Predicate[properties.length];
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			if (property == null) {
				throw new NullPointerException("properties[" + i + "] must not be null.");
			}
			Object arg = args[i];
			predicates[i] = arg == null ? builder.isNotNull(root.get(property)) : builder.equal(root.get(property), arg);
		}
		query.where(builder.and(predicates));
		if (orders != null) {
			List<javax.persistence.criteria.Order> internalOrders = new ArrayList<>();
			for (Order order : orders) {
				internalOrders.add(order.isAscending() ? builder.asc(root.get(order.getPropertyName())) : builder.desc(root.get(order.getPropertyName())));
			}
			query.orderBy(internalOrders);
		}
		return getSession().createQuery(query).getResultList();
	}

	/**
	 * @see GenericDao#queryByProperty(java.lang.Class, java.lang.String, java.lang.Object, org.hibernate.criterion.Order[])
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperty(final Class<E> clazz, final String property, final Object arg, final Order... orders) {
		return queryByProperties(clazz, new String[] { property }, new Object[] { arg }, orders);
	}

	/**
	 * @see GenericDao#queryByProperties(java.lang.Class, long, long, java.lang.String[], java.lang.Object[], org.hibernate.criterion.Order[])
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperties(final Class<E> clazz, final long firstResult, final long maxResults, final String[] properties, final Object[] args, final Order... orders) {
		if (properties == null || args == null) {
			throw new NullPointerException("argument properties and args must not be null.");
		}
		if (properties.length != args.length) {
			throw new IllegalArgumentException("argument properties.length must equals args.length.");
		}
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<E> query = builder.createQuery(clazz);
		Root<E> root = query.from(clazz);

		Predicate[] predicates = new Predicate[properties.length];
		for (int i = 0; i < properties.length; i++) {
			String property = properties[i];
			if (property == null) {
				throw new NullPointerException("properties[" + i + "] must not be null.");
			}
			Object arg = args[i];
			predicates[i] = arg == null ? builder.isNotNull(root.get(property)) : builder.equal(root.get(property), arg);
		}
		query.where(builder.and(predicates));
		if (orders != null) {
			List<javax.persistence.criteria.Order> internalOrders = new ArrayList<>();
			for (Order order : orders) {
				internalOrders.add(order.isAscending() ? builder.asc(root.get(order.getPropertyName())) : builder.desc(root.get(order.getPropertyName())));
			}
			query.orderBy(internalOrders);
		}
		return getSession().createQuery(query).setMaxResults((int) maxResults).setFirstResult((int) firstResult).getResultList();
	}

	/**
	 * @see GenericDao#queryByProperty(java.lang.Class, long, long, java.lang.String, java.lang.Object, org.hibernate.criterion.Order[])
	 */
	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperty(final Class<E> clazz, final long firstResult, final long maxResults, final String property, final Object arg, final Order... orders) {
		return queryByProperties(clazz, firstResult, maxResults, new String[] { property }, new Object[] { arg }, orders);
	}

	/**
	 * @see GenericDao#uniqueResultSql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object uniqueResultSql(final String sql, final Object... args) {
		return uniqueResultSql((ResultTransformer) null, sql, args);
	}

	/**
	 * @see GenericDao#uniqueResultSql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> T uniqueResultSql(final Class<T> expectType, final String sql, final Object... args) {
		logger.debug("SQL unique query, expectType:{}, sql:[{}], args count:{}.", new Object[] { expectType.getName(), sql, args.length });
		NativeQuery<T> query = getSession().createNativeQuery(sql, expectType);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.uniqueResult();
	}

	/**
	 * @see GenericDao#criteria(org.hibernate.criterion.DetachedCriteria)
	 */
	@Override
	public List<?> criteria(final DetachedCriteria criteria) {
		logger.debug("DetachedCriteria query:{}.", criteria);
		return criteria.getExecutableCriteria(getSession()).list();
	}

	/**
	 * @see GenericDao#callback(org.springframework.orm.hibernate3.HibernateCallback)
	 */
	@Override
	public <T> T callback(final HibernateCallback<T> callback) {
		logger.debug("Callback Execute:{}.", callback);
		return callback.doInHibernate(getSession());
	}

	/**
	 * @see GenericDao#topResultHql(java.lang.Class, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> List<T> topResultHql(final Class<T> clazz, final int top, final String hql, final Object... args) {
		Query<T> query = getSession().createQuery(hql, clazz);
		query.setMaxResults(top);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		return query.list();
	}

	/**
	 * @see GenericDao#topResultSql(java.lang.Class, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> List<T> topResultSql(final Class<T> clazz, final int top, final String sql, final Object... args) {
		return topResultSql(clazz, null, top, sql, args);
	}

	/**
	 * @see GenericDao#topResultHql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> T topResultHql(final Class<T> clazz, final String hql, final Object... args) {
		Query<T> query = getSession().createQuery(hql, clazz);
		query.setMaxResults(1);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		return query.uniqueResult();
	}

	/**
	 * @see GenericDao#topResultHql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object topResultHql(final String hql, final Object... args) {
		Query<?> query = getSession().createQuery(hql);
		query.setMaxResults(1);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		return query.uniqueResult();
	}

	/**
	 * @see GenericDao#topResultSql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object topResultSql(final String sql, final Object... args) {
		return topResultSql((ResultTransformer) null, sql, args);
	}

	/**
	 * @see GenericDao#topResultSql(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T topResultSql(final Class<T> clazz, final String sql, final Object... args) {
		return (T) topResultSql(sql, args);
	}

	/**
	 * @see GenericDao#iterate(java.lang.Class, java.lang.String, java.lang.Object[])
	 * @deprecated use {@link #stream(Class, String, Object...)} instead
	 */
	@Deprecated
	@Override
	public <T> Iterator<T> iterate(final Class<T> clazz, final String hql, final Object... args) {
		Query<T> query = getSession().createQuery(hql, clazz);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args);
		}
		return query.iterate();
	}

	@Override
	public <T> Stream<T> stream(final Class<T> clazz, final String hql, final Object... args) {
		Query<T> query = getSession().createQuery(hql, clazz);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.stream();
	}

	/**
	 * @see GenericDao#closeIterator(java.util.Iterator)
	 */
	@Override
	public void closeIterator(final Iterator<?> iterator) {
		Hibernate.close(iterator);
	}

	/**
	 * @see GenericDao#bulkUpdateHql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int bulkUpdateHql(final String hql, final Object... args) {
		Query<?> query = getSession().createQuery(hql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * @see GenericDao#bulkUpdateSql(java.lang.String, java.lang.Object[])
	 */
	@Override
	public int bulkUpdateSql(final String sql, final Object... args) {
		NativeQuery<?> query = getSession().createSQLQuery(sql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i + 1, args[i]);
		}
		return query.executeUpdate();
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#sql(org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> sql(final ResultTransformer resultTransformer, final String sql, final Object... args) {
		logger.trace("SQL query, sql:[{}], args count:{}.", sql, args == null ? 0 : args.length);
		NativeQuery<?> query = getSession().createSQLQuery(sql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		if (resultTransformer != null) {
			query.setResultTransformer(resultTransformer);
		}
		return query.list();
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#sql(org.hibernate.transform.ResultTransformer, long, long, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> sql(final ResultTransformer resultTransformer, final long firstResult, final long maxResults, final String sql, final Object... args) {
		logger.trace("SQL query with page, sql:[{}], args count:{}, firstResult:{}, maxResults:{}", sql, args == null ? 0 : args.length, firstResult, maxResults);
		NativeQuery<?> query = getSession().createSQLQuery(sql);
		for (int i = 0; args != null && i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		query.setFirstResult((int) firstResult);
		query.setMaxResults((int) maxResults);
		if (resultTransformer != null) {
			query.setResultTransformer(resultTransformer);
		}
		return query.list();
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#sql(java.lang.Class, org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> sql(final Class<T> expectType, final ResultTransformer resultTransformer, final String sql, final Object... args) {
		return (List<T>) sql(resultTransformer, sql, args);
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#sql(java.lang.Class, org.hibernate.transform.ResultTransformer, long, long, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> sql(final Class<T> expectType, final ResultTransformer resultTransformer, final long firstResult, final long maxResults, final String sql, final Object... args) {
		return (List<T>) sql(resultTransformer, firstResult, maxResults, sql, args);
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#uniqueResultSql(org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object uniqueResultSql(final ResultTransformer resultTransformer, final String sql, final Object... args) {
		logger.debug("SQL unique query, sql:[{}], args count:{}.", sql, args.length);
		NativeQuery<?> query = getSession().createSQLQuery(sql);
		for (int i = 0; i < args.length; i++) {
			query.setParameter(i, args[i]);
		}
		if (resultTransformer != null) {
			query.setResultTransformer(resultTransformer);
		}
		return query.uniqueResult();
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#uniqueResultSql(java.lang.Class, org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T uniqueResultSql(final Class<T> expectType, final ResultTransformer resultTransformer, final String sql, final Object... args) {
		return (T) uniqueResultSql(resultTransformer, sql, args);
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#topResultSql(org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@Override
	public Object topResultSql(final ResultTransformer resultTransformer, final String sql, final Object... args) {
		NativeQuery<?> sqlQuery = getSession().createSQLQuery(sql);
		sqlQuery.setMaxResults(1);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				sqlQuery.setParameter(0, args[i]);
			}
		}
		if (resultTransformer != null) {
			sqlQuery.setResultTransformer(resultTransformer);
		}
		return sqlQuery.uniqueResult();
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#topResultSql(java.lang.Class, org.hibernate.transform.ResultTransformer, java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T> T topResultSql(final Class<T> clazz, final ResultTransformer resultTransformer, final String sql, final Object... args) {
		return (T) topResultSql(resultTransformer, sql, args);
	}

	/**
	 * @see net.lc4ever.framework.dao.GenericDao#topResultSql(java.lang.Class, org.hibernate.transform.ResultTransformer, int, java.lang.String, java.lang.Object[])
	 */
	@Override
	public <T> List<T> topResultSql(final Class<T> clazz, final ResultTransformer resultTransformer, final int top, final String sql, final Object... args) {
		NativeQuery<T> sqlQuery = getSession().createNativeQuery(sql, clazz);
		sqlQuery.setMaxResults(top);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				sqlQuery.setParameter(i, args[i]);
			}
		}
		if (resultTransformer != null) {
			sqlQuery.setResultTransformer(resultTransformer);
		}
		return sqlQuery.list();
	}

	@Override
	public void flush() {
		getSession().flush();
	}

	@Override
	public <T> List<T> named(Class<T> clazz, String name, Object... args) {
		return named(name, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> named(String name, Object... args) {
		Query<T> query = getSession().getNamedQuery(name);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		return query.list();
	}

	@Override
	public <T> List<T> named(Class<T> clazz, long firstResult, long maxResults, String name, Object... args) {
		return named(firstResult, maxResults, name, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> named(long firstResult, long maxResults, String name, Object... args) {
		Query<T> query = getSession().getNamedQuery(name);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		query.setFirstResult((int) firstResult);
		query.setMaxResults((int) maxResults);
		return query.list();
	}

	@Override
	public <T> T namedUniqueResult(Class<T> clazz, String name, Object... args) {
		return namedUniqueResult(name, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T namedUniqueResult(String name, Object... args) {
		Query<T> query = getSession().getNamedQuery(name);
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i + 1, args[i]);
			}
		}
		return query.uniqueResult();
	}

	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void update(E entity, String[] properties) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void update(Class<E> clazz, ID id, String[] properties, Object[] values) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public <E extends BaseEntity<ID>, ID extends Serializable> void updateWithout(E entity, String[] exculdeProperties) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("");
	}

	@Override
	public <T extends BaseEntity<?>> void evict(T entity) {
		getSession().evict(entity);
	}

	@Override
	public <T extends BaseEntity<?>> void refresh(T entity) {
		getSession().refresh(entity);
	}

	@Override
	public LobHelper lobHelper() {
		return getSession().getLobHelper();
	}

	@Override
	public void clear() {
		getSession().clear();
	}

	@Override
	public Cache getCache() {
		return getSession().getSessionFactory().getCache();
	}

	@Override
	public <T> List<T> hql(Class<T> clazz, long firstResult, long maxResults, String hql, Map<String, Object> params) {
		Query<T> query = getSession().createQuery(hql, clazz);
		for (Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() instanceof Collection<?>) {
				query.setParameterList(entry.getKey(), (Collection<?>) entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		query.setFirstResult((int) firstResult);
		query.setMaxResults((int) maxResults);
		return query.list();
	}

	@Override
	public <T> T uniqueResultHql(Class<T> clazz, String hql, Map<String, Object> params) {
		Query<T> query = getSession().createQuery(hql, clazz);
		for (Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() instanceof Collection<?>) {
				query.setParameterList(entry.getKey(), (Collection<?>) entry.getValue());
			} else {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query.uniqueResult();
	}

}
