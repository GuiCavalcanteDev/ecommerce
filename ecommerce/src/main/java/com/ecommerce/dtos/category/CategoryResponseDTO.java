package com.ecommerce.dtos.category;

import org.springframework.http.HttpStatus;

public record CategoryResponseDTO(HttpStatus httpStatus, String message) {
}
