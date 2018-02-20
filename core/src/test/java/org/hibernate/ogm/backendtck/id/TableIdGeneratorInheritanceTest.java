/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.backendtck.id;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.hibernate.ogm.utils.jpa.OgmJpaTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TableIdGeneratorInheritanceTest extends OgmJpaTestCase {


	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testTableIdGenerator() {
		EntityManagerFactory emf = getFactory();
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Truck loadedTruck = new Truck();
		em.persist( loadedTruck );
		em.getTransaction().commit();

		em.clear();

		em.getTransaction().begin();

		loadedTruck = em.find( Truck.class, loadedTruck.getId() );

		assertThat( loadedTruck.getId() ).isNotNull();
		assertThat( loadedTruck ).isNotNull();
		em.getTransaction().commit();
		em.close();
	}

//	@Override
//	protected void configure(GetterPersistenceUnitInfo info) {
//		TestHelper.enableCountersForInfinispan( info.getProperties() );
//	}

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] {
				BaseCar.class,
				Truck.class
		};
	}
}
