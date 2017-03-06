package com.cmich.edu;

	import java.io.IOException;
	import java.math.BigInteger;

	import javax.servlet.http.*;

	import com.google.appengine.api.datastore.DatastoreService;
	import com.google.appengine.api.datastore.DatastoreServiceFactory;
	import com.google.appengine.api.datastore.Entity;
	import com.google.appengine.api.datastore.FetchOptions;
	import com.google.appengine.api.datastore.Key;
	import com.google.appengine.api.datastore.PreparedQuery;
	import com.google.appengine.api.datastore.Query;
	import com.google.appengine.api.datastore.QueryResultList;
	import com.google.appengine.api.memcache.MemcacheService;
	import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gson.Gson;

import javax.servlet.ServletException;

	@SuppressWarnings("serial")
	public class memcached extends HttpServlet  {

		        
		        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		       
		        		//response.getWriter().println( "Mem-Cached Data");
		        		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();    // creating datastore instance
		        		Entity ey = new Entity("ArticlesphotoDB");   // creating object for  entity
		        		Query q1 = new Query("ArticlesphotoDB");        // creating object  for query to query ArticlesphotoDB
		        		q1.addSort("timestamp", Query.SortDirection.DESCENDING);        //     sorting
		        		PreparedQuery pq1 = ds.prepare(q1);        
		        		QueryResultList<Entity> result = pq1.asQueryResultList(FetchOptions.Builder.withLimit(10));   // storing query in result 
		        		Key k =ey.getParent();    // acccesing parent key
		        		MemcacheService cache = MemcacheServiceFactory.getMemcacheService(); // instance for cache
		        		cache.put(k,result);           // passing data in to memcache
		        		Gson gson = new Gson();                     
		        		String data = gson.toJson(cache.get(k));     //converting result into json
		        		response.setContentType("application/json");
		        		response.getWriter().println(data); ////print
		        		
		        		
		        		
		        		
		        		// page count using Mem-caching 
		        		
	    				MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();      //  memcache based page counter
		        	    String key = "count-sync2";
		        	    byte[] value;
		        	    long count = 1;
		        	    value = (byte[]) syncCache.get(key);
		        	    if (value == null) {
		        	      value = BigInteger.valueOf(count).toByteArray();
		        	      syncCache.put(key, value);
		        	    } else {
		        	      // Increment value
		        	      count = new BigInteger(value).longValue();
		        	      count++;
		        	      value = BigInteger.valueOf(count).toByteArray();
		        	      // Put back in cache
		        	      syncCache.put(key, value);
		        	    }

		        	    // Output content
		        	   response.setContentType("text/plain");
		        	response.getWriter().print("@memcache---page views is " + count + "\n----- ");
		       
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	


