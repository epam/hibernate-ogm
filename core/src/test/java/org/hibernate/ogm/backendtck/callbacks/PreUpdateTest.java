/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.backendtck.callbacks;

import javax.persistence.EntityManager;

import org.hibernate.ogm.utils.jpa.OgmJpaTestCase;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

public class PreUpdateTest extends OgmJpaTestCase {

	/**
	 * Update an entity which uses a @PreUpdate annotated method
	 * to set boolean field to 'true'.
	 */
	@Test
	public void testFieldSetInPreUpdate() {
		final String INITIAL = "initial";
		final String UPDATED = "updated";

		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();

		PreUpdatableBus bus = new PreUpdatableBus();
		bus.setId( 1 );
		bus.setField( INITIAL );

		em.persist( bus );
		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		bus = em.find( PreUpdatableBus.class, bus.getId() );

		assertNotNull( bus );
		assertEquals( bus.getField(), INITIAL );
		assertFalse( bus.isPreUpdated() );

		bus.setField( UPDATED );
		em.getTransaction().commit();

		assertTrue( bus.isPreUpdated() );

		em.clear();

		assertTrue( bus.isPreUpdated() );

		em.getTransaction().begin();

		bus = em.find( PreUpdatableBus.class, bus.getId() );
		assertNotNull( bus );
		assertEquals( bus.getField(), UPDATED );
		// @PreUpdate executed before the database UPDATE operation
		assertTrue( bus.isPreUpdated() );

		em.getTransaction().commit();

		em.close();
	}

	@Test
	public void testFieldSetInPreUpdateByListener() {
		final String INITIAL = "initial";
		final String UPDATED = "updated";

		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();

		PreUpdatableBus bus = new PreUpdatableBus();
		bus.setId( 1 );
		bus.setField( INITIAL );

		em.persist( bus );
		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		bus = em.find( PreUpdatableBus.class, bus.getId() );

		assertNotNull( bus );
		assertEquals( bus.getField(), INITIAL );
		assertFalse( bus.isPreUpdatedByListener() );

		bus.setField( UPDATED );
		em.getTransaction().commit();

		assertTrue( bus.isPreUpdatedByListener() );

		em.clear();

		assertTrue( bus.isPreUpdatedByListener() );

		em.getTransaction().begin();

		bus = em.find( PreUpdatableBus.class, bus.getId() );
		assertNotNull( bus );
		assertEquals( bus.getField(), UPDATED );
		assertTrue( bus.isPreUpdatedByListener() );

		em.getTransaction().commit();

		em.close();
	}

	@After
	@Override
	public void removeEntities() throws Exception {
		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();
		em.remove( em.find( PreUpdatableBus.class, 1 ) );
		em.getTransaction().commit();
		em.close();
	}


	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] { PreUpdatableBus.class };
	}
}
