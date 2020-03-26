package com.leonapps.sample.springboot.thymeleaf.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter @Setter @ToString @EqualsAndHashCode
public class ProductForm {

    private Integer productId;

    @NotNull
    @Size(min = 3, max = 50, message = "Product Name is invalid.")
    private String name;

    @NotNull
    @Size(min = 5, max = 10, message = "Serial No. is invalid.")
    private String serialNo;

    @NotNull
    @Pattern(regexp = "Computer|Book|Food")
    private String catalog;

    @Size(max = 200, message = "Maximum length should not exceed 200 English characters.")
    private String description;

    @JsonIgnore
    private final LocalDateTime createdTime = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime updatedTime = LocalDateTime.now();

}
