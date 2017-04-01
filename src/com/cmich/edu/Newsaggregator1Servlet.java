package com.cmich.edu;
import java.io.IOException;
import java.math.BigInteger;
import javax.servlet.http.*;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class Newsaggregator1Servlet extends HttpServlet  {

	        
	        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	       
	        		response.getWriter().println( "Main Page - HeadTweets\n");
	        		DatastoreService ds = DatastoreServiceFactory.getDatastoreService(); // creating datastore instance
	        		Entity ey = new Entity("ArticlesphotoDB");// creating object for  entity
	        		Query q1 = new Query("ArticlesphotoDB"); // creating object  for query to query ArticlesphotoDB
	        		q1.addSort("timestamp", Query.SortDirection.DESCENDING);  //     sorting based on time stamp
	        		PreparedQuery pq1 = ds.prepare(q1);
	        		QueryResultList<Entity> result = pq1.asQueryResultList(FetchOptions.Builder.withLimit(10));  // storing query in result
	        	
	        		
			
	        		for(int i=0; i<10;i++)           // accessing each property  to print in servlet output screen
	        		{   response.getWriter().print("\nNews Section :\t");
	        			String sec=(result.get(i).getProperty("section")).toString();
	        			response.getWriter().println(sec); 
	        			response.getWriter().print("Title :\t\t");
	        			String titl=(result.get(i).getProperty("title")).toString();
	        			response.getWriter().println(titl); 

	        			response.getWriter().print("News :\t\t");
	        			String abst=(result.get(i).getProperty("abstract")).toString();
	        			response.getWriter().println(abst); 

	        			response.getWriter().print("Published On :\t");
	        			String publ=(result.get(i).getProperty("published_date")).toString();
	        			response.getWriter().println(publ); 

	        			response.getWriter().print("Read More @ :\t");
	        			String ur=(result.get(i).getProperty("url")).toString();
	        			response.getWriter().println(ur); 

	        			response.getWriter().print("Posted On :\t");
	        			String ts=(result.get(i).getProperty("timestamp")).toString();
	        			response.getWriter().println(ts); 
	        			
	        			response.getWriter().print("\n-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
	    	        	}
	        		

	        		//response.getWriter().println(result);
	    		
	        		// page count using Mem-caching 
	        		
    				MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	        	    String key = "count-sync"; /////////memcache based page view counter
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
	        	    response.getWriter().print("@memcache---page views is " + count + "\n ");
	       
    }
}
