package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.util.TarefaUtil;

public class DaoGeneric<E> {
	
	// todos os posts devem ficar aqui
	public void salvar(E entidade) {
		EntityManager entityManager = TarefaUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		entityManager.persist(entidade);
		
		entityTransaction.commit();
		entityManager.close();
	}
	// fechar os posts
	
	// todos os gets devem ficar aqui
	public List<E> getList(Class<E> entidade){
		EntityManager entityManager = TarefaUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		@SuppressWarnings("unchecked")
		List<E> retorno = entityManager.createQuery("from " + entidade.getName()).getResultList();
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
	}
	
	// fechar os gets
	
	// todos os delets devem ficar aqui
	public void deleteById(E entidade) {
		EntityManager entityManager = TarefaUtil.getEntityManager();
		EntityTransaction entityTransaction =  entityManager.getTransaction();
		entityTransaction.begin();
		
		Object id = TarefaUtil.getPrimaryKey(entidade);
		
		entityManager.createQuery("delete from " + entidade.getClass().getCanonicalName() + " where id = " + id).executeUpdate();
		
		entityTransaction.commit();
		entityManager.close();
	}
	// fechar os delests
	
	// todos os puts devem ficar aqui
	public E merge(E entidade) {
		EntityManager entityManager = TarefaUtil.getEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
		E retorno = entityManager.merge(entidade);
		
		entityTransaction.commit();
		entityManager.close();
		
		return retorno;
	}
	// fechar os puts
}
