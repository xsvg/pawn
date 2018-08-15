package cn.cnplay.demo.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.Transformers;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class AbstractService {

	@PersistenceContext protected EntityManager entityManager;
	
	public <T> void insert(T t){
		entityManager.persist(t);
	}
	
	public <T> void update(T t){
		entityManager.merge(t);
	}
	
	public <T> T get(Class<T> clazz, Serializable id){
		return entityManager.find(clazz, id);
	}
	
	public <T> List<T> listAll(Class<T> clazz){
		StringBuilder builder  = new StringBuilder("from ");
		builder.append(clazz.getSimpleName());
		Query query = entityManager.createQuery(builder.toString());
		return (List<T>)query.getResultList();
	}
	
	/**
	 * 通过hql语句进行查询
	 * @param hql 查询语句
	 * @param params 查询的参数
	 * @return 如果hql是类似" from Entity *** "则返回的是Entity列表,如果from前指定了列名返回是Object[]数组列表
	 */
	public  List<?> list(String hql,List<Object> params){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return query.getResultList();
	}
	
	public Long count(String hql,List<Object> params){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return (Long)query.getSingleResult();
	}
	
	/**
	 * 请参考  {@link AbstractService#list(String, List)} 
	 * 
	 */
	public  List<?> list(String hql,List<Object> params,int page,int pageSize){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(page*pageSize);
		return query.getResultList();
	}
	
	/**
	 * 查询字段需命名别名成对应映射实体字段的名称,"select user.id as id,user.username as username,user.name as name from User user"
	 * @param 返回的结果类型
	 * @param 查询hql语句
	 * @param 查询条件
	 * @return
	 */
	public <T> List<T> list(Class<T> clazz,String hql,List<Object> params){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(clazz));
		return query.getResultList();
	}
	/**
	 * 请参考  {@link AbstractService#list(Class, String, List) } 
	 */
	public <T>  List<T> list(Class<T> clazz,String hql,List<Object> params,int page,int pageSize){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(page*pageSize);
		query.unwrap(org.hibernate.Query.class).setResultTransformer(Transformers.aliasToBean(clazz));
		return query.getResultList();
	}
	
	public int executeUpdate(String hql){
		Query query = entityManager.createQuery(hql);
		return query.executeUpdate();
	}
	
	public int executeUpdate(String hql,List<Object> params){
		Query query = entityManager.createQuery(hql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return query.executeUpdate();
	}
	
	public Long countSql(String sql,List<Object> params){
		Query query = entityManager.createNativeQuery(sql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return (Long)query.getSingleResult();
	}
	
	/**
	 * 通过原生SQL进行查询,返回结果请参考  {@link AbstractService#list(Class, String, List) } 
	 */
	public <T> List<T> listBySql(Class<T> clazz,String sql,List<Object> params){
		Query query = entityManager.createNativeQuery(sql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
		return query.getResultList();
	}
	
	/**
	 * 通过原生SQL进行查询，请参考  {@link AbstractService#list(Class, String, List) } 
	 */
	public <T>  List<T> listBySql(Class<T> clazz,String sql,List<Object> params,int page,int pageSize){
		Query query = entityManager.createNativeQuery(sql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(page*pageSize);
		query.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
		return query.getResultList();
	}
	
	public int executeSql(String sql){
		Query query = entityManager.createNativeQuery(sql);
		return query.executeUpdate();
	}
	
	public int executeSql(String sql,List<Object> params){
		Query query = entityManager.createNativeQuery(sql);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return query.executeUpdate();
	}
}
