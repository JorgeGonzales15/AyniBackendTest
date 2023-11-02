package com.greatminds.ayni.management.application.internal.commandservices;

import com.greatminds.ayni.management.domain.model.aggregates.Crop;
import com.greatminds.ayni.management.domain.model.commands.CreateCropCommand;
import com.greatminds.ayni.management.domain.model.queries.GetProductByIdQuery;
import com.greatminds.ayni.management.domain.services.CropCommandService;
import com.greatminds.ayni.management.domain.services.ProductQueryService;
import com.greatminds.ayni.management.infrastructure.persistence.jpa.repositories.CropRepository;
import org.springframework.stereotype.Service;

@Service
public class CropCommandServiceImpl implements CropCommandService {
    private final CropRepository cropRepository;
    private final ProductQueryService productQueryService;

    public CropCommandServiceImpl(CropRepository cropRepository, ProductQueryService productQueryService) {
        this.cropRepository = cropRepository;
        this.productQueryService = productQueryService;
    }

    @Override
    public Long handle(CreateCropCommand command) {
        var getProductByIdQuery=new GetProductByIdQuery(command.productId());
        var product=productQueryService.handle(getProductByIdQuery).orElseThrow();
        var crop = new Crop(command.name(),command.undergrowth(),command.fertilize(),command.oxygenate(),command.line(),command.hole(),command.watered(),command.pestCleaning(),product);
        cropRepository.save(crop);
        return crop.getId();
    }
}
