/*
 * Hibernate OGM, Domain model persistence for NoSQL datastores
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.ogm;

import org.infinispan.tasks.ServerTask;
import org.infinispan.tasks.TaskContext;

public class HelloTask implements ServerTask<Number> {

	private TaskContext ctx;

	@Override
	public void setTaskContext(TaskContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public Number call() {
		String name = (String) ctx.getParameters().get().get( "param" );
		return 1;
	}

	@Override
	public String getName() {
		return "simpleValueProcedure";
	}

}
