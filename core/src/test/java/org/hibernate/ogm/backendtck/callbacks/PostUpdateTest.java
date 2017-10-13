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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class PostUpdateTest extends OgmJpaTestCase {

	/**
	 * Update an entity which uses a @PostUpdate annotated method
	 * to set boolean field to 'true'.
	 */
	@Test
	public void testFieldSetInPostUpdate() {
		final String INITIAL = "initial";
		final String UPDATED = "updated";

		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();

		PostUpdatableBus bus = new PostUpdatableBus();
		bus.setId( 1 );
		bus.setField( INITIAL );

		em.persist( bus );
		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		bus = em.find( PostUpdatableBus.class, bus.getId() );

		assertNotNull( bus );
		assertEquals( bus.getField(), INITIAL );
		assertFalse( bus.isPostUpdated() );

		bus.setField( UPDATED );
		em.getTransaction().commit();

		assertTrue( bus.isPostUpdated() );

		em.clear();

		em.getTransaction().begin();

		bus = em.find( PostUpdatableBus.class, bus.getId() );
		assertNotNull( bus );
		assertEquals( bus.getField(), UPDATED );
		// @PostUpdate executed after the database UPDATE operation
		assertFalse( bus.isPostUpdated() );

		em.getTransaction().commit();

		em.close();
	}

	@Test
	public void testFieldSetInPostUpdateByListener() {
		final String INITIAL = "initial";
		final String UPDATED = "updated";

		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();

		PostUpdatableBus bus = new PostUpdatableBus();
		bus.setId( 1 );
		bus.setField( INITIAL );

		em.persist( bus );
		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		bus = em.find( PostUpdatableBus.class, bus.getId() );

		assertNotNull( bus );
		assertEquals( bus.getField(), INITIAL );
		assertFalse( bus.isPostUpdatedByListener() );

		bus.setField( UPDATED );
		em.getTransaction().commit();

		assertTrue( bus.isPostUpdatedByListener() );

		em.clear();

		em.getTransaction().begin();

		bus = em.find( PostUpdatableBus.class, bus.getId() );
		assertNotNull( bus );
		assertEquals( bus.getField(), UPDATED );
		assertFalse( bus.isPostUpdatedByListener() );

		em.getTransaction().commit();

		em.close();
	}

	@After
	@Override
	public void removeEntities() throws Exception {
		EntityManager em = getFactory().createEntityManager();
		em.getTransaction().begin();
		em.remove( em.find( PostUpdatableBus.class, 1 ) );
		em.getTransaction().commit();
		em.close();
	}


	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[] { PostUpdatableBus.class };
	}
}
