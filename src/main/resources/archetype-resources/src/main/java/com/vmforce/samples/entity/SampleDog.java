#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SampleDog {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	private String name;
	private int age;
	
	@Enumerated(EnumType.ORDINAL)
	private SampleBreed breed;
	
	@ManyToOne
	private SampleDogOwner dogOwner;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	public SampleBreed getBreed() {
		return breed;
	}

	public void setBreed(SampleBreed breed) {
		this.breed = breed;
	}

	public SampleDogOwner getDogOwner() {
		return dogOwner;
	}

	public void setDogOwner(SampleDogOwner dogOwner) {
		this.dogOwner = dogOwner;
	}

}
