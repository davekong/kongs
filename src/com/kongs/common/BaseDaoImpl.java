package com.kongs.common;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.lob.SerializableClob;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;


/**
 * 
 * @author cui
 * ��֪��
 * 1��������ȫʹ��session���в�������ֱ��ʹ��connection���в�����
 * 2������ʹ��query�ĵط���������hibernateTemplate�Ļص�����ͨ��session����query��������
 * 
 * 3����֪��
 *	hql��֧��select�Ӳ�ѯ��from�Ӳ�ѯ����֧��union ��on �ȹؼ��֡�
 *  select�Ӳ�ѯ�� select t2.name ,(select t1.age from table1 t1 where 1=1) from table2 t2 where 1=1��
 *  from �Ӳ�ѯ:  select t2.name from (select t1.name from table2 where 1=1)
 *  
 *  ��֧��in�Ӳ�ѯ
 *  �������ǿɽ�from �Ӳ�ѯת��Ϊin�Ӳ�ѯ
 *  select t1.name from table1 t1 where 1=1 and  t1.id in(select t2.id from table2 where 1=1)
 *  
 *  
 *
 */

public class BaseDaoImpl extends HibernateDaoSupport implements BaseDao {

	/**
	 * 1��hibernate ��ʵ�� ����
	 */
	
	// 1.1 ��ʵ�� ��ѯ ����
	public Object get(Class clazz ,String id) {// get��ʽ���ص�ʵ��
		if (id == null) {
			return null;
		}
		return  getHibernateTemplate().get(clazz, id);
	}
	public Object load(Class clazz ,String id) {// load��ʽ���ص�ʵ��
		if (id == null) {
			return null;
		}
		return getHibernateTemplate().load(clazz, id);
	}

	//1.2 ��ʵ�� ����|����|ɾ�� ����
	public Serializable save(Object entity) {// ���浥ʵ��
		 return getHibernateTemplate().save(entity);
	}
	public void update(Object entity) {// ���µ�ʵ��
		getHibernateTemplate().update(entity);
	}
	public void delete(Object entity) {// ɾ��ʵ��
		getHibernateTemplate().delete(entity);
	}
	public void delete(String id) {// ɾ��ʵ��
		getHibernateTemplate().delete(id);
	}
	public void saveOrUpdate(Object entity) {// �������µ�ʵ��
		getHibernateTemplate().saveOrUpdate(entity);
	}
	public  Object merge(Object entity) {// ���µ�ʵ�壬���˵����....
		return getHibernateTemplate().merge(entity);
	}
	
	
	
	/**
	 * 2��hibernate ��ʵ�� ����|����|ɾ�� ����
	 */
	// 2.1 ��ʵ�� ����|����|ɾ�� ����
	public Integer saves(final List entitys) {// ����ʵ�弯��
		return(Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for(int i=0;i<entitys.size();i++){
					 session.save(entitys.get(i));
					 if(i%50==0){ 
						 session.flush();
						 session.clear();
					 }
				}
				return entitys.size();
			}
		});
	}
	public Integer updates(final List entitys) {// ����ʵ�弯,���鲻Ҫʹ���������ʹ��hql���и���
		return (Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for(int i=0;i<entitys.size();i++){
					 session.update(entitys.get(i));
					 if(i%50==0){ 
						 session.flush();
						 session.clear();
					 }
				}
				return entitys.size();
			}
		});
	}
	public Integer deletes(final List entitys) {// ɾ��ʵ�弯�����鲻Ҫʹ���������ʹ��hql����ɾ��
		return(Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				for(int i=0;i<entitys.size();i++){
					 session.delete(entitys.get(i));
					 if(i%50==0){ 
						 session.flush();
						 session.clear();
					 }
				}
				return entitys.size();
			}
		});
	}

	
	//2.2 ��ʵ��  ��ѯ ����  ----hibernate api��session ���� hqlQuery ����
	public Integer hqlIUD(String hql,List list){
		return IUDByHql(hql,list);
	}
	
	public List hqlList(String hql, List list) {// ռλ��hql��ѯ��list�з���ռλ���ֵ��ͬʱҲ�����ڲ���ռλ��Ĳ�ѯ
		return (List)queryByHql(0,0,hql,list);
	}
	public Page hqlPage(String hql, Integer pageNo, Integer pageSize,// hql��ҳ��ѯ��ͬʱҲ�����ڲ���ռλ��Ĳ�ѯ
			List list) {
		
		// totalCount:��������ļ�¼��
		// pageSize:������� ÿҳ��ʾ������
		// pageCount:������� һ������ҳ
		// pageNo:������� �ڼ�ҳ
		int recodeCount = hqlRecodeCount(hql, list);
		Page page = new Page();
		if(recodeCount==0){//����ѯ���Ϊ�գ���ô
			page.setRecodeCount(0);//��¼��Ϊ0
			page.setPageCount(0);//ҳ��Ϊ0
			page.setPageNo(0);//��ǰҳ��Ϊ0
			page.setPageSize(pageSize);//ÿҳ��ʾΪ0��
			page.setList(null);
			return page;
		}//����--->
		
		if (pageNo < 1) {
			pageNo = 1;
		}
		
		Integer pageCount = 0;
		if (recodeCount % pageSize > 0) {
			pageCount = recodeCount / pageSize + 1;
		} else {
			pageCount = recodeCount / pageSize;
		}
		page.setPageCount(pageCount);
		page.setPageSize(pageSize);
		
		if (pageNo > pageCount) {
			pageNo = pageCount;
		}
		page.setPageNo(pageNo);
		
		
		List result=queryByHql((pageNo - 1) * pageSize,pageSize,hql,list);
		
		page.setRecodeCount(recodeCount);
		page.setPageCount(pageCount);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setList(result);

		return page;
	}
	

	//2.3 ��ʵ��  ��ѯ ����   ----hibernate api��session ���� sqlQuery ����
	public Integer sqlIUD(final String sql, final List list) {//list�з���ռλ��ֵ
		return IUDBySql(sql,list);
	}
	public List sqlList(final String sql, final List list) {//list�з���ռλ��ֵ
		return queryBySql(0,0,sql,list);
	}
	
	public Page sqlPage(final String sql, final Integer pageNo, final Integer pageSize,// ��ҳ��ѯ
			final List list) {
				
		// totalCount:��������ļ�¼��
		// pageSize:������� ÿҳ��ʾ������
		// pageCount:������� һ������ҳ
		// pageNo:������� �ڼ�ҳ
		int recodeCount = sqlRecodeCount(sql, list);

		Page page = new Page();

		if (recodeCount < 1) {// ����ѯ���Ϊ�գ���ô
			page.setRecodeCount(0);// ��¼��Ϊ0
			page.setPageCount(0);// ҳ��Ϊ0
			page.setPageNo(0);// ��ǰҳ��Ϊ0
			page.setPageSize(pageSize);// ÿҳ��ʾΪ0��
			page.setList(null);
		}// ����--->

		Integer pageCount = 0;

		if (recodeCount % pageSize > 0) {
			pageCount = recodeCount / pageSize + 1;
		} else {
			pageCount = recodeCount / pageSize;
		}
		page.setPageCount(pageCount);
		page.setPageSize(pageSize);
		
		int no = pageNo;
		if (no > pageCount) {
			no = pageCount;
		}
		page.setPageNo(no);
		
		
		List result = queryBySql((no - 1) * pageSize, pageSize, sql,list);
		
		page.setList(result);
		page.setRecodeCount(recodeCount);
		return page;
		
	}
	
	public List hqlListBySrEr(String hql, Integer startResult, Integer endResult, List list){
		if(1>startResult||endResult<startResult){
			throw new RuntimeException("the value of startResult/endResult is error! ");
		}
		int firstResult=startResult-1;
		int maxResults=endResult-startResult+1;
		
		return queryByHql(firstResult,maxResults,hql,list);
	}
	public List sqlListBySrEr(String sql, Integer startResult, Integer endResult, List list){
		if(1>startResult||endResult<startResult){
			throw new RuntimeException("the value of startResult/endResult is error! ");
		}
		int firstResult=startResult-1;
		int maxResults=endResult-startResult+1;
		
		return queryBySql(firstResult,maxResults,sql,list);
	}
	
	
	
	/**
	 * ����daoע��sessionFactory
	 */
	@Resource  
	public void setSessionFacotry(SessionFactory sessionFacotry) {  
        super.setSessionFactory(sessionFacotry);  
    }  
	
	/**
	 * ������ʹ��
	 */
	protected List handleResult(List l,String[]columnNames){
		if(null==l) return new ArrayList();
		
		List result=new ArrayList();
		for(int i=0;i<l.size();i++){
			Object []o=(Object[])l.get(i);
			Map m=new HashMap();
			for(int j=0;j<o.length;j++){
				
				//�����clob����ת��Ϊstring����
				if(o[j]!=null && o[j].getClass().getName().trim().equals("org.hibernate.lob.SerializableClob")){
					m.put(columnNames[j], getClob((SerializableClob)o[j]));
				}
				else{
					m.put(columnNames[j], o[j]==null?"":o[j]);
				}
			}
			result.add(m);
		}
		return result;
	}
	/**
	 * clob����ת��Ϊstring
	 */
	private String getClob(SerializableClob c){
		Reader reader;
		StringBuffer sb = new StringBuffer();
		try {
			reader = c.getCharacterStream();
			BufferedReader br = new BufferedReader(reader);
			String temp = null;
			while ((temp=br.readLine()) != null) {
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return sb.toString();
	}
	
	/** *****************************����************************** */
	//ֱ��ʹ��hibernate api��session ���� hqlQuery��ɾ��
	private Integer IUDByHql(final String hql, final List list){
		return(Integer) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = createHqlQuery(session,hql, list);
				Integer result = query.executeUpdate();
				return result;
			}
		});
	}
	
	//ֱ��ʹ��hibernate api��session ���� sqlQuery ��ɾ��
	private Integer IUDBySql(final String hql, final List list){
		return (Integer)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = createSqlQuery(session,hql, list);
				Integer result = query.executeUpdate();
				return result;
			}
		});
	}
	//ֱ��ʹ��hibernate api��session ���� hqlQuery ��ѯ
	private List queryByHql(final int fistResult,final int maxResults,final String hql, final List list){
		return(List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = createHqlQuery(session,hql, list);
				
				if(0<fistResult){
					query.setFirstResult(fistResult);
				}
				if(0<maxResults){
					query.setMaxResults(maxResults);
				}
				
				List result = query.list();
				return result;
			}
		});
	}
	//ֱ��ʹ��hibernate api��session ����sqlQuery ��ѯ
	private List queryBySql(final int fistResult,final int maxResults,final String sql, final List list){
		return (List)getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query =createSqlQuery(session,sql, list);
				
				if(0<fistResult){
					query.setFirstResult(fistResult);
				}
				if(0<maxResults){
					query.setMaxResults(maxResults);
				}
				List result = query.list();
				return result;
			}
		});
	}
	
	
	/**
	 * ����query
	 */
	private Query createHqlQuery(Session session,String hql, List list) {// ���� hql query
		Query query = session.createQuery(hql);
		if(list==null){
			return query;
		}
		for (int i = 0; i < list.size(); i++) {
			query.setParameter(i, list.get(i));
		}
		return query;
	}
	private Query createSqlQuery( Session session,String sql, List list) {// ���� sql  query
		Query query = session.createSQLQuery(sql);
		if(list==null){
			return query;
		}
		for (int i = 0; i < list.size(); i++) {
			query.setParameter(i, list.get(i));
			
		}
		return query;
	}
	
	/**
	 * ��ѯ��¼��
	 */
	private Integer hqlRecodeCount(String hql, List list) {// ��ô��ѯ�ļ�¼��
		List countlist = hqlList(handleHql(hql), list);
		int recodeCount = Integer.parseInt(countlist.get(0).toString());
		return recodeCount;
	}
	private Integer sqlRecodeCount(String sql, List list) {// ��ô��ѯ�ļ�¼��
		//��hql or sql��һ��from֮ǰ������滻Ϊ��select count (*)������ѯ��¼���
		List countlist = sqlList(handleSql(sql), list);
		int recodeCount = Integer.parseInt(countlist.get(0).toString());
		return recodeCount;
	}
	private String handleHql(String hql){
		int beginPos = hql. toLowerCase().indexOf("from");
		return "select count(*) "+hql.substring(beginPos);
	}
	private String handleSql(String sql) {
		return sql="select count(*) from ( "+sql+" ) full";
	}
	private static String removeOrders(String hsql) {// ȥ��hql|sql������
		Pattern p = Pattern.compile("^(order)",
				Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hsql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public static void main(String args[]) {
		/*
		Class c = TSysUser.class;
		Object obj = null;
		try {
			obj = c.newInstance();
			Method m = c.getMethod("setUserid", new Class[] { String.class });
			m.invoke(obj, new Object[] { "12345678" });
		} catch (Exception e) {

		}

		TSysUser t = (TSysUser) obj;
		System.out.println(t.getUserid());*/

		//new BaseDaoImp().executeSqlIterator("");//����public Iterator executeSqlIterator(final String sql, final Object... values) 
		//new BaseDaoImp().executeSqlIterator("",null);//����
		String sql="select * from (select * from t_demo_demo a where 1=1 order by a.name )b where 1=1 order by  b.name";
		
		System.out.println(removeOrders(sql));
		
	}
}
