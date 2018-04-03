package org.hibernate.ogm.datastore.infinispan.test.storedprocedures;

import org.hibernate.ogm.backendtck.storedprocedures.Car;

import org.infinispan.util.function.SerializableCallable;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class ResultSetProcedure implements SerializableCallable<Car> {

	private Integer id;

	private String title;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public Car call() {
		return new Car( id, title );
	}
}
