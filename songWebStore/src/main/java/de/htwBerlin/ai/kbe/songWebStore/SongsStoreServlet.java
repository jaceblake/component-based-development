package de.htwBerlin.ai.kbe.songWebStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet(
	name = "SongsServlet", 
	urlPatterns = "/*",
	// hier fehlt noch was
	initParams = {
	         @WebInitParam(name = "songFile", value = "songs.json")
	}
		)
        
public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String APPLICATION_JSON = "application/json";
	
	private static final String TEXT_PLAIN = "text/plain";
	
    private String songFilename = null;
	
    private Map<Integer, Song> songStore = null;
    
    private Integer currentID = null;

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {
		
		songFilename = servletConfig.getInitParameter("songFile");
		
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);
		
	    try {
	    	List<Song> songList = new ObjectMapper().
	    			readValue(input,
	    					new TypeReference<List<Song>>(){});
	    	
	    songStore = new ConcurrentHashMap<>();	
	    
	    songList.stream().forEach(e -> this.songStore.put(e.getId(), e));
	    
	    
	    
		} catch (JsonParseException e) {
			
			e.printStackTrace();
		} catch (JsonMappingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	
	    System.out.println("TEST");
		System.out.println("In init");
	}

    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Enumeration<String> paramNames = request.getParameterNames();
		
		response.setContentType("application/json");
		String param = "";
		while(paramNames.hasMoreElements()) {
		param = paramNames.nextElement();
		
		try (PrintWriter out = response.getWriter()) {
			
            if(request.getParameter("all") != null) {
			out.println(new ObjectMapper().writeValueAsString(songStore));
            }
            
            else if(request.getParameter("songId") != null ) {
            	if(request.getParameter("songId").equals("6")) {
			    out.println(new ObjectMapper().writeValueAsString(songStore.get(6)));
            	}
            }
		}
	}
		
	}

	
//	@Override
//	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}
	
//	@Override
//	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}	
	
//	@Override
//	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
//	}
	
	// save songStore to file
	@Override
	public void destroy() {
		System.out.println("In destroy");
	}
	
	
//	
//	public static void main(String[] args) {
//		
//		Map<Integer, Song> songStore = new HashMap<>();
//		
//	    try {
//	    	
//	    	List<Song> songList = new ObjectMapper().
//	    			readValue(new File("src/main/resources/songs.json"),
//	    					new TypeReference<List<Song>>(){});
//	    	
//	    	songList.stream().forEach(e -> songStore.put(e.getId(), e));
//	    	
//	    songList.stream().forEach(e -> System.out.println(e.getAlbum()));		
//			
//		} catch (JsonParseException e) {
//			
//			e.printStackTrace();
//		} catch (JsonMappingException e) {
//			
//			e.printStackTrace();
//		} catch (IOException e) {
//			
//			e.printStackTrace();
//		}
//
//	
//	    
//	}
		
		
	}

