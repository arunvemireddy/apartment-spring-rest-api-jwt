package com.example.Apartment.Dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Apartment.DTO.ApartmentDTO;
import com.example.Apartment.DTO.OwnerNameDTO;
import com.example.Apartment.Entity.Apartment;
import com.example.Apartment.Entity.OwnerDetails;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Repository
public interface OwnerDetailsDao extends JpaRepository<OwnerDetails, Integer> {

	@Query(value = "SELECT apartment_name,noof_flats FROM apartment", nativeQuery = true)
	List<ApartmentDTO> fetchDetails();

	@Query(value = "select * from apartment", nativeQuery = true)
	List<Apartment> getApDetails();

	@Query(value = "select name from owner_details where id=1", nativeQuery = true)
	List<String> getOwnerDetails();

	@Modifying
	@Transactional
	@Query(value = "delete from apartment where ap_id=?1", nativeQuery = true)
	void delete(int apId);

	@Query("select m from OwnerDetails m")
	List<OwnerDetails> fetchOwnerDetails(Pageable pageable);

	@Query(value = "select count(flatno) from owner_details  where flatno Like ?1%", nativeQuery = true)
	List<Integer> fetFlatno(int flatno);

	@Query(value = "select flatno from owner_details where flatno Like ?1%", nativeQuery = true)
	List<Integer> fetchFlatno(int flatno);

	@Query(value = "select * from owner_details where flatno Like ?1%", nativeQuery = true)
	List<OwnerDetails> getOwnerDetails(int flatno);

	@Query("SELECT new com.example.Apartment.DTO.OwnerNameDTO(u.name)  FROM OwnerDetails u")
	List<OwnerNameDTO> getOwnerName();

	@Query("select od from OwnerDetails od where od.name = :userName")
	List<OwnerDetails> getOwnerDetailsByName(@Param("userName") String userName);

	@Query(value = "select m.flatno from owner_details m where m.flatno like ?1%", nativeQuery = true)
	List<Integer> getOwnersByFlat(int flatno);

	@Query(value = "select m.name from owner_details m where m.flatno = ?1", nativeQuery = true)
	List<String> getOwnersListByFlat(int flatno);

	OwnerDetails findByflatno(int flatno);
}
