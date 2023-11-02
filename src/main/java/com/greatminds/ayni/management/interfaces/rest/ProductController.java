package com.greatminds.ayni.management.interfaces.rest;

import com.greatminds.ayni.management.domain.model.queries.GetAllProductsQuery;
import com.greatminds.ayni.management.domain.model.queries.GetProductByIdQuery;
import com.greatminds.ayni.management.domain.services.ProductCommandService;
import com.greatminds.ayni.management.domain.services.ProductQueryService;
import com.greatminds.ayni.management.interfaces.rest.resources.CreateProductResource;
import com.greatminds.ayni.management.interfaces.rest.resources.ProductResource;
import com.greatminds.ayni.management.interfaces.rest.transform.CreateProductCommandFromResourceAssembler;
import com.greatminds.ayni.management.interfaces.rest.transform.ProductResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api/v1/products", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Product", description = "Product Management Endpoints")
public class ProductController {
    private final ProductQueryService productQueryService;
    private final ProductCommandService productCommandService;

    public ProductController(ProductQueryService productQueryService, ProductCommandService productCommandService) {
        this.productQueryService = productQueryService;
        this.productCommandService = productCommandService;
    }

    @PostMapping
    public ResponseEntity<ProductResource> createProduct(@RequestBody CreateProductResource resource){
        var createProductCommand = CreateProductCommandFromResourceAssembler.toCommandFromResource(resource);

        var productId = productCommandService.handle(createProductCommand);

        if(productId == 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getTransactionIdByQuery = new GetProductByIdQuery(productId);
        var transaction = productQueryService.handle(getTransactionIdByQuery);

        if(transaction.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var transactionResource = ProductResourceFromEntityAssembler.toResourceFromEntity(transaction.get());
        return new ResponseEntity<>(transactionResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductResource>> getAllProducts() {
        var getAllProductsQuery = new GetAllProductsQuery();
        var products = productQueryService.handle(getAllProductsQuery);
        var profilesResources= products.stream().map(ProductResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(profilesResources);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResource> getProductById(@PathVariable Long productId) {
        var getProductByIdQuery = new GetProductByIdQuery(productId);
        var product = productQueryService.handle(getProductByIdQuery);

        if(product.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var productResource = ProductResourceFromEntityAssembler.toResourceFromEntity(product.get());
        return ResponseEntity.ok(productResource);
    }
}
