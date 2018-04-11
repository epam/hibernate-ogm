/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

package org.hibernate.ogm.util.impl;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hibernate.ogm.model.spi.Tuple;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class TupleHelper {

	private static final Log log = LoggerFactory.make( MethodHandles.lookup() );

	private TupleHelper() {
		// util class
	}

	/**
	 * Extract tuple values from given object.
	 *
	 * @param obj object to be extracted
	 *
	 * @return list tuple result
	 */
	public static List<Tuple> extractTuples(Object obj) {
		if ( obj instanceof Iterable ) {
			Iterable<?> it = (Iterable) obj;
			return StreamSupport.stream( it.spliterator(), false )
					.map( TupleHelper::extractTuple )
					.collect( Collectors.toList() );
		}
		Tuple tuple = new Tuple();
		tuple.put( "result", obj );
		return Collections.singletonList( tuple );
	}

	private static Tuple extractTuple(Object obj) {
		Tuple tuple = new Tuple();
		if ( obj instanceof Map ) {
			Map<?, ?> map = (Map) obj;
			for ( Map.Entry<?, ?> e : map.entrySet() ) {
				tuple.put( String.valueOf( e.getKey() ), e.getValue() );
			}
			return tuple;
		}
		try {
			Map<String, Object> introspect = ReflectionHelper.introspect( obj );
			introspect.forEach( tuple::put );
			return tuple;
		}
		catch (Exception e) {
			throw log.cannotExtractTuple( e );
		}
	}
}
