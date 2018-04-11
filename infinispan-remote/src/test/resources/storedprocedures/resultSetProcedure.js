// mode=local,language=javascript,parameters=[id,title]
var Collections = Java.type( 'java.util.Collections' );
var HashMap = Java.type( 'java.util.HashMap' );
var hashMap = new HashMap();
hashMap.put( 'id', id );
hashMap.put( 'title', title );
Collections.singleton( hashMap );