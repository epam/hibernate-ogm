/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.infinispanremote.test.cache;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.ogm.cfg.OgmProperties;
import org.hibernate.ogm.datastore.infinispanremote.InfinispanRemoteProperties;
import org.hibernate.ogm.datastore.infinispanremote.schema.spi.MapSchemaCapture;
import org.hibernate.ogm.datastore.infinispanremote.utils.RemoteHotRodServerRule;
import org.hibernate.ogm.utils.TestHelper;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test of startup with entity with not defined cache template
 */
public class CacheNotDefinedTest {

	@ClassRule
	public static final RemoteHotRodServerRule hotRodServer = new RemoteHotRodServerRule();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void noTemplateTest() {
		thrown.expect( HibernateException.class );
		thrown.expectMessage( "OGM001717:" );
		Map<String, Object> settings = settings();
		MapSchemaCapture schemaCapture = new MapSchemaCapture();
		settings.put( InfinispanRemoteProperties.SCHEMA_CAPTURE_SERVICE, schemaCapture );
		TestHelper.getDefaultTestSessionFactory( settings, EntityWithWrongTemplate.class );
	}

	public static Map<String, Object> settings() {
		Map<String, Object> settings = new HashMap<>();
		settings.put( OgmProperties.DATASTORE_PROVIDER, "infinispan_remote" );
		settings.put(
				InfinispanRemoteProperties.CONFIGURATION_RESOURCE_NAME,
				"hotrod-client-testingconfiguration.properties"
		);
		settings.put( OgmProperties.CREATE_DATABASE, true );
		return settings;
	}

}
