package com.msa.hub.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "p_hub",
        uniqueConstraints = {
        @UniqueConstraint(name = "UK_HUB_NAME", columnNames = "name")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hub extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    @Column(name="id")
    private String id;

    @Column(name="name", nullable=false)
    private String name;

    @Column(name="city", nullable=false)
    private String city;

    @Column(name="district", nullable=false)
    private String district;

    @Column(name="street_name", nullable=false)
    private String streetName;

    @Column(name="street_number", nullable=false)
    private String streetNumber;

    @Column(name="address_detail", nullable=false)
    private String addressDetail;

    @Column(name="latitude", nullable=false)
    private Double latitude;

    @Column(name="longitude", nullable=false)
    private Double longitude;

    @Column(name="manager_id")
    private Long managerId;

    @OneToMany(mappedBy = "sourceHubId")
    private List<HubRoute> sourceHubRoutes = new ArrayList<>();

    @OneToMany(mappedBy = "destinationHubId")
    private List<HubRoute> destinationHubRoutes = new ArrayList<>();

    @Builder(access = AccessLevel.PRIVATE)
    private Hub(String name, String city, String district, String streetName, String streetNumber, String addressDetail,
               Double latitude, Double longitude, Long managerId) {
        this.name = name;
        this.city = city;
        this.district = district;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.addressDetail = addressDetail;
        this.latitude = latitude;
        this.longitude = longitude;
        this.managerId = managerId;
    }

    public void setManager(Long managerId) {
        this.managerId = managerId;
    }

    public static Hub createBy(
            String name, String city,
            String district, String streetName, String streetNumber, String addressDetail,
            Double latitude, Double longitude
    ) {
        return Hub.builder()
                .name(name)
                .city(city)
                .district(district)
                .streetName(streetName)
                .streetNumber(streetNumber)
                .addressDetail(addressDetail)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

}
