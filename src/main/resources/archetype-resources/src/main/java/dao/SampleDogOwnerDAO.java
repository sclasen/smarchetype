#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;
//TEST
import java.util.List;

import ${package}.entity.SampleDogOwner;

public interface SampleDogOwnerDAO {
	
	List<SampleDogOwner> getAllDogOwners();  
}
