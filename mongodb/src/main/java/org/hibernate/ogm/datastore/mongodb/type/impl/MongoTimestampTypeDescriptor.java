/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.mongodb.type.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;

public class MongoTimestampTypeDescriptor extends JdbcTimestampTypeDescriptor {

	@Override
	public <X> Date wrap(X value, WrapperOptions options) {
		Date wraped = super.wrap( value, options );
		if ( Date.class.isInstance( wraped ) && !Timestamp.class.isInstance( wraped ) ) {
			return new Timestamp( wraped.getTime() );
		}
		return wraped;
	}
}
