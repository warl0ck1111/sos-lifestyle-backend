package com.example.sosinventory.product.color;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;


    @Override
    public List<Color> getAllColors() {
        return colorRepository.findAll();
    }

    @Override
    public Color getColorById(long id) {
        return findColorById(id);
    }

    @Override
    public Color createColor(ColorRequest colorRequest) {

        log.info("createColor/colorRequest:{}", colorRequest);
        Color color = new Color();
        BeanUtils.copyProperties(colorRequest,color);
        return colorRepository.save(color);
    }

    @Override
    public Color updateColor(long id, ColorRequest colorRequest) {
        Color color = findColorById(id);
        BeanUtils.copyProperties(colorRequest, color);
        return colorRepository.save(color);
    }

    @Override
    public void deleteColor(long id) {
        colorRepository.deleteById(id);
    }



    private Color findColorById(long id){
        return colorRepository.findById(id).orElseThrow(()-> new ColorException("Color not found"));
    }


}
