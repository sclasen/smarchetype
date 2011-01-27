#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao;

import java.util.List;

import com.vmforce.samples.entity.SampleDogOwner;

public interface SampleDogOwnerDAO {
	
	List<SampleDogOwner> getAllDogOwners();  
}
