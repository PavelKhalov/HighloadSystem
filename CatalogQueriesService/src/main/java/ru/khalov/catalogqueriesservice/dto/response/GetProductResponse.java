package ru.khalov.catalogqueriesservice.dto.response;

import ru.khalov.catalogqueriesservice.entiry.Product;

public record GetProductResponse(
        Product product
) {
}
