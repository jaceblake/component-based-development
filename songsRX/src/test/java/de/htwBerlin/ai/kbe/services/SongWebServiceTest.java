package de.htwBerlin.ai.kbe.services;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.htwBerlin.ai.kbe.bean.Song;
import de.htwBerlin.ai.kbe.services.SongWebService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SongWebServiceTest extends JerseyTest {
			
    @Override
    protected Application configure() {
        return new ResourceConfig(SongWebService.class);
    }

  //Get all test returns http 200
    @Test
    public void getAllSongsReturns200() {
    	Response response = target("/songs").request().get();
    	Assert.assertEquals(200, response.getStatus());
    }
    
    //check default content type
    
    @Test
    public void getSongDefaultContentTypeShouldBeXML() {
        String response = target("/songs/82").request().get(String.class);
        //System.out.println(response);
        Assert.assertTrue(response.startsWith("<?xml"));
    }
    

    //test if valid song returns
    @Test
    public void getSongWithValidIdShouldReturnSong() {
        Song song = target("/songs/82").request(MediaType.APPLICATION_JSON).get(Song.class);
        //System.out.println(song);
        Assert.assertEquals(82, song.getId().intValue());
    }
    
    //test if non existing song returns 404
    @Test
    public void getSongWithNonExistingIdShouldReturn404() {
        Response response = target("/songs/827").request().get();
        Assert.assertEquals(404, response.getStatus());
    }
    
    //test if post successful
    @Test
    public void createSongShouldReturn201AndID() {
    		Song s = new Song();
    		s.setAlbum("Geeks");
    		s.setTitle("Geek meets Greek");
    		s.setArtist("Meeky Mike");
    		s.setReleased(2018);
        Response response = target("/songs").request().post(Entity.xml(s));
        Assert.assertEquals(201, response.getStatus());

    }
    
    //test if post fails
    
    @Test
    public void createSongShouldReturn404() {
    		Song s = new Song();
    		s.setAlbum("Geeks");
 
    		s.setArtist("Meeky Mike");
    		s.setReleased(2018);
        Response response = target("/songs").request().post(Entity.xml(s));
        Assert.assertEquals(404, response.getStatus());
     
    }
    
    //test if put success
    
    @Test
    public void PutSongShouldReturn202() {
    		Song s = new Song();
    		s.setAlbum("Geeks");
    		s.setTitle("Geek meets Greek");
    		s.setArtist("Meeky Mike");
    		s.setReleased(2018);
        Response response = target("/songs/82").request().put(Entity.xml(s));
        Assert.assertEquals(202, response.getStatus());

    }
    
    //test if put fails
    @Test
    public void PutSongShouldReturn404() {
    		Song s = new Song();
    		s.setAlbum("Geeks");
    		s.setTitle("Geek meets Greek");
    		s.setArtist("Meeky Mike");
    		s.setReleased(2018);
        Response response = target("/songs/645").request().put(Entity.xml(s));
        Assert.assertEquals(404, response.getStatus());

    }
    
    
    //test if delete success
    
    @Test
    public void DeleteSongShouldReturn204() {
        Response response = target("/songs/64").request().delete() ;
        Assert.assertEquals(204, response.getStatus());

    }
    
    //test if delete fails
    @Test
    public void DeleteSongShouldReturn404() {
    		Song s = new Song();
    		s.setAlbum("Geeks");
    		s.setTitle("Geek meets Greek");
    		s.setArtist("Meeky Mike");
    		s.setReleased(2018);
        Response response = target("/songs/645").request().delete();
        Assert.assertEquals(404, response.getStatus());

    }
    
    

    
    
}
