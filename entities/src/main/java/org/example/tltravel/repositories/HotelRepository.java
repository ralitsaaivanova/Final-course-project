package org.example.tltravel.repositories;

import org.example.tltravel.entities.ExtrasEntity;
import org.example.tltravel.entities.FeedingTypeEntity;
import org.example.tltravel.entities.HotelEntity;
import org.example.tltravel.entities.HotelExtrasEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<HotelEntity,Long> {
    @Query("SELECT  h from HOTEL h WHERE h.isActive=true ")
    Page<HotelEntity> findAllActive(Pageable pageable);

    @Query("SELECT h from HOTEL h WHERE h.id = :id AND h.isActive=true")
    Optional<HotelEntity> findByIdAndIsActive(@Param("id")Long id);

    @Query("SELECT  h from HOTEL h WHERE h.isActive=true and h.partnerId=:partnerId ")
    Page<HotelEntity> findAllByPartnerIdActive(@Param("partnerId") Long partnerId,Pageable pageable);

    @Query("SELECT h from HOTEL h WHERE h.locationId = :id")
    Page<HotelEntity> findAllByLocation(Long id, Pageable pageable);



    @Query("""
             SELECT e FROM HOTEL h
                        JOIN HOTELEXTRAS he ON he.hotel_id = h.id
                        JOIN EXTRAS e ON e.id = he.extras_id
                        WHERE h.id = :id AND he.isActive = true
            """
            )
    List<ExtrasEntity> findAllByExtraIdAndActive(@Param("id")Long id);

    @Query("""
        SELECT h FROM HOTEL h
        WHERE EXISTS (
            SELECT he.hotel_id FROM HOTELEXTRAS  he 
            WHERE he.isActive = true
            AND he.extras_id in :ids
            AND he.hotel_id = h.id
            group by he.hotel_id
            having count(he.extras_id) = :size 
            
        )
 
    """)
    Page<HotelEntity> findAllByExtrasIdAndIsActive(List<Long> ids,@Param("size")Integer size, Pageable pageable);



    @Query("""
             SELECT e FROM HOTEL h
                        JOIN HOTELFEEDINGTYPES hf ON hf.hotel_id = h.id
                        JOIN FEEDINGTYPE e ON e.id = hf.feedingType_id
                        WHERE h.id = :id AND hf.isActive = true
            """
    )
    List<FeedingTypeEntity> findAllByHotelIdAndActive(@Param("id")Long id);



    @Query("""
        SELECT h FROM HOTEL h
        WHERE EXISTS (
            SELECT he.hotel_id FROM HOTELFEEDINGTYPES  he 
            WHERE he.isActive = true
            AND he.feedingType_id in :ids
            AND he.hotel_id = h.id
            group by he.hotel_id
            having count(he.feedingType_id) = :size 
            
        )
 
    """)
    Page<HotelEntity> findAllByFeedingTypesIdAndIsActive(List<Long> ids,@Param("size")Integer size, Pageable pageable);
}
