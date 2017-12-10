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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@WebServlet(name = "SongsServlet", urlPatterns = "/*", initParams = {
		@WebInitParam(name = "songFile", value = "songs.json") })

public class SongsStoreServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String APPLICATION_JSON = "application/json";

	private static final String TEXT_PLAIN = "text/plain";

	private String songFilename = null;

	private Map<Integer, Song> songStore = null;

	private AtomicInteger currentID = null;

	private Logger log;

	// load songStore from JSON file and set currentID
	public void init(ServletConfig servletConfig) throws ServletException {

		log = Logger.getLogger(SongsStoreServlet.class.getName());

		try {
			Handler handler = new FileHandler("log.txt", true);
			handler.setLevel(Level.INFO);
			log.addHandler(handler);
		} catch (SecurityException e1) {
			System.out.println("SecurityException by creating log File");
		} catch (IOException e1) {
			System.out.println("IOException by creating log File");
		}

		songFilename = servletConfig.getInitParameter("songFile");

		try {
			initializeSongStore();
		} catch (IOException e) {
			log.info("Cann't read json object and convert to Song (In Init)" + e);
		}
		currentID = new AtomicInteger(songStore.size());

		System.out.println(currentID);

		System.out.println("In init");
	}

	public boolean isEmptySongStore() {
		if (songStore == null)
			return true;
		return false;
	}

	public void initializeSongStore() throws IOException {

		if (songFilename == null) {
			songFilename = "songs.json";
		}
		InputStream input = this.getClass().getClassLoader().getResourceAsStream(songFilename);

		List<Song> songList = new ObjectMapper().readValue(input, new TypeReference<List<Song>>() {
		});

		songStore = new ConcurrentHashMap<>();

		songList.stream().forEach(e -> this.songStore.put(e.getId(), e));

	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// all Parameter (keys)
		Enumeration<String> paramNames = request.getParameterNames();

		Map<String, String> allParams = new ConcurrentHashMap<>();
		String param = "";
		while (paramNames.hasMoreElements()) {
			param = paramNames.nextElement();
			allParams.put(param, request.getParameter(param));
		}

		try (PrintWriter out = response.getWriter()) {

			if (allParams.size() == 1) {

				if (allParams.get("all") != null && allParams.get("all").equals("")) {
					response.setContentType(APPLICATION_JSON);
					out.println(new ObjectMapper().writeValueAsString(songStore));
				}

				else if (allParams.get("songId") != null) {

					String value = allParams.get("songId");

					try {
						int valueInt = Integer.parseInt(value);

						if (songStore.get(valueInt) != null) {
							response.setContentType(APPLICATION_JSON);
							out.println(new ObjectMapper().writeValueAsString(songStore.get(valueInt)));
						} else {
							response.setContentType(TEXT_PLAIN);
							out.println("Object not Found");
						}
					} catch (NumberFormatException e) {
						response.setContentType(TEXT_PLAIN);
						out.println("Expected Value is Integer");
						log.info("User tried to give non integer SongId (In doGet)" + e);
					}
				} else {
					response.setContentType(TEXT_PLAIN);
					out.println("Wrong parameter Name !!!");

				}

			} else if (allParams.size() > 1) {
				response.setContentType(TEXT_PLAIN);
				out.println("Expected only one Parameter !!!");
			} else {
				response.setContentType(TEXT_PLAIN);
				out.println("Expected minimum one Parameter !!!");

			}
		}

	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType(TEXT_PLAIN);
		try (PrintWriter out = response.getWriter()) {

			String body = request.getReader().lines().reduce("", (key, value) -> key + value);

			if (body.equals("null")) {
				out.println("please don't send any null value !!!");

			} else if (body.isEmpty()) {
				out.println("please don't send any Empty value !!!");
			} else {

				try {

					Song song = new ObjectMapper().readValue(body, Song.class);

					song.setId(currentID.incrementAndGet());
					System.out.println(song.getId());
					songStore.put(currentID.get(), song);
					out.println("Thanks, your new Song has ID  " + currentID);

				} catch (JsonParseException e) {
					out.println("Please send a valid song Object !!!");
					log.info("User tried to give invalid song Object (In doPsot)" + e);
				}
			}
		}
	}

	// @Override
	// public void doPut(HttpServletRequest request, HttpServletResponse response)
	// throws IOException {
	// }
	//
	//
	// @Override
	// public void doDelete(HttpServletRequest request, HttpServletResponse
	// response) throws IOException {
	// }

	// save songStore to file
	@Override
	public void destroy() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			String str = this.getClass().getClassLoader().getResource(songFilename).getPath();
			System.out.println(str);
			FileOutputStream fileout = new FileOutputStream(str);
			mapper.writeValue(fileout, songStore.values());

		} catch (JsonGenerationException e) {
			log.info("Cann't write value in File (In destroy) " + e);
		} catch (JsonMappingException e) {
			log.info("Cann't write value in File (In destroy) " + e);
		} catch (FileNotFoundException e) {
			log.info("File Not Found (In destroy) " + e);
		} catch (IOException e) {
			log.info("Cann't write value in File (In destroy) " + e);
		}
		System.out.println("In destroy");
	}
} 