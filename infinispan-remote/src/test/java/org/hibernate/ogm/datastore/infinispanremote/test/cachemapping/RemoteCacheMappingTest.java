/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.infinispanremote.test.cachemapping;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.ogm.datastore.infinispanremote.configuration.impl.InfinispanRemoteConfiguration;
import org.hibernate.ogm.datastore.infinispanremote.impl.InfinispanRemoteDatastoreProvider;
import org.hibernate.ogm.datastore.infinispanremote.test.initialize.DisneyGrandChild;
import org.hibernate.ogm.datastore.infinispanremote.test.initialize.DisneyGrandMother;
import org.hibernate.ogm.datastore.infinispanremote.utils.InfinispanRemoteServerRunner;
import org.hibernate.ogm.datastore.spi.DatastoreProvider;
import org.hibernate.ogm.engine.spi.OgmSessionFactoryImplementor;
import org.hibernate.ogm.utils.OgmTestCase;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.fest.assertions.Assertions.assertThat;
import static org.hibernate.ogm.utils.TestHelper.getNumberOfAssociations;
import static org.hibernate.ogm.utils.TestHelper.getNumberOfEntities;

/**
 * Test for the remote cache mapping strategy.
 *
 * @author Pratip Mondal
 */
@RunWith(InfinispanRemoteServerRunner.class)
public class RemoteCacheMappingTest extends OgmTestCase {

	@Test
	public void shouldUseCachePerTable() {
		DisneyGrandMother grandMother = new DisneyGrandMother( "Grandma Duck" );

		// insert entity with embedded collection
		try ( Session session = openSession() ) {
			Transaction tx = session.beginTransaction();
			DisneyGrandChild donald = new DisneyGrandChild();
			donald.setName( "Donald Duck" );
			DisneyGrandChild gus = new DisneyGrandChild();
			gus.setName( "Gus Goose" );

			grandMother.getGrandChildren().add( gus );
			grandMother.getGrandChildren().add( donald );

			session.persist( grandMother );
			tx.commit();
		}

		//Check whether the cache is created properly
		OgmSessionFactoryImplementor sessionFactory = getSessionFactory();
		ServiceRegistryImplementor serviceRegistry = sessionFactory.getServiceRegistry();
		InfinispanRemoteDatastoreProvider service = (InfinispanRemoteDatastoreProvider) serviceRegistry.getService( DatastoreProvider.class );
		InfinispanRemoteConfiguration conf = new InfinispanRemoteConfiguration();

		assertThat( service.getMappedCacheNames().size() ).isEqualTo( 2 );
		RemoteCacheManager remoteCacheManager = service.getRemoteCacheManager();
		RemoteCache<Object, Object> parentCache = remoteCacheManager.getCache( "DisneyGrandMother" );
		RemoteCache<Object, Object> childCache = remoteCacheManager.getCache( "DisneyGrandMother_grandChildren" );
		assertThat( parentCache ).isNotEmpty();
		assertThat( childCache ).isNotEmpty();

		assertThat( getNumberOfEntities( sessionFactory ) ).isEqualTo( 1 );
		assertThat( getNumberOfAssociations( sessionFactory ) ).isEqualTo( 1 );
	}

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class<?>[]{
				DisneyGrandMother.class,
		};
	}
}
