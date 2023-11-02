package com.greatminds.ayni.management.application.internal.commandservices;

import com.greatminds.ayni.management.domain.model.commands.CreateProductCommand;
import com.greatminds.ayni.management.domain.model.entities.Product;
import com.greatminds.ayni.management.domain.services.ProductCommandService;
import com.greatminds.ayni.management.infrastructure.persistence.jpa.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductCommandServiceImpl implements ProductCommandService {
    private final ProductRepository productRepository;

    public ProductCommandServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Long handle(CreateProductCommand command) {
        var product = new Product(command.name(), command.description(), command.distance(), command.depth(), command.weather(), command.groundType(), command.season(), command.imageUrl());
        productRepository.save(product);
        return product.getId();
    }
}
