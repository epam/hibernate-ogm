package org.hibernate.ogm.datastore.mongodb.test.query.namedquery;

import org.fest.util.Arrays;
import org.hibernate.ogm.utils.OgmTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MongoDBNamedQueryCountTest extends OgmTestCase {

    @Before
    public void init() {
        inTransaction( session -> {

        } );
    }

    @After
    public void tearDown() {
        inTransaction( session -> {

        } );
    }

    @Test
    public void testCountQuery() throws Exception {
        Long result = (Long) em.createNamedQuery( "CountPoems" ).getSingleResult();
        assertThat( result ).isEqualTo( 2L );
    }

    @Override
    protected Class<?>[] getAnnotatedClasses() {
        return Arrays.array(MarkTwainPoem.class);
    }
}
