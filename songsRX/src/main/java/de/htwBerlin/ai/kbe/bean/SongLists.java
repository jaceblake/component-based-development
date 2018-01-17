package de.htwBerlin.ai.kbe.bean;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songlists")
@Entity
@Table(name = "SongLists")
public class SongLists {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Integer id;
	private String owner;
	private boolean isPublic;
	private ArrayList<Integer> songs;

	public SongLists() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public void setPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}

	public ArrayList<Integer> getSongs() {
		return songs;
	}

	public void setSongs(ArrayList<Integer> songs) {
		this.songs = songs;
	}

}
