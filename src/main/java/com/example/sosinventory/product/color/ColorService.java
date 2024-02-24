package com.example.sosinventory.product.color;

import java.util.List;

public interface ColorService {

    List<Color> getAllColors();

    Color getColorById(long id);

    Color createColor(ColorRequest categoryRequest);

    Color updateColor(long id, ColorRequest categoryRequest);

    void deleteColor(long id);
}
