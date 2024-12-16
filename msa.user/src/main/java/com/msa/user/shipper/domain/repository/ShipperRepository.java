package com.msa.user.shipper.domain.repository;

import com.msa.user.shipper.domain.model.Shipper;
import com.msa.user.shipper.domain.model.type.ShipperType;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShipperRepository extends JpaRepository<Shipper, UUID> {

    // 최대 deliveryOrder 값 조회
    @Query("SELECT MAX(s.deliveryOrder) FROM Shipper s WHERE s.isDeleted = false")
    Integer findMaxDeliveryOrder();

    // 특정 허브와 타입에 맞는 이용 가능한 배송 담당자 검색
    @Query("SELECT s FROM Shipper s "
            + "WHERE s.hubId = :hubId AND s.type = :type "
            + "AND s.isDeleted = false "
            + "ORDER BY s.deliveryOrder ASC")
    List<Shipper> findAvailableShippers(UUID hubId, ShipperType type);

}
