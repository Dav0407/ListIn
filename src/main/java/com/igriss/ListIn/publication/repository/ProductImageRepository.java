package com.igriss.ListIn.publication.repository;

import com.igriss.ListIn.publication.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
}