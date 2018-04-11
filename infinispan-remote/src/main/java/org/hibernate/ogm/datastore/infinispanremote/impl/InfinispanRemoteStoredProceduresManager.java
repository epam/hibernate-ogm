/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.infinispanremote.impl;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.hibernate.ogm.datastore.infinispanremote.logging.impl.Log;
import org.hibernate.ogm.datastore.infinispanremote.logging.impl.LoggerFactory;
import org.hibernate.ogm.dialect.query.spi.ClosableIterator;
import org.hibernate.ogm.model.spi.Tuple;
import org.hibernate.ogm.storedprocedure.ProcedureQueryParameters;
import org.hibernate.ogm.util.impl.CollectionHelper;
import org.hibernate.ogm.util.impl.TupleHelper;

import org.infinispan.client.hotrod.RemoteCache;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class InfinispanRemoteStoredProceduresManager {

	private static final Log log = LoggerFactory.make( MethodHandles.lookup() );

	/**
	 * Returns the result of a stored procedure executed on the backend.
	 *
	 * @param remoteCache remote cache
	 * @param storedProcedureName name of stored procedure
	 * @param queryParameters parameters passed for this query
	 * @return a {@link ClosableIterator} with the result of the query
	 */
	public ClosableIterator<Tuple> callStoredProcedure( RemoteCache<Object, Object> remoteCache, String storedProcedureName, ProcedureQueryParameters queryParameters ) {
		if ( remoteCache.get( storedProcedureName ) == null ) {
			throw log.procedureWithResolvedNameDoesNotExist( storedProcedureName, null );
		}
		validate( queryParameters );
		try {
			Object res = remoteCache.execute( storedProcedureName, queryParameters.getNamedParameters() );
			return CollectionHelper.newClosableIterator( TupleHelper.extractTuples( res ) );
		}
		catch (Exception e) {
			throw log.cannotExecuteStoredProcedure( storedProcedureName, e );
		}
	}

	private void validate(ProcedureQueryParameters queryParameters) {
		List<Object> positionalParameters = queryParameters.getPositionalParameters();
		if ( positionalParameters != null && positionalParameters.size() > 0 ) {
			throw log.dialectDoesNotSupportPositionalParametersForStoredProcedures( getClass() );
		}
	}
}
