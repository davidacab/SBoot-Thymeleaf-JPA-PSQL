package it.thyjpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

import it.thyjpa.model.User;

@Repository
public class UserDao {

	@PersistenceContext
	EntityManager em;
	
	@Transactional
	public void createUser(User user) {
		em.persist(user);
	}
	
	@Transactional
	public void removeUser(Long id) {
		User user=new User(id,"","");
		em.remove(em.contains(user)?user:em.merge(user));
	}
	
	@Transactional
	public void updateUser(User user) {
		em.merge(em.contains(user)?user:em.merge(user));
	}
	
	@Transactional
	public List<User> findByEmail(User user) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.where(cb.like(root.get("email"),"%"+user.getEmail()+"%"));
		return em.createQuery(cq).getResultList();
	}
	
	@Transactional
	public List<User> getAllUsers(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.select(root);
		return em.createQuery(cq).getResultList();
	}
	
	@Transactional
	public User findById(Long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.where(cb.equal(root.get("id"),id));
		return em.createQuery(cq).getSingleResult();
	}
}
