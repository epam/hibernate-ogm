package org.hibernate.ogm.datastore.infinispan.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.infinispan.util.function.SerializableCallable;

/**
 * @author The Viet Nguyen &amp;ntviet18@gmail.com&amp;
 */
public class ReflectionHelper {

	public static Map<String, Object> introspect(Object obj)
			throws IntrospectionException, InvocationTargetException, IllegalAccessException {
		Map<String, Object> result = new HashMap<>();
		BeanInfo info = Introspector.getBeanInfo( obj.getClass() );
		for ( PropertyDescriptor pd : info.getPropertyDescriptors() ) {
			Method reader = pd.getReadMethod();
			String name = pd.getName();
			if ( reader != null && !"class".equals( name ) ) {
				result.put( name, reader.invoke( obj ) );
			}
		}
		return result;
	}

	public static boolean isPrimitiveRef(Class<?> refType) {
		return refType.isPrimitive() ||
				refType == String.class || refType == Boolean.class || refType == Byte.class ||
				refType == Character.class || refType == Double.class || refType == Float.class ||
				refType == Integer.class || refType == Long.class || refType == Short.class ||
				refType == Void.class;
	}

	public static Callable<?> instantiate(String className)
			throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		Class<?> clazz = Class.forName( className );
		return (SerializableCallable<?>) clazz.newInstance();
	}

	private static void setField(Object object, String field, Object value)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		Class<?> clazz = object.getClass();
		Method m = clazz.getMethod( "set" + capitalize( field ), value.getClass() );
		m.invoke( object, value );
	}

	public static void setFields(Object object, Map<String, Object> params)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		for ( Map.Entry<String, Object> entry : params.entrySet() ) {
			setField( object, entry.getKey(), entry.getValue() );
		}
	}

	private static String capitalize(String str) {
		return str.substring( 0, 1 ).toUpperCase() + str.substring( 1 );
	}
}
