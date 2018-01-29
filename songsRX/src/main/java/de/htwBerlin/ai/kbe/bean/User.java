package de.htwBerlin.ai.kbe.bean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "user")
@Entity
@Table(name = "User")
public class User {

	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Id
	private String userId;
	private String lastName;
	private String firstName;
	
    @OneToMany(mappedBy="user", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<SongLists> songlists ;

	
    public List<SongLists> getSonglists() {
        if(songlists == null) {
        	songlists =  new ArrayList<SongLists>();
        }
        return songlists;
    }

    public void setSonglists(List<SongLists> songlists) {
        this.songlists = songlists;
        // Works for JSON, but not for XML
        if(songlists != null) {
            this.songlists.forEach(a-> a.setUser(this));
        }
    }
    
    public void addAddress(SongLists songli) {
        if(songlists == null) {
        	songlists = new ArrayList<SongLists>();
        }
        songli.setUser(this);
        this.songlists.add(songli);
    }

	public User(Integer id, String userId, String lastName, String firstName) {
		super();
		this.id = id;
		this.userId = userId;
		this.lastName = lastName;
		this.firstName = firstName;
	}

	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userId=" + userId + ", lastName=" + lastName + ", firstName=" + firstName + "]";
	}

}
