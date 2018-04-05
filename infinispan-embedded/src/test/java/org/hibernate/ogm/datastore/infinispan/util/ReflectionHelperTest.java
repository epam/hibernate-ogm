/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */

package org.hibernate.ogm.datastore.infinispan.util;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class ReflectionHelperTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void introspectShouldReturnFieldValues() throws Exception {
		Subject subject = new Subject();
		subject.setField( "value" );
		Map<String, Object> res = ReflectionHelper.introspect( subject );
		assertThat( res ).isEqualTo( Collections.singletonMap( "field", "value" ) );
	}

	@Test
	public void instantiate() throws Exception {
		Object subject = ReflectionHelper.instantiate( "org.hibernate.ogm.datastore.infinispan.util.ReflectionHelperTest$Subject" );
		assertThat( subject ).isEqualTo( new Subject() );
		assertThat( subject ).isNotEqualTo( new Subject( "value" ) );
	}

	@Test
	public void setFields() throws Exception {
		Subject subject = new Subject();
		ReflectionHelper.setFields( subject, Collections.singletonMap( "field", "value" ) );
		assertThat( subject.getField() ).isEqualTo( "value" );
	}

	@Test
	public void setNonExistingFields() throws Exception {
		thrown.expect( NoSuchMethodException.class );
		ReflectionHelper.setFields( new Subject(), Collections.singletonMap( "nonExistingField", "value" ) );
	}

	public static class Subject {

		private String field;

		public Subject() {
		}

		public Subject(String field) {
			this.field = field;
		}

		public String getField() {
			return field;
		}

		public void setField(String field) {
			this.field = field;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( o == null || getClass() != o.getClass() ) {
				return false;
			}
			Subject subject = (Subject) o;
			return Objects.equals( field, subject.field );
		}

		@Override
		public int hashCode() {
			return Objects.hash( field );
		}
	}
}
