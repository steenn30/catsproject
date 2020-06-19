package com.revature.cats.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cats.models.Cat;
import com.revature.cats.models.User;
import com.revature.cats.repositories.CatRepository;
import com.revature.cats.repositories.UserRepository;

@RestController
public class TestController {
	@Autowired
	CatRepository catRepository;
	@Autowired
	UserRepository userRepository;
	
	// To get the underlying Session from Spring, autowire in an EntityManager
	@Autowired
	EntityManager em;
	
	
	@GetMapping("/testrepo")
	public Object testRepoResult() {
		return catRepository.findByName("princess");
	}
	
	@GetMapping("/hbsession")
	public String hbSessionDemo() {
		String out = "";
		Session session = em.unwrap(Session.class);
		// being DB transaction, good thing to start with.
		session.beginTransaction();
		Cat fluffy = session.get(Cat.class, 1);
		fluffy.setCuteness(69.69);
		
		Cat lazyFluffy= session.load(Cat.class, 1);
		
		// We can add new Cat objects to the DB with save and persists.
		// save takes place immediately, persist makes the object persistent (it will update eventually, 
		// but its part of the session cache as normal
		
//		Cat newCat = Cat(null, "newhbcat", 33.3);
		User user = session.get(User.class,  2);
//		newCat.setOwner(user);
		
		//		session.save(newCat);
		
		// Some fancier ways to retrieve data from the DB: Criteria and Query
		// Note these are Hibernate 4 so you'll see them in the wild but they're deprecated in Hibernate 5
		
		//Query is for HQL queries, which uses a query language like SQL but that deals with Objects
		//Criteria is a programmative, type-safe way of specifying queries to the DB.
		// You should know what these are, but in practice we'll use repository CRUD methods, and HQL or SQL queries
		// HQL is a benefit over SQL becuase it works with any DB
		Set<String> catNames = new HashSet<String>();
		catNames.add("fluffy");
		catNames.add("whiskers");
		catNames.add("kibo");
		Criteria c = session.createCriteria(Cat.class)
				.add(Restrictions.in("name", catNames ));
		List<Cat> catsMeetingCriteria = c.list();
		out+= catsMeetingCriteria.toString();
		
		//HQL query, parameters start with :
		org.hibernate.Query<Cat> q = session.createQuery("from Cat");
	
		// commit DB transaction
		session.getTransaction().commit();
		out+=lazyFluffy.toString();
		session.evict(lazyFluffy);
		return out;
		
		//just know what a Query and Criteria is. Hibernate and Criteria to retrieve objects based on conditions.
	}
	// know what transient, persistant, Session are. lazy eager loading.
}
