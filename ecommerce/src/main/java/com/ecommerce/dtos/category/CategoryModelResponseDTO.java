package com.ecommerce.dtos.category;

import java.util.Date;

public record CategoryModelResponseDTO(String id, String name, String description, boolean status, String createdBy, Date createdOn) {
}
