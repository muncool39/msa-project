package com.msa.user.domain.repository;

import com.msa.user.domain.model.Shipper;
import com.msa.user.domain.model.ShipperStatus;
import com.msa.user.domain.model.ShipperType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {

    // 최대 deliveryOrder 값 조회
    @Query("SELECT MAX(s.deliveryOrder) FROM Shipper s WHERE s.isDeleted = false")
    Integer findMaxDeliveryOrder();

    // 허브 배송 담당자 N명 조회
    @Query("SELECT s "
            + "FROM Shipper s "
            + "WHERE s.type = :type "
            + "AND s.status = :status "
            + "ORDER BY s.createdAt ASC LIMIT :limit")
    List<Shipper> findTopNByTypeAndStatusOrderByCreatedAtAsc(
            @Param("type") ShipperType type,
            @Param("status") ShipperStatus status,
            @Param("limit") int limit);

    // 업체 배송 담당자 1명 조회
    List<Shipper> findTop1ByTypeAndStatusOrderByCreatedAtAsc(ShipperType type, ShipperStatus status);




}
