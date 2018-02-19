/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.type.descriptor.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;

/**
 * Wraps JdbcTimestampTypeDescriptor to fix Date to Timestamp.
 *
 * @author Davide D'Alto &lt;davide@hibernate.org&gt;
 */
public class TimestampTypeDescriptor extends JdbcTimestampTypeDescriptor {

	public static final TimestampTypeDescriptor INSTANCE = new TimestampTypeDescriptor();


	@Override
	public <X> Date wrap(X value, WrapperOptions options) {
		Date wrapped = super.wrap( value, options );
		if ( Date.class.isInstance( wrapped ) && !Timestamp.class.isInstance( wrapped ) ) {
			return new Timestamp( wrapped.getTime() );
		}
		return wrapped;
	}

}
