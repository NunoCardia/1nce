package com.once.demoproject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "TRIANGLE_INFO")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TriangleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_side")
    private BigDecimal firstSide;

    @Column(name = "second_side")
    private BigDecimal secondSide;

    @Column(name = "third_side")
    private BigDecimal thirdSide;

    @Column(name = "category")
    private String category;

    @CreatedDate
    @Column(name = "timestamp")
    private LocalDateTime time;
}
