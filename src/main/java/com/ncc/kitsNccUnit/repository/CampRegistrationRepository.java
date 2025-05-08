package com.ncc.kitsNccUnit.repository;

import com.ncc.kitsNccUnit.entity.Cadet;
import com.ncc.kitsNccUnit.entity.CampRegistrations;
import com.ncc.kitsNccUnit.entity.Camps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CampRegistrationRepository extends JpaRepository<CampRegistrations, Integer> {
    List<CampRegistrations> findByCampId(int campId);
    List<CampRegistrations> findByCadetId(int cadetId);
    List<CampRegistrations> findByCampIdAndIsAcceptedTrue(int campId);
    List<CampRegistrations> findByCampIdAndIsAcceptedFalse(int campId);
    boolean existsByCampAndCadet(Camps camp, Cadet cadet);

    @Modifying
    @Transactional
    @Query(value = "UPDATE camp_registration SET is_accepted = :isAccepted WHERE id = :registrationId", nativeQuery = true)
    void updateIsAccepted(@Param("registrationId") int registrationId, @Param("isAccepted") boolean isAccepted);

}
