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

	@Procedure(value = Car.SIMPLE_VALUE_PROC, mode= Mode.WRITE)
	public Stream<Output> simpleValueProcedure(@Name("id") long id) {
		Map<String, Object> map = new HashMap<>(  );
		map.put( "id", id );
		return  Collections.list(new Output(map)).stream();
	}

	@Procedure(value = Car.RESULT_SET_PROC, mode= Mode.WRITE)
	public Stream<Output> resultSetProcedure(@Name("id") long id, @Name("title") String title) {
		Map map = new HashMap();
		map.put( id, title );
		return Collections.list(new Output(map)).stream();
	}

	public class Output {
		public Map out;

		public Output(Map out) {
			this.out = out;
		}

		public Map getOut() {
			return out;
		}

	}
}
//	Result result = dataBase.execute( "CALL dbms.procedures()" );
//	ArrayList list = new ArrayList(  );
//while(result.hasNext()){
//		list.add( result.next().get( "name" ) );
//		}
//		list