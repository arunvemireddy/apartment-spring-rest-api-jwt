package com.example.Apartment.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.FlatsDTO;
import com.example.Apartment.DTO.OwnerDetailsDTO;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.OwnerDetails;

@Repository
public interface ApartmentDao extends JpaRepository<OwnerDetails, Long> {

	@Query(value = "SELECT apartment_name,noof_flats FROM apartment", nativeQuery = true)
	List<ApartmentDTO> fetchDetails();
	
	@Query(value = "select * from apartment", nativeQuery = true)
	List<Apartment> getApDetails();
	
	@Query(value = "select name from owner_details where id=1", nativeQuery = true)
	List<String> getOwnerDetails();

	@Modifying
    @Transactional
	@Query(value = "delete from apartment where ap_id=?1",nativeQuery = true)
	void delete(int apId);
	
	@Query(value = "select * from owner_details", nativeQuery = true)
	List<OwnerDetails> fetchOwnerDetails();
	
	@Query(value = "select flatno from owner_details", nativeQuery = true)
	List<Integer> fetFlatno();
	
	@Query(value = "select flatno from owner_details where flatno Like ?1%", nativeQuery = true)
	List<Integer> fetchFlatno(int flatno);
	
	@Query(value = "select * from owner_details where flatno Like ?1%", nativeQuery = true)
	List<OwnerDetails> getOwnerDetails(int flatno);
}
