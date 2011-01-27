#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vmforce.samples.dao.SampleDogOwnerDAO;
import com.vmforce.samples.entity.SampleDog;
import com.vmforce.samples.entity.SampleBreed;
import com.vmforce.samples.entity.SampleDogOwner;

import static org.junit.Assert.*;

public class JPATest {

	private static ApplicationContext context = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("app-context.xml");
	}

	private static void deleteTestObjects() {
		
		EntityManager em = getEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		Query query = em.createQuery("delete from SampleDogOwner spo");
		query.executeUpdate();
		
		query = em.createQuery("delete from SampleDog sp");
		query.executeUpdate();
		
		tx.commit();
		em.close();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		deleteTestObjects();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	private static EntityManager getEntityManager() {
		EntityManagerFactory emFactory = context.getBean("entityManagerFactory", EntityManagerFactory.class);
		EntityManager em = emFactory.createEntityManager();
		return em;
	}
	
	@Test
	public void testJPAConnection() throws Exception {
		EntityManager em = getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		SampleDogOwner owner1 = createFirstDogOwner(em);
		SampleDogOwner owner2 = createSecondDogOwner(em);
		
		tx.commit();
		em.close();
		
		
		// *******************************
		// Load it back from the database
		EntityManager readTestEm = getEntityManager();
		EntityTransaction readTx = readTestEm.getTransaction();
		readTx.begin();
		
		SampleDogOwner dbOwner1 = readTestEm.find(SampleDogOwner.class, owner1.getId());
		SampleDogOwner dbOwner2 = readTestEm.find(SampleDogOwner.class, owner2.getId());
		
		assertTrue(allFieldsMatch(owner1, dbOwner1));
		assertTrue(allFieldsMatch(owner2, dbOwner2));
		
		assertEquals(2, dbOwner1.getDogs().size());
		assertEquals(2, dbOwner2.getDogs().size());
		
		assertTrue(hasDogNamed("Goldie", dbOwner1));
		assertTrue(hasDogNamed("Sparky", dbOwner1));
		
		assertTrue(hasDogNamed("Max", dbOwner2));
		assertTrue(hasDogNamed("Fluffy", dbOwner2));
		
		readTx.commit();
		readTestEm.close();
	}

	private boolean hasDogNamed(String dogName, SampleDogOwner dogOwner) {
		
		for (SampleDog dog : dogOwner.getDogs()) {
			if (dog.getName().equals(dogName)) {
				return true;
			}
		}
		
		return false;
	}

	private boolean allFieldsMatch(SampleDogOwner firstOwner,
			SampleDogOwner secondOwner) {
		
		if (!firstOwner.getFirstName().equals(secondOwner.getFirstName())) {
			return false;
		}
		
		if (!firstOwner.getLastName().equals(secondOwner.getLastName())) {
			return false;
		}
		
		if (!firstOwner.getPhoneNumber().equals(secondOwner.getPhoneNumber())) {
			return false;
		}
		
		if (firstOwner.getDogs().size() != secondOwner.getDogs().size()) {
			return false;
		}
		
		return true;
	}

	private SampleDogOwner createSecondDogOwner(EntityManager em) {
		SampleDogOwner dogOwner = new SampleDogOwner();
		dogOwner.setFirstName("Sally");
		dogOwner.setLastName("Salazar");
		dogOwner.setPhoneNumber("650-555-9942");
		
		SampleDog max = new SampleDog();
		max.setAge(2);
		max.setBreed(SampleBreed.GERMAN_SHEPHERD);
		max.setName("Max");
		max.setDogOwner(dogOwner);
		
		SampleDog fluffy = new SampleDog();
		fluffy.setAge(7);
		fluffy.setBreed(SampleBreed.SHITZU);
		fluffy.setName("Fluffy");
		fluffy.setDogOwner(dogOwner);
		
		dogOwner.getDogs().add(max);
		dogOwner.getDogs().add(fluffy);
		em.persist(dogOwner);
		
		return dogOwner;
	}

	private SampleDogOwner createFirstDogOwner(EntityManager em) {
		SampleDogOwner dogOwner = new SampleDogOwner();
		dogOwner.setFirstName("Johnny");
		dogOwner.setLastName("Smith");
		dogOwner.setPhoneNumber("415-555-1223");
		
		SampleDog goldie = new SampleDog();
		goldie.setAge(4);
		goldie.setBreed(SampleBreed.GOLDEN_RETRIEVER);
		goldie.setName("Goldie");
		goldie.setDogOwner(dogOwner);
		
		SampleDog sparky = new SampleDog();
		sparky.setAge(4);
		sparky.setBreed(SampleBreed.SHIBA_INU);
		sparky.setName("Sparky");
		sparky.setDogOwner(dogOwner);
		
		dogOwner.getDogs().add(goldie);
		dogOwner.getDogs().add(sparky);
		em.persist(dogOwner);
		
		return dogOwner;
	}

	@Test
	public void testDAOFind () {
		deleteTestObjects();
		
		EntityManager em = getEntityManager();
		
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		
		createFirstDogOwner(em);
		createSecondDogOwner(em);
		
		tx.commit();
		em.close();
		
		// Test retrieval with DAO
		SampleDogOwnerDAO dao = context.getBean("sampleDogOwnerDAO", SampleDogOwnerDAO.class);
		List<SampleDogOwner> dogOwners = dao.getAllDogOwners();
		assertEquals(2, dogOwners.size());
	}
	
}


