package com.cmich.edu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.taskqueue.DeferredTask;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.google.gson.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
@SuppressWarnings("serial")
public class JsonFetcher extends HttpServlet   {
	
	static final int DELAY_MS = 5000;
		
	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	public static class UpLoadImage implements DeferredTask {
		String sec;
		String titl; // variables to store values passed via parameter
		String publ;
		String abst;
		String ur;
		String link ;
        
        public UpLoadImage(String s1,String s2,String s3,String s4,String s5) {
        	sec=s1;
        	titl=s2;
        	publ=s3; // constructor
        	abst=s4;
        	ur =s5;
       	    //link = s6;
        }
           public void run()  {
        	   try {
			//URL	url = new URL(link);
        	   ///////////////////////////////used incase image/ picture is used
	     	/*InputStream inStream;
			 inStream = url.openStream();
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
      			int nRead;
      			byte[] data = new byte[1024];
      			while((nRead = inStream.read(data, 0, data.length)) != -1) {
      				buffer.write(data, 0, nRead);
      			}
      			buffer.flush();
      			byte[] imageData = buffer.toByteArray(); 
      			*/
        		   
        		DatastoreService ds1 = DatastoreServiceFactory.getDatastoreService();  // datastore instance
        		Entity article = new Entity("ArticlesDB", "vigneshram"); //parent entity
      			Entity photo = new Entity("ArticlesphotoDB", article.getKey());  // child entity
      			photo.setProperty("section",sec);           // set section
	   			photo.setProperty("title", titl); //set title
	   			photo.setProperty("published_date", publ); //set published date
	   			photo.setProperty("abstract", abst );  // set abstract
	   			photo.setProperty("url", ur);   // set url
      			//photo.setProperty("image", link);           // set image
      			photo.setProperty("timestamp", new Date());          // register timestamp
      			ds1.put(photo);              // add all to datastore
      			
                
                
        	   }
        	   catch( Exception e){
        		   
        	         System.out.println("exception on task queue");

        	   }
            
       }}
	
	
	
	 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 
         response.getWriter().println( " fetcher  started" + timestamp);
        
			    String strURL= "https://api.nytimes.com/svc/topstories/v2/home.json?apikey=fc8222667db44088805d9d95dfc9c06e";        //storing api url in string
			    URL url = new URL(strURL);    // creating url instance
			    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream())); //storing json in buffered reader
			    JsonArray ja,ja1,ja2,ja3,ja4,ja5,ja6,ja7,ja8,ja9,ja10= new JsonArray();      // object for jsonarray
			    JsonParser pars= new JsonParser();             //object for jsonparser
			    JsonObject jo,jo1,jo2,jo3,jo4,jo5,jo6,jo7,jo8,jo9,jo10,joi1 ,joi2 ,joi3 ,joi4 ,joi5 ,joi6 ,joi7 ,joi8 ,joi9, joi10 = new JsonObject();    //objects for jsonobject
			    JsonElement je = pars.parse(reader);          // storing parsed json to json elements
			    reader.close(); 
			    jo= je.getAsJsonObject();            // converting element to json object
			    ja=  jo.getAsJsonArray("results");           // retriving result array from json object

			    
			    jo1 =ja.get(0).getAsJsonObject();    // converting json result array to object

		         
			     String a1s=jo1.get("section").toString();         // accessing object & converting to string
			     String a1t=jo1.get("title").toString();
			     String a1p=jo1.get("published_date").toString();
			     String a1a=jo1.get("abstract").toString();
			     String a1u=jo1.get("url").toString();

			
				  //  ja1=jo1.get("multimedia").getAsJsonArray();
				   // joi1= ja1.get(0).getAsJsonObject();
				    //String a1i=joi1.get("url").toString();
				   
			    jo2 =ja.get(1).getAsJsonObject();
			    String a2s= jo2.get("section").toString(); // accessing object & converting to string
			    String a2t =jo2.get("title").toString();
			    String a2p=jo2.get("published_date").toString();
			    String a2a= jo2.get("abstract").toString();
			    String a2u= jo2.get("url").toString();
			    
			    
			     

			    //ja2=jo2.get("multimedia").getAsJsonArray();
			    //joi2= ja2.get(0).getAsJsonObject();
			    //String a2i=joi2.get("url").toString();

			    jo3 =ja.get(2).getAsJsonObject(); // accessing object & converting to string
			      String a3s= jo3.get("section").toString();
			      String a3t= jo3.get("title").toString();
			      String a3p= jo3.get("published_date").toString();
			      String a3a= jo3.get("abstract").toString();
			      String a3u=jo3.get("url").toString();
			    


				    //ja3=jo3.get("multimedia").getAsJsonArray();
				    //joi3= ja3.get(0).getAsJsonObject();
				  //  String a3i=joi3.get("url").toString();

			      
			    jo4 =ja.get(3).getAsJsonObject();
			      String a4s= jo4.get("section").toString(); // accessing object & converting to string
			      String a4t= jo4.get("title").toString();
			      String a4p= jo4.get("published_date").toString();
			      String a4a=jo4.get("abstract").toString();
			      String a4u= jo4.get("url").toString();
			  


				  //  ja4=jo1.get("multimedia").getAsJsonArray();
				    //joi4= ja4.get(0).getAsJsonObject();
				    //String a4i=joi4.get("url").toString();

			      
			    jo5 =ja.get(4).getAsJsonObject();
			      String a5s= jo5.get("section").toString(); // accessing object & converting to string
			      String a5t= jo5.get("title").toString();
			      String a5p= jo5.get("published_date").toString();
			      String a5a= jo5.get("abstract").toString();
			      String a5u= jo5.get("url").toString();
			      

				   

				  //  ja5=jo5.get("multimedia").getAsJsonArray();
				    //joi5= ja5.get(0).getAsJsonObject();
				    //String a5i=joi5.get("url").toString();
			      
			    jo6 =ja.get(5).getAsJsonObject();
			      String a6s=jo6.get("section").toString();
			      String a6t =jo6.get("title").toString();
			      String a6p= jo6.get("published_date").toString();
			      String a6a=jo6.get("abstract").toString(); // accessing object & converting to string
			      String a6u=jo6.get("url").toString();
			      
			      
			      

				   // ja6=jo6.get("multimedia").getAsJsonArray();
				    //joi6= ja6.get(0).getAsJsonObject();
				    //String a6i=joi6.get("url").toString();
			      
			      
			    jo7 =ja.get(6).getAsJsonObject();
			      String a7s=jo7.get("section").toString();
			      String a7t=jo7.get("title").toString(); // accessing object & converting to string
			      String a7p= jo7.get("published_date").toString();
			      String a7a=jo7.get("abstract").toString();
			      String a7u=jo7.get("url").toString();
			      
			      

				  //  ja7=jo7.get("multimedia").getAsJsonArray();
				   // joi7= ja7.get(0).getAsJsonObject();
				    //String a7i=joi7.get("url").toString();
			      
			      
			    jo8 =ja.get(7).getAsJsonObject();
			      String a8s=jo8.get("section").toString();
			      String a8t= jo8.get("title").toString(); // accessing object & converting to string
			      String a8p= jo8.get("published_date").toString();
			      String a8a=jo8.get("abstract").toString();
			      String a8u=jo8.get("url").toString();
			      
			      

				  //  ja8=jo8.get("multimedia").getAsJsonArray();
				    //joi8= ja8.get(0).getAsJsonObject();
				    //String a8i=joi8.get("url").toString();
			      
			      
			    jo9 =ja.get(8).getAsJsonObject();
			      String a9s= jo9.get("section").toString(); // accessing object & converting to string
			      String a9t=  jo9.get("title").toString();
			      String a9p=jo9.get("published_date").toString();
			      String a9a= jo9.get("abstract").toString();
			      String a9u= jo9.get("url").toString();
			      
			      
			      

				  //  ja9=jo9.get("multimedia").getAsJsonArray();
				   // joi9= ja9.get(0).getAsJsonObject();
				    //String a9i=joi9.get("url").toString();
			      
			      
			    jo10 =ja.get(9).getAsJsonObject();
			      String a10s=jo10.get("section").toString(); // accessing object & converting to string
			      String a10t= jo10.get("title").toString();
			      String a10p= jo10.get("published_date").toString();
			      String a10a=jo10.get("abstract").toString();
			      String a10u=jo10.get("url").toString();
			      
			      
			      
			      
			      
			      

				   // ja10=jo10.get("multimedia").getAsJsonArray();
				    //joi10= ja10.get(0).getAsJsonObject();
				    //String a10i=joi10.get("url").toString();
			      
			      //,a10i.substring(1, a10i.length()-1),a9i.substring(1, a9i.length()-1),a8i.substring(1, a8i.length()-1),a7i.substring(1, a7i.length()-1),a6i.substring(1, a6i.length()-1),a5i.substring(1, a5i.length()-1),a4i.substring(1, a4i.length()-1),a3i.substring(1, a3i.length()-1),a1i.substring(1, a1i.length()-1),a2i.substring(1, a2i.length()-1)
			        
			      String[] Article1 = {a1s.substring(1, a1s.length()-1),a1t.substring(1, a1t.length()-1),a1p.substring(1, a1p.length()-1),a1a.substring(1, a1a.length()-1),a1u.substring(1, a1u.length()-1)};
			      String[] Article2 = {a2s.substring(1, a2s.length()-1),a2t.substring(1, a2t.length()-1),a2p.substring(1, a2p.length()-1),a2a.substring(1, a2a.length()-1),a2u.substring(1, a2u.length()-1)};
			      String[] Article3 = {a3s.substring(1, a3s.length()-1),a3t.substring(1, a3t.length()-1),a3p.substring(1, a3p.length()-1),a3a.substring(1, a3a.length()-1),a3u.substring(1, a3u.length()-1)};           // storing all strings into article array
			      String[] Article4 = {a4s.substring(1, a4s.length()-1),a4t.substring(1, a4t.length()-1),a4p.substring(1, a4p.length()-1),a4a.substring(1, a4a.length()-1),a4u.substring(1, a4u.length()-1)};
			      String[] Article5 = {a5s.substring(1, a5s.length()-1),a5t.substring(1, a5t.length()-1),a5p.substring(1, a5p.length()-1),a5a.substring(1, a5a.length()-1),a5u.substring(1, a5u.length()-1)};
			      String[] Article6 = {a6s.substring(1, a6s.length()-1),a6t.substring(1, a6t.length()-1),a6p.substring(1, a6p.length()-1),a6a.substring(1, a6a.length()-1),a6u.substring(1, a6u.length()-1)};
			      String[] Article7 = {a7s.substring(1, a7s.length()-1),a7t.substring(1, a7t.length()-1),a7p.substring(1, a7p.length()-1),a7a.substring(1, a7a.length()-1),a7u.substring(1, a7u.length()-1)};
			      String[] Article8 = {a8s.substring(1, a8s.length()-1),a8t.substring(1, a8t.length()-1),a8p.substring(1, a8p.length()-1),a8a.substring(1, a8a.length()-1),a8u.substring(1, a8u.length()-1)};
			      String[] Article9 = {a9s.substring(1, a9s.length()-1),a9t.substring(1, a9t.length()-1),a9p.substring(1, a9p.length()-1),a9a.substring(1, a9a.length()-1),a9u.substring(1, a9u.length()-1)};
			      String[] Article10 = {a10s.substring(1, a10s.length()-1),a10t.substring(1, a10t.length()-1),a10p.substring(1, a10p.length()-1),a10a.substring(1, a10a.length()-1),a10u.substring(1, a10u.length()-1)};
			   
			  	Entity art = new Entity("ArticlesDB", "vigneshram");

			    Queue queue = QueueFactory.getDefaultQueue();
			    
		   			queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article1[0],Article1[1],Article1[2],Article1[3],Article1[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article2[0],Article2[1],Article2[2],Article2[3],Article2[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));              // passing article array to task queue
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article3[0],Article3[1],Article3[2],Article3[3],Article3[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article4[0],Article4[1],Article4[2],Article4[3],Article4[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article5[0],Article5[1],Article5[2],Article5[3],Article5[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article6[0],Article6[1],Article6[2],Article6[3],Article6[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article7[0],Article7[1],Article7[2],Article7[3],Article7[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article8[0],Article8[1],Article8[2],Article8[3],Article8[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article9[0],Article9[1],Article9[2],Article9[3],Article9[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            queue.add(TaskOptions.Builder.withPayload(new UpLoadImage(Article10[0],Article10[1],Article10[2],Article10[3],Article10[4])).etaMillis(System.currentTimeMillis() + DELAY_MS));
		            
		   			response.getWriter().println( " fetcher ended" + timestamp);
		   	
		   			}
}
