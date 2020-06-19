package com.revature.cats.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.revature.cats.exceptions.CatNotFoundException;
import com.revature.cats.models.Cat;
import com.revature.cats.repositories.CatRepository;

/**
 * This is the real cat service that uses the DB. We access the DB by autowiring in a CatRepository
 * @author Nick
 *
 */

@Service
@Primary //this makes Spring use catServiceImplementation by default for all CatService bean wiring
public class CatServiceImplementation implements CatService {
	
	@Autowired
	CatRepository catRepository;
	
	@Override
	public List<Cat> getAll() {
		// TODO Auto-generated method stub
		return catRepository.findAll();
	}

	@Override
	public Cat getById(Integer id) {
		//findById returns an Optional, let's resolve that optional here in the service layer
		Optional<Cat> cat = catRepository.findById(id);
		if(cat.isPresent()) {
			return cat.get();
		} else {
			throw new CatNotFoundException();
		}
		
	}

	@Override
	public Cat create(Cat cat) {
		cat.setCatId(0); //ignore the given ID, create new cat. There are other choices that oculd be reasonably made here
		// setting the id to 9 and using the GneratedValue annotation
		return catRepository.save(cat);
	}

	@Override
	public Cat update(Cat cat) {
		//We want to make sure the cat we're given exists before we save it to the DB
		Optional<Cat> existingCat = catRepository.findById(cat.getCatId());
		if(existingCat.isPresent()) {
			return catRepository.save(cat);
		} else {
			throw new CatNotFoundException();
		}
	}

	@Override
	public Cat createOrUpdate(Cat cat) {
		return catRepository.save(cat);
	}

	@Override
	public void delete(Integer id) {
		catRepository.deleteById(id);

	}

}
