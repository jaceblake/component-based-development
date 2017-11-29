package de.htwBerlin.ai.kbe.songWebStore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SongsStoreServletTest {

	private SongsStoreServlet servlet;
	private MockServletConfig config;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;

	private ObjectMapper objectMapper;


	@Before
	public void setUp() throws ServletException {
		objectMapper = new ObjectMapper();
		response = new MockHttpServletResponse();
		request = new MockHttpServletRequest();
		config = new MockServletConfig();
		servlet = new SongsStoreServlet();
		servlet.init(config);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void playingWithJackson() throws IOException {
		// songs.json and testSongs.json contain songs from this Top-10:
		// http://time.com/collection-post/4577404/the-top-10-worst-songs/

		// Read a JSON file and create song list:
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("testSongs.json");

		List<Song> songList = (List<Song>) objectMapper.readValue(input, new TypeReference<List<Song>>() {
		});

		// write a list of objects to a JSON-String with prettyPrinter
		String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(songList);

		// write a list of objects to an outputStream in JSON format
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new FileOutputStream("output.json"), songList);

		// Create a song and write to JSON
		Song song = new Song(null, "titleXX", "artistXX", "albumXX", 1999);

		byte[] jsonBytes = objectMapper.writeValueAsBytes(song);

		// Read JSON from byte[] into Object
		Song newSong = (Song) objectMapper.readValue(jsonBytes, Song.class);
		assertEquals(song.getTitle(), newSong.getTitle());
		assertEquals(song.getArtist(), newSong.getArtist());
	}

	@Test
	public void initShouldLoadSongList() throws IOException, ServletException {

		servlet.init(config);
		assertEquals(false, servlet.isEmptySongStore());
	}

	@Test
	public void doGetWitWrongParameter() throws IOException {

		request.addParameter("r", "3");

		servlet.doGet(request, response);
		String str = response.getContentAsString().trim();
		assertEquals("Wrong parameter Name !!!", str);
	}

	@Test
	public void doGetInvalidSongId() throws IOException {

		request.addParameter("songId", ")");
		servlet.doGet(request, response);
		String str = response.getContentAsString().trim();
		assertEquals("Expected Value is Integer", str);
	}

	@Test
	public void doGetWithoutParameter() throws IOException {

		servlet.doGet(request, response);
		String str = response.getContentAsString().trim();
		assertEquals("Expected minimum one Parameter !!!", str);
	}

	@Test
	public void doPosthasEmptyBody() throws  IOException{
		String badJson = "";
		request.setContent(badJson.getBytes());
		servlet.doPost(request,response);
		String str = response.getContentAsString().trim();
		assertEquals("please don't send any Empty value !!!", str);
	}
	
	@Test
	public void doPosthasNullBody() throws  IOException{
		String badJson = "null";
		request.setContent(badJson.getBytes());
		servlet.doPost(request,response);
		String str = response.getContentAsString().trim();
		assertEquals("please don't send any null value !!!", str);
	}
	
	@Test
	public void doPosthasBadPayload() throws  IOException{
		String badJson = "hi";
		request.setContent(badJson.getBytes());
		servlet.doPost(request,response);
		String str = response.getContentAsString().trim();
		System.out.println(str);
		assertEquals("Please send a valid song Object !!!", str);
	}
	
	
}
