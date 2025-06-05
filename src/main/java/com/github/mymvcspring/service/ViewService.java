package com.github.mymvcspring.service;

import com.github.mymvcspring.repository.Image;
import com.github.mymvcspring.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ImageRepository imageRepository;

    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
}
