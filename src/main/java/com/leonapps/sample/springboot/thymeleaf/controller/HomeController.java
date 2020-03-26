package com.leonapps.sample.springboot.thymeleaf.controller;

import com.leonapps.sample.springboot.thymeleaf.domain.ProductEntity;
import com.leonapps.sample.springboot.thymeleaf.domain.dto.ProductForm;
import com.leonapps.sample.springboot.thymeleaf.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    ProductRepository productRepository;

    @RequestMapping("/")
    public String home(Model model) {
        List<ProductEntity> productEntitys = productRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        List<ProductForm> productFormList = Arrays.asList(modelMapper.map(productEntitys, ProductForm[].class));
        model.addAttribute("products", productFormList);
        return "index";
    }
}
