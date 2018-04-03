/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm.datastore.infinispanremote.test.storedprocedures;

import org.hibernate.SessionFactory;
import org.hibernate.ogm.backendtck.storedprocedures.Car;
import org.hibernate.ogm.backendtck.storedprocedures.NamedParametersStoredProcedureCallTest;
import org.hibernate.ogm.datastore.infinispanremote.impl.InfinispanRemoteDatastoreProvider;
import org.hibernate.ogm.datastore.infinispanremote.utils.InfinispanRemoteJpaServerRunner;
import org.hibernate.ogm.utils.TestForIssue;

import org.infinispan.client.hotrod.RemoteCache;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.hibernate.ogm.datastore.infinispanremote.utils.InfinispanRemoteTestHelper.getProvider;

/**
 * Testing call of stored procedures.
 * <p>This test based on 3 deployables (simple-value-procedure.jar, result-set-procedure.jar, exceptional-procedure.jar).
 * <p>They're representatives of following classes accordingly
 * <p>Simple value procedure<pre>{@code
 * public class SimpleValueProcedure implements ServerTask<Integer> {
 *
 *   private int id;
 *
 *   @Override
 *   public void setTaskContext(TaskContext taskContext) {
 *     id = taskContext.getParameters()
 *       .map(p -> p.get("param"))
 *       .map(Integer.class::cast)
 *       .orElseThrow(() -> new RuntimeException("missing parameter 'param'"));
 *   }
 *
 *   @Override
 *   public Integer call() throws Exception {
 *     return id;
 *   }
 *
 *   @Override
 *   public String getName() {
 *     return "simpleValueProcedure";
 *   }
 * }
 *}</pre>
 *
 * <p>Result set procedure<pre>{@code
 * public class ResultSetProcedure implements ServerTask<List<Car>> {
 *
 *   private int id;
 *   private String title;
 *
 *   @Override
 *   public void setTaskContext(TaskContext taskContext) {
 *     id = taskContext.getParameters()
 *       .map(p -> p.get("id"))
 *       .map(Integer.class::cast)
 *       .orElseThrow(() -> new RuntimeException("missing parameter 'id'"));
 *     title = taskContext.getParameters()
 *       .map(p -> p.get("title"))
 *       .map(String.class::cast)
 *       .orElseThrow(() -> new RuntimeException("missing parameter 'title'"));
 * }
 *
 *   @Override
 *   public List<Car> call() throws Exception {
 *     return Collections.singletonList(new Car(id, title));
 *   }
 *
 *   @Override
 *   public String getName() {
 *     return "resultSetProcedure";
 *   }
 * }
 *}</pre>
 *
 * <p>Exceptional procedure<pre>{@code
 * public class ExceptionalProcedure implements ServerTask<Void> {
 *
 *   @Override
 *   public void setTaskContext(TaskContext taskContext) {
 *   }
 *
 *   @Override
 *   public Void call() throws Exception {
 *     throw new RuntimeException("Failure! This is supposed to happen for tests, don't worry")
 *   }
 *
 *   @Override
 *   public String getName() {
 *     return "simpleValueProcedure";
 *   }
 * }
 * }</pre>
 *
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
@TestForIssue(jiraKey = { "OGM-1431" })
@RunWith(InfinispanRemoteJpaServerRunner.class)
public class ServerTaskInfinispanNamedParametersStoredProcedureCallTest extends NamedParametersStoredProcedureCallTest {

	@Before
	public void setUp() {
		super.setUp();
		InfinispanRemoteDatastoreProvider provider = getProvider( ( (SessionFactory) getFactory() ) );
		RemoteCache<Object, Object> scriptCache = provider.getScriptCache();
		// remove all scripts because they have higher precedences
		scriptCache.remove( Car.SIMPLE_VALUE_PROC );
		scriptCache.remove( Car.RESULT_SET_PROC );
		scriptCache.remove( "exceptionalProcedure" );
	}
}
