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

    @Column(nullable = false, name = "name_uz")
    private String nameUz;

    @Column(nullable = false, name = "name_ru")
    private String nameRu;

    @Column(nullable = false)
    private String helperText;

    @Column(nullable = false, name = "helper_text_uz")
    private String helperTextUz;

    @Column(nullable = false, name = "helper_text_ru")
    private String helperTextRu;

    private String subHelperText;

    @Column(name = "sub_helper_text_uz")
    private String subHelperTextUz;

    @Column(name = "sub_helper_text_ru")
    private String subHelperTextRu;

    @Column(nullable = false)
    private String widgetType;

    private String subWidgetType;

    @Column(nullable = false)
    private String dataType;

    @Column(nullable = false)
    private String filterText;

    @Column(nullable = false, name = "filter_text_uz")
    private String filterTextUz;

    @Column(nullable = false, name = "filter_text_ru")
    private String filterTextRu;

    @Column(nullable = false)
    private String subFilterText;

    @Column(nullable = false, name = "sub_filter_text_uz")
    private String subFilterTextUz;

    @Column(nullable = false, name = "sub_filter_text_ru")
    private String subFilterTextRu;

    @Column(nullable = false)
    private String filterWidgetType;

    @Column(nullable = false)
    private String subFilterWidgetType;
}