package com.relativerecipes.recipe.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public abstract class AbstractRepositoryIntegrationTest {
	
	@Autowired
    TestEntityManager entityManager;
	
	protected void persist(Object entity) {
		entityManager.persist(entity);
        entityManager.flush();
	}
	
	protected void update(Object entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

}
