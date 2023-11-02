package com.greatminds.ayni.management.interfaces.rest;

import com.greatminds.ayni.management.domain.model.queries.GetAllCropsByProductIdQuery;
import com.greatminds.ayni.management.domain.model.queries.GetAllCropsQuery;
import com.greatminds.ayni.management.domain.model.queries.GetCropByIdQuery;
import com.greatminds.ayni.management.domain.model.queries.GetCropByProductIdQuery;
import com.greatminds.ayni.management.domain.model.valueobjects.ProductId;
import com.greatminds.ayni.management.domain.services.CropCommandService;
import com.greatminds.ayni.management.domain.services.CropQueryService;
import com.greatminds.ayni.management.interfaces.rest.resources.CreateCropResource;
import com.greatminds.ayni.management.interfaces.rest.resources.CropResource;
import com.greatminds.ayni.management.interfaces.rest.transform.CreateCropCommandFromResourceAssembler;
import com.greatminds.ayni.management.interfaces.rest.transform.CropResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/crops", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Crops", description = "Crops Management Endpoints")
public class CropController {
    private final CropQueryService cropQueryService;
    private final CropCommandService cropCommandService;

    public CropController(CropQueryService cropQueryService, CropCommandService cropCommandService) {
        this.cropQueryService = cropQueryService;
        this.cropCommandService = cropCommandService;
    }

    @PostMapping
    public ResponseEntity<CropResource> createCrop(@RequestBody CreateCropResource resource){
        var createCropCommand = CreateCropCommandFromResourceAssembler.toCommandFromResource(resource);

        var cropId = cropCommandService.handle(createCropCommand);

        if(cropId == 0L) {
            return ResponseEntity.badRequest().build();
        }

        var getCropIdByQuery = new GetCropByIdQuery(cropId);
        var crop = cropQueryService.handle(getCropIdByQuery);

        if(crop.isEmpty()){
            return ResponseEntity.badRequest().build();
        }

        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return new ResponseEntity<>(cropResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CropResource>> getAllCrops() {
        var getAllCropsQuery = new GetAllCropsQuery();
        var crops = cropQueryService.handle(getAllCropsQuery);
        var profilesResources= crops.stream().map(CropResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(profilesResources);
    }

    @GetMapping("/{cropId}")
    public ResponseEntity<CropResource> getCropById(@PathVariable Long cropId) {
        var getCropByIdQuery = new GetCropByIdQuery(cropId);
        var crop = cropQueryService.handle(getCropByIdQuery);

        if(crop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return ResponseEntity.ok(cropResource);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<CropResource>> getAllCropsByProductId(@PathVariable Long productId) {
        var getAllCropsByProductIdQuery = new GetAllCropsByProductIdQuery(productId);
        var crops = cropQueryService.handle(getAllCropsByProductIdQuery);
        var cropsResource= crops.stream().map(CropResourceFromEntityAssembler::toResourceFromEntity).collect(Collectors.toList());
        return ResponseEntity.ok(cropsResource);
    }

    /*@GetMapping("/product/{productId}")
    public ResponseEntity<CropResource> getCropByProductId(@PathVariable Long productId) {
        var getCropByProductIdQuery = new GetCropByProductIdQuery(productId);
        var crop = cropQueryService.handle(getCropByProductIdQuery);

        if(crop.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        var cropResource = CropResourceFromEntityAssembler.toResourceFromEntity(crop.get());
        return ResponseEntity.ok(cropResource);
    }*/
}
