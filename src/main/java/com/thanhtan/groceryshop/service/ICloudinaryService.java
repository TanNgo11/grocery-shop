package com.thanhtan.groceryshop.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ICloudinaryService {
     Map upload(MultipartFile file);
}
