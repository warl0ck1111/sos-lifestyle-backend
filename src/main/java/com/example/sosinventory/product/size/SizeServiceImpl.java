package com.example.sosinventory.product.size;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;


    @Override
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }

    @Override
    public Size getSizeById(long id) {
        return findSizeById(id);
    }

    @Override
    public Size createSize(SizeRequest sizeRequest) {

        log.info("createSize/sizeRequest:{}", sizeRequest);
        Size size = new Size();
        BeanUtils.copyProperties(sizeRequest,size);
        return sizeRepository.save(size);
    }

    @Override
    public Size updateSize(long id, SizeRequest sizeRequest) {
        Size size = findSizeById(id);
        BeanUtils.copyProperties(sizeRequest, size);
        return sizeRepository.save(size);
    }

    @Override
    public void deleteSize(long id) {
        sizeRepository.deleteById(id);
    }



    private Size findSizeById(long id){
        return sizeRepository.findById(id).orElseThrow(()-> new SizeException("Size not found"));
    }


}
