package ru.khalov.catalogqueriesservice.dto.response;

import ru.khalov.catalogqueriesservice.entiry.Product;

import java.util.List;

public record GetAllProductResponse (
        List<Product> products
){
}
