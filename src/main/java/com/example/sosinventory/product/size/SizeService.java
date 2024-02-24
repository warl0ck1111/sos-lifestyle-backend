package com.example.sosinventory.product.size;

import java.util.List;

public interface SizeService {

    List<Size> getAllSizes();

    Size getSizeById(long id);

    Size createSize(SizeRequest categoryRequest);

    Size updateSize(long id, SizeRequest categoryRequest);

    void deleteSize(long id);
}
