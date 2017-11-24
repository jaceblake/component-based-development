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
import java.util.concurrent.atomic.AtomicInteger;

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

@WebServlet(name = "SongsServlet", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "songFile", value = "songs.json") })

public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_JSON = "application/json";

	private static final String TEXT_PLAIN = "text/plain";

	private String songFilename = null;

	private Map<Integer, Song> songStore = null;

	private AtomicInteger currentID = null;

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {

		songFilename = servletConfig.getInitParameter("songFile");

		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);

		try {
			List<Song> songList = new ObjectMapper().
					readValue(input, new TypeReference<List<Song>>() {
			});

			songStore = new ConcurrentHashMap<>();

			songList.stream().forEach(e -> this.songStore.put(e.getId(), e));
			
			currentID =  new AtomicInteger(songStore.size());
			
			System.out.println(currentID);

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

		response.setContentType(APPLICATION_JSON);

		try (PrintWriter out = response.getWriter()) {

			if (request.getParameter("all") != null && request.getParameter("all").equals("")) {
				out.println(new ObjectMapper().writeValueAsString(songStore));
			}

			else if (request.getParameter("songId") != null) {

				String value = request.getParameter("songId");

				try {
					int valueInt = Integer.parseInt(value);

					if (songStore.get(valueInt) != null) {
						out.println(new ObjectMapper().writeValueAsString(songStore.get(valueInt)));
					} else {
						response.setContentType(TEXT_PLAIN);
						out.println("Object not Found");
					}
				} catch (NumberFormatException e) {
					response.setContentType(TEXT_PLAIN);
					out.println("Expected Value is Integer");
				}
			}

		}
	}


	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
//		Song s = new ObjectMapper().readValue(request.get, Song.class);
//		
//		response.setContentType(TEXT_PLAIN);
//		
//		try (PrintWriter out = response.getWriter()) {
//			
//		songStore.put(currentID.incrementAndGet(), s);
//		
//		out.println(currentID);
//		
//		System.out.println(songStore.size());
//		
//		}
	}

	@Override
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
	}
	

	@Override
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
	}

	// save songStore to file
	@Override
	public void destroy() {
		System.out.println("In destroy");
	}
	

}