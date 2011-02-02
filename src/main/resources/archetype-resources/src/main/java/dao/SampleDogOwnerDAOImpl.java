package com.vmforce.samples.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.vmforce.samples.entity.SampleDogOwner;

public class SampleDogOwnerDAOImpl implements SampleDogOwnerDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<SampleDogOwner> getAllDogOwners() {
		
		Query query = em.createQuery("SELECT FROM SampleDogOwner sc");
		return (List<SampleDogOwner>)query.getResultList();
	}

}
