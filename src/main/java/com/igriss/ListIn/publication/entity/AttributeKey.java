package com.igriss.ListIn.publication.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "attribute_keys")
public class AttributeKey {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attribute_id")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String helperText;

    private String subHelperText;

    @Column(nullable = false)
    private String widgetType;

    private String subWidgetType;

    @Column(nullable = false)
    private String dataType;

    @Column(nullable = false)
    private String filterText;

    @Column(nullable = false)
    private String subFilterText;

    @Column(nullable = false)
    private String filterWidgetType;

    @Column(nullable = false)
    private String subFilterWidgetType;
}