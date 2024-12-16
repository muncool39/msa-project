package com.msa.user.shipper.domain.model;

import com.msa.user.domain.model.BaseEntity;
import com.msa.user.shipper.domain.model.type.ShipperType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "p_shipper")
public class Shipper extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hub_id")
    private String hubId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ShipperType type;

    @Column(name = "delivery_order")
    private Integer deliveryOrder;

    public void updateShipper(String hubId, ShipperType type) {
        this.hubId = hubId;
        this.type = type;
    }
}
