package org.hibernate.ogm.datastore.infinispan.test.storedprocedures;

import org.infinispan.util.function.SerializableCallable;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class SimpleValueProcedure implements SerializableCallable<Integer> {

	private Integer param;

	public void setParam(Integer param) {
		this.param = param;
	}

	@Override
	public Integer call() {
		return param;
	}
}
