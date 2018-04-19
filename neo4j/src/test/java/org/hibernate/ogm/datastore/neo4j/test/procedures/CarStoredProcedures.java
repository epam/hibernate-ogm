package org.hibernate.ogm.datastore.neo4j.test.procedures;

import java.util.stream.Stream;

import org.hibernate.ogm.backendtck.storedprocedures.Car;

import org.fest.util.Collections;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;


public class CarStoredProcedures {

	@Procedure(value = Car.SIMPLE_VALUE_PROC, mode = Mode.WRITE)
	public Stream<Result> simpleValueProcedure(@Name("id") long id) {
		return Collections.list( new Result( (int) id, null ) ).stream();
	}

	@Procedure(value = Car.RESULT_SET_PROC, mode = Mode.WRITE)
	public Stream<Result> resultSetProcedure(@Name("id") long id, @Name("title") String title) {
		return Collections.list( new Result( (int) id, title ) ).stream();
	}

	public static class Result {

		public Number id;
		public String title;

		public Result() {
		}

		public Result(Integer id) {
			this.id = id;
		}

		public Result(Integer id, String title) {
			this.id = id;
			this.title = title;
		}
	}

}