package com.leonapps.sample.springboot.thymeleaf.controller;

import com.leonapps.sample.springboot.thymeleaf.domain.ProductEntity;
import com.leonapps.sample.springboot.thymeleaf.domain.dto.ProductForm;
import com.leonapps.sample.springboot.thymeleaf.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    /**
     * Initialize page form to create product
     * @param model
     * @return '/product/edit' view model
     */
    @RequestMapping(value = "/create")
    public String createForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "product/edit";
    }

    /**
     * Initialize page form to edit product
     * @param productId
     * @param model
     * @return '/product/edit' view model
     */
    @RequestMapping(value = "/edit/{productId}", method = RequestMethod.GET)
    public String editForm(@PathVariable int productId, Model model) {
        if (productId < 1){
            model.addAttribute("message_error","Resource doesn't exist!");
            return "index";
        }
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (!product.isPresent()) {
            model.addAttribute("message_error","Resource doesn't exist!");
            return "index";
        }
        ProductForm form = new ProductForm();
        BeanUtils.copyProperties(product.get(), form);
        model.addAttribute("productForm", form);
        return "product/edit";
    }

    /**
     * Receive product value from page view and save as a new product
     * @param productForm
     * @param redirectAttributes
     * @param validationResult
     * @param model
     * @return '/product/detail' view model
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveForNew(@Valid ProductForm productForm, BindingResult validationResult, RedirectAttributes redirectAttributes, Model model) {
        productForm.setProductId(null);
        if (validationResult.hasErrors()){
            productForm.setProductId(null);
            model.addAttribute("productForm", productForm);
            return "product/edit";
        }
        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productForm, productEntity);
        productEntity.setCreatedBy("Leon Yang");
        productEntity.setUpdatedBy("Leon Yang");

        productRepository.save(productEntity);

        redirectAttributes.addFlashAttribute("message_success"," Saved successfully!");

        return "redirect:/";
    }

    /**
     * Receive product value from page view and update product with as-is product id
     * @param productForm
     * @param redirectAttributes
     * @param validationResult
     * @param model
     * @return '/product/detail' view model
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public String saveForUpdate(@Valid ProductForm productForm, BindingResult validationResult, RedirectAttributes redirectAttributes, Model model) {
        if (productForm.getProductId() == null){
            return "index";
        }

        if (validationResult.hasErrors()){
            System.out.println("validationResult.hasErrors() = true");
            for(FieldError error : validationResult.getFieldErrors()){
                System.out.println(error.getField() + ": " + error.getDefaultMessage());
            }
            model.addAttribute("productForm", productForm);
            return "product/edit";
        }

        ProductEntity productEntity = new ProductEntity();
        BeanUtils.copyProperties(productForm, productEntity);
        productEntity.setCreatedBy(null);
        productEntity.setCreatedTime(null);
        productEntity.setUpdatedBy("Leon Yang");
        productRepository.save(productEntity);
        ProductForm newForm = new ProductForm();
        BeanUtils.copyProperties(productEntity, newForm);
        model.addAttribute("productForm", newForm);

        model.addAttribute("message_success","Updated Successfully!");

        return "product/detail";
    }

    /**
     * Delete product by product id
     * @param productId
     * @param redirectAttributes
     * @param model
     * @return redirect to home page
     */
    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.GET)
    public String delete(@PathVariable int productId, RedirectAttributes redirectAttributes, Model model) {
        if (productId < 1 || ! productRepository.findById(productId).isPresent()){
            redirectAttributes.addFlashAttribute("message_error","Resource doesn't exist!");
        } else {
            productRepository.deleteById(productId);
            redirectAttributes.addFlashAttribute("message_success","Removed successfully!");
        }
        return "redirect:/";
    }

    /**
     * Load product detail to display product info via view page
     * @param productId
     * @param redirectAttributes
     * @param model
     * @return '/product/detail' view model
     */
    @RequestMapping(value = "/detail/{productId}", method = RequestMethod.GET)
    public String detailForm(@PathVariable int productId, RedirectAttributes redirectAttributes, Model model) {
        if (productId < 1){
            redirectAttributes.addFlashAttribute("message_error","Resource doesn't exist!");
            return "redirect:/";
        }
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (!product.isPresent()){
            redirectAttributes.addFlashAttribute("message_error","Resource doesn't exist!");
            return "redirect:/";
        }

        ProductForm newForm = new ProductForm();
        BeanUtils.copyProperties(product.get(), newForm);
        model.addAttribute("productForm", newForm);
        return "product/detail";
    }

}
