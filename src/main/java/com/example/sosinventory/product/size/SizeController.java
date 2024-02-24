package com.example.sosinventory.product.size;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sizes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SizeController {
    private final SizeService sizeService;

    @GetMapping
    public List<Size> getAllCategories() {
        return sizeService.getAllSizes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Size> getSizeById(@PathVariable long id) {
        Size size = sizeService.getSizeById(id);
        return ResponseEntity.ok(size);
    }

    @PostMapping
    public ResponseEntity<Size> createSize(@RequestBody SizeRequest sizeRequest) {
        Size createdSize = sizeService.createSize(sizeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSize);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Size> updateSize(@PathVariable long id, @RequestBody SizeRequest sizeRequest) {
        Size updatedSize = sizeService.updateSize(id, sizeRequest);
        return ResponseEntity.ok(updatedSize);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSize(@PathVariable long id) {
        sizeService.deleteSize(id);
        return ResponseEntity.noContent().build();
    }
}
