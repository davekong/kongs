package com.kongs.common;

import java.io.Serializable;
import java.util.List;

public interface BaseDao {

	/**
	 * 1��hibernate ��ʵ�� ����
	 */

	// 1.2 ��ʵ�� ��ѯ ����
	public Object get(Class clazz,String id);
	public Object load(Class clazz,String id);

	// 1.2 ��ʵ�� ����|����|ɾ�� ����
	public Serializable save(Object entity);
	public void update(Object entity);
	public void delete(Object entity);
	public void delete(String id);
	public void saveOrUpdate(Object entity);
	public Object merge(Object entity);
	
	
	/**
	 * 2��hibernate ��ʵ�� ����|����|ɾ�� ����
	 */
	// 2.1 ��ʵ�� ����|����|ɾ�� ����
	public Integer saves(final List entitys);
	public Integer updates(final List entitys);
	public Integer deletes(final List entitys);
	
	// 2.2 ��ʵ�� ��ѯ ���� ----hibernate api��session ���� hqlQuery ����
	public Integer hqlIUD(String hql,List list);
	public List hqlList(String hql, List list);
	public Page hqlPage(String hql, Integer pageNo, Integer pageSize, List list);

	// 2.3 ��ʵ�� ��ѯ ���� ----hibernate api��session ���� sqlQuery ����
	public Integer sqlIUD(final String sql, final List list);
	public List sqlList(final String sql, final List list);
	public Page sqlPage(final String sql,final Integer pageNo, final Integer pageSize, final List list);
	
	
	//2.4����ֹ��¼���ѯ
	public List hqlListBySrEr(String hql, Integer startResult, Integer endResult, List list);
	public List sqlListBySrEr(String sql, Integer startResult, Integer endResult, List list);
}
