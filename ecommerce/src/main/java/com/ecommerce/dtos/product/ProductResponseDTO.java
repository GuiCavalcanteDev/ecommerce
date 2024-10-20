package com.ecommerce.dtos.product;

import java.util.Date;

public record ProductResponseDTO(String id, String name, String categoryId, String description, int priceInCents, int stockQuantity, String sku, byte[] image, String createdBy, Date createdOn) {
}
