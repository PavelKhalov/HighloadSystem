package ru.khalov.catalogcommandsservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ru.khalov.catalogcommandsservice.dto.request.CreateProductRequest;
import ru.khalov.catalogcommandsservice.dto.request.UpdateProductRequest;
import ru.khalov.catalogcommandsservice.dto.response.UpdateProductResponse;
import ru.khalov.catalogcommandsservice.entity.Product;
import ru.khalov.catalogcommandsservice.event.CreateProductEvent;
import ru.khalov.catalogcommandsservice.event.DeleteProductEvent;
import ru.khalov.catalogcommandsservice.event.UpdateProductEvent;
import ru.khalov.catalogcommandsservice.repositroy.ProductRepository;
import ru.khalov.catalogcommandsservice.dto.response.CreateProductResponse;
import ru.khalov.catalogcommandsservice.util.error.ProductNotFoundException;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public CreateProductResponse createProduct(Long sellerId, CreateProductRequest request){
        Product product = new Product(
                request.title(),
                sellerId,
                request.priceOneProduct(),
                request.quantity()
        );

        Product saved = productRepository.saveAndFlush(product);

        var createEvent = new CreateProductEvent(
                UUID.randomUUID(),
                saved.getId(),
                saved.getTitle(),
                sellerId,
                saved.getQuantity(),
                saved.getPriceOneProduct()
        );


        ProducerRecord record = new ProducerRecord<>("catalog-topic", String.valueOf(product.getId()), createEvent);
        CompletableFuture<SendResult<String, CreateProductEvent>> future = kafkaTemplate.send(record);
        future.whenComplete((res, exception) -> {
            if(exception != null){
                log.error("Send message failed");
            } else {
                log.debug("Send message successful");
            }
        });

        return new CreateProductResponse("Product saved successful");
    }

    public UpdateProductResponse updateProduct(Long sellerId, UpdateProductRequest request){
        Product product = productRepository.findProductBySellerIdAndId(sellerId, request.id())
                .orElseThrow(() -> new ProductNotFoundException("Product with sellerId: " + sellerId + ", and productId: " + request.id() +  " not found"));

        product.setTitle(request.title());
        product.setPriceOneProduct(request.priceOneProduct());

        Product saved = productRepository.saveAndFlush(product);

        var updateEvent = new UpdateProductEvent(
                UUID.randomUUID(),
                saved.getId(),
                saved.getTitle(),
                sellerId,
                saved.getPriceOneProduct()
        );

        ProducerRecord record = new ProducerRecord<>("catalog-topic", String.valueOf(product.getId()), updateEvent);

        CompletableFuture<SendResult<String, UpdateProductEvent>> future = kafkaTemplate.send(record);
        future.whenComplete((res, exception) -> {
            if(exception != null){
                log.error("Send message failed");
            } else {
                log.debug("Send message successful");
            }
        });
        return new UpdateProductResponse("Product updated successful");
    }

    public String deleteProduct(Long sellerId, Integer productId){
        Product product = productRepository.findProductBySellerIdAndId(sellerId, productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with sellerId: " + sellerId + ", and productId: " + productId +  " not found"));

        var deleteEvent = new DeleteProductEvent(
                UUID.randomUUID(),
                sellerId,
                productId
        );

        ProducerRecord record = new ProducerRecord<>("catalog-topic", String.valueOf(product.getId()), deleteEvent);
        CompletableFuture<SendResult<String, DeleteProductEvent>> future = kafkaTemplate.send(record);
        future.whenComplete((res, exception) -> {
            if(exception != null){
                log.error("Send message failed");
            } else {
                log.debug("Send message successful");
            }
        });

        productRepository.deleteById(productId);
        productRepository.flush();

        return "Product deleted successful";
    }
}
