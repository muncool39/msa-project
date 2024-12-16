package com.msa.user.shipper.domain.repository;

import com.msa.user.shipper.domain.model.Shipper;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipperRepository extends JpaRepository<Shipper, UUID> {

    // 최대 deliveryOrder 값 조회
    @Query("SELECT MAX(s.deliveryOrder) FROM Shipper s WHERE s.isDeleted = false")
    Integer findMaxDeliveryOrder();
}
