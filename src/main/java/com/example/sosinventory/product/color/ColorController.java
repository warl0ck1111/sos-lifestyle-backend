package com.example.sosinventory.product.color;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/colors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ColorController {
    private final ColorService colorService;

    @GetMapping
    public List<Color> getAllCategories() {
        return colorService.getAllColors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getColorById(@PathVariable long id) {
        Color color = colorService.getColorById(id);
        return ResponseEntity.ok(color);
    }

    @PostMapping
    public ResponseEntity<Color> createColor(@RequestBody ColorRequest colorRequest) {
        Color createdColor = colorService.createColor(colorRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdColor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> updateColor(@PathVariable long id, @RequestBody ColorRequest colorRequest) {
        Color updatedColor = colorService.updateColor(id, colorRequest);
        return ResponseEntity.ok(updatedColor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColor(@PathVariable long id) {
        colorService.deleteColor(id);
        return ResponseEntity.noContent().build();
    }
}
