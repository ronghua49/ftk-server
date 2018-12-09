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
package net.lc4ever.framework.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.hibernate.Cache;
import org.hibernate.LobHelper;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate5.HibernateCallback;

import net.lc4ever.framework.domain.BaseEntity;

/**
 * 通用Data Access Object.
 *
 * $Revision:$
 * @author <a href="mailto:apeidou@gmail.com">Q-Wang</a>
 */
public interface GenericDao {

	<E extends BaseEntity<ID>, ID extends Serializable> List<E> list(Class<E> clazz);

	<E extends BaseEntity<ID>, ID extends Serializable> E get(Class<E> clazz, ID id);

	<E extends BaseEntity<ID>, ID extends Serializable> void delete(E entity);

	<E extends BaseEntity<ID>, ID extends Serializable> ID save(E entity);

	<E extends BaseEntity<ID>, ID extends Serializable> void update(E entity);

	<E extends BaseEntity<ID>, ID extends Serializable> void saveOrUpdate(E entity);

	<E extends BaseEntity<ID>, ID extends Serializable> long count(Class<E> clazz);

	<E extends BaseEntity<ID>, ID extends Serializable> void update(E entity, String[] properties);

	<E extends BaseEntity<ID>, ID extends Serializable> void update(Class<E> clazz, ID id, String[] properties, Object[] values);

	<E extends BaseEntity<ID>, ID extends Serializable> void updateWithout(E entity, String[] exculdeProperties);

	//	public <T extends Serializable> Pager<T> page(Pager<T> pager);

	List<?> hql(String hql, Object... args);

	List<?> hql(long firstResult, long maxResults, String hql, Object... args);

	<T> List<T> hql(Class<T> expectType, String hql, Object... args);

	<T> List<T> hql(Class<T> expectType, long firstResult, long maxResults, String hql, Object... args);

	List<?> sql(final String sql, final Object... args);

	List<?> sql(long firstResult, long maxResults, String sql, Object... args);

	<T> List<T> sql(Class<T> expectType, final String sql, final Object... args);

	<T> List<T> sql(Class<T> expectType, long firstResult, long maxResults, String sql, Object... args);

	Object uniqueResultHql(String hql, Object... args);

	<T> T uniqueResultHql(Class<T> expectType, String hql, Object... args);

	Object uniqueResultSql(final String sql, final Object... args);

	<T> T uniqueResultSql(Class<T> expectType, final String sql, final Object... args);

	<E extends BaseEntity<ID>, ID extends Serializable> E uniqueResultByProperties(final Class<E> clazz, final String[] properties, final Object[] args);

	<E extends BaseEntity<ID>, ID extends Serializable> E uniqueResultByProperty(final Class<E> clazz, final String property, final Object arg);

	<E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperties(final Class<E> clazz, final String[] properties, final Object[] args, final Order... orders);

	<E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperties(final Class<E> clazz, final long firstResult, final long maxResults, final String[] properties, final Object[] args, final Order... orders);

	<E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperty(final Class<E> clazz, final String property, final Object arg, final Order... orders);

	<E extends BaseEntity<ID>, ID extends Serializable> List<E> queryByProperty(final Class<E> clazz, final long firstResult, final long maxResults, final String property, final Object arg, final Order... orders);

	List<?> criteria(DetachedCriteria criteria);

	<T> T callback(HibernateCallback<T> callback);

	<T> T topResultHql(Class<T> clazz, final String hql, final Object... args);

	Object topResultHql(final String hql, final Object... args);

	Object topResultSql(final String sql, final Object... args);

	<T> T topResultSql(Class<T> clazz, final String sql, final Object... args);

	<T> List<T> topResultHql(Class<T> clazz, final int top, final String hql, final Object... args);

	<T> List<T> topResultSql(Class<T> clazz, final int top, final String sql, final Object... args);

	@Deprecated
	<T> Iterator<T> iterate(Class<T> clazz, final String hql, final Object... args);

	<T> Stream<T> stream(final Class<T> clazz, final String hql, final Object... args);

	void closeIterator(Iterator<?> iterator);

	int bulkUpdateHql(String hql, Object... args);

	int bulkUpdateSql(final String sql, final Object... args);

	List<?> sql(final ResultTransformer resultTransformer, final String sql, final Object... args);

	List<?> sql(final ResultTransformer resultTransformer, final long firstResult, final long maxResults, final String sql, final Object... args);

	<T> List<T> sql(final Class<T> expectType, final ResultTransformer resultTransformer, final String sql, final Object... args);

	<T> List<T> sql(final Class<T> expectType, final ResultTransformer resultTransformer, final long firstResult, final long maxResults, final String sql, final Object... args);

	Object uniqueResultSql(final ResultTransformer resultTransformer, final String sql, final Object... args);

	<T> T uniqueResultSql(final Class<T> expectType, final ResultTransformer resultTransformer, final String sql, final Object... args);

	Object topResultSql(final ResultTransformer resultTransformer, final String sql, final Object... args);

	<T> T topResultSql(final Class<T> clazz, final ResultTransformer resultTransformer, final String sql, final Object... args);

	<T> List<T> topResultSql(final Class<T> clazz, final ResultTransformer resultTransformer, final int top, final String sql, final Object... args);

	void flush();

	void clear();

	Cache getCache();

	Session getSession();

	LobHelper lobHelper();

	<T> List<T> named(Class<T> clazz, String name, Object... args);

	<T> List<T> named(String name, Object... args);

	<T> List<T> named(Class<T> clazz, long firstResult, long maxResults, String name, Object... args);

	<T> List<T> named(long firstResult, long maxResults, String name, Object... args);

	<T> T namedUniqueResult(Class<T> clazz, String name, Object... args);

	<T> T namedUniqueResult(String name, Object... args);

	<T extends BaseEntity<?>> void evict(T entity);

	<T extends BaseEntity<?>> void refresh(T entity);

	<T> List<T> hql(Class<T> clazz, long firstResult, long maxResults, String hql, Map<String, Object> params);

	<T> T uniqueResultHql(Class<T> clazz, String hql, Map<String, Object> params);
}
