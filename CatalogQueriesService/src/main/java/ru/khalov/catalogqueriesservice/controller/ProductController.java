package ru.khalov.catalogqueriesservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.khalov.catalogqueriesservice.dto.response.GetAllProductResponse;
import ru.khalov.catalogqueriesservice.dto.response.GetProductResponse;
import ru.khalov.catalogqueriesservice.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/catalog")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<GetProductResponse> getProduct(@AuthenticationPrincipal Jwt jwt,
                                                         @PathVariable Integer id){
        Long sellerId = jwt.getClaim("publicId");
        return ResponseEntity.ok().body(productService.getProduct(sellerId, id));
    }


    @GetMapping()
    public ResponseEntity<GetAllProductResponse> getAllProduct(@AuthenticationPrincipal Jwt jwt){
        Long sellerId = jwt.getClaim("publicId");
        return ResponseEntity.ok().body(productService.getAllProduct(sellerId));
    }
}
