package ru.khalov.catalogqueriesservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.khalov.catalogqueriesservice.dto.response.GetAllProductResponse;
import ru.khalov.catalogqueriesservice.dto.response.GetProductResponse;
import ru.khalov.catalogqueriesservice.entiry.Product;
import ru.khalov.catalogqueriesservice.event.CreateProductEvent;
import ru.khalov.catalogqueriesservice.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @KafkaListener(topics = "catalog-topic")
    public void saveProduct(CreateProductEvent event){
        Product product = new Product();

        product.setId(event.productId());
        product.setTitle(event.title());
        product.setSellerId(event.sellerId());
        product.setPriceOneProduct(event.priceOneProduct());
        product.setQuantity(event.quantity());

        productRepository.saveAndFlush(product);
    }

    public GetProductResponse getProduct(Long sellerId, Integer productId){
        Product product = productRepository.findProductBySellerIdAndProductId(sellerId, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with id: " + productId +
                        ", and sellerId: " + sellerId + " not found"));

        return new GetProductResponse(product);
    }

    public GetAllProductResponse getAllProduct(Long sellerId){
        List<Product> products = productRepository.findAllProductBySellerId(sellerId);

        if(products.isEmpty() || products == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "this seller dont have products");
        }

        return new GetAllProductResponse(products);
    }
}
