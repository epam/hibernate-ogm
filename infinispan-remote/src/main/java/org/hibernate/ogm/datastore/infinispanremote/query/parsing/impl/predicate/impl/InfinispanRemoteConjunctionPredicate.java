/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.infinispanremote.query.parsing.impl.predicate.impl;

import org.hibernate.hql.ast.spi.predicate.ConjunctionPredicate;
import org.hibernate.hql.ast.spi.predicate.NegatablePredicate;
import org.hibernate.ogm.datastore.infinispanremote.query.parsing.impl.InfinispanRemoteQueryBuilder;

/**
 * Infinispan remote-based implementation of {@link ConjunctionPredicate}.
 *
 * @author Fabio Massimo Ercoli
 */
public class InfinispanRemoteConjunctionPredicate extends ConjunctionPredicate<InfinispanRemoteQueryBuilder> implements NegatablePredicate<InfinispanRemoteQueryBuilder> {

	public InfinispanRemoteConjunctionPredicate() {
	}

	@Override
	public InfinispanRemoteQueryBuilder getQuery() {
		return new InfinispanRemoteQueryBuilder( "and", false, children );
	}

	@Override
	public InfinispanRemoteQueryBuilder getNegatedQuery() {
		return new InfinispanRemoteQueryBuilder( "or", true, children );
	}
}
