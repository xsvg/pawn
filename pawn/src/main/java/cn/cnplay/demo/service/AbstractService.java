package cn.cnplay.demo.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public abstract class AbstractService {

	@Autowired protected EntityManager entityManager;
	
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
	
	public <T> List<T> list(Class<T> clazz,String hql,List<Object> params){
		Query query = entityManager.createQuery(hql,clazz);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return query.getResultList();
	}
	
	public <T>  List<T> list(Class<T> clazz,String hql,List<Object> params,int page,int pageSize){
		Query query = entityManager.createQuery(hql,clazz);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(page*pageSize);
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
	
	public <T> List<T> listBySql(Class<T> clazz,String sql,List<Object> params){
		Query query = entityManager.createNativeQuery(sql,clazz);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		return query.getResultList();
	}
	
	public <T>  List<T> listBySql(Class<T> clazz,String sql,List<Object> params,int page,int pageSize){
		Query query = entityManager.createNativeQuery(sql,clazz);
		int i=1;
		for(Object param:params){
			query.setParameter(i++, param);
		}
		query.setFirstResult((page-1)*pageSize);
		query.setMaxResults(page*pageSize);
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
