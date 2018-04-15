package org.hibernate.ogm.datastore.neo4j.test.procedures;

import static org.neo4j.kernel.api.proc.Neo4jTypes.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.hibernate.ogm.backendtck.storedprocedures.Car;

import org.fest.util.Collections;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;


public class CarStoredProcedures {

	@Procedure(value = Car.SIMPLE_VALUE_PROC, mode = Mode.WRITE)
	public Stream<Result> simpleValueProcedure(@Name("id") long id) {
		return Collections.list( new Result( id, "" ) ).stream();
	}

	@Procedure(value = Car.RESULT_SET_PROC, mode = Mode.WRITE)
	public Stream<Result> resultSetProcedure(@Name("id") long id, @Name("title") String title) {
		return Collections.list( new Result( id, title ) ).stream();
	}

	public static class Result {
		public long id;
		public String title;

		public Result() {
		}

		public Result(Long id) {
			this.id = id;
		}

		public Result(Long id, String title) {
			this.id = id;
			this.title = title;
		}
	}
}
//	Result result = dataBase.execute( "CALL dbms.procedures()" );
//	ArrayList list = new ArrayList(  );
//while(result.hasNext()){
//		list.add( result.next().get( "name" ) );
//		}
//		list