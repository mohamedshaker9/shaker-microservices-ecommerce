package com.shaker.ecommicro.aichat.tools;


import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class AiChatTools {

    private static final Logger logger = LoggerFactory.getLogger(AiChatTools.class);

//    private final CategoryService categoryService;
//    private final ProductService productService;

//    public AiChatTools(CategoryService categoryService, ProductService productService) {
//        this.categoryService = categoryService;
//        this.productService = productService;
//    }


//    @Tool(name = "getCategories", description = "This is used for getting all the categories as a list with id and name")
//    public List<CategoryDTO> getCategories() {
//        logger.info("Get Categories: ...............");
//        return categoryService.getCategories(1, 50)
//                .getCategories();
//    }
//
//    @Tool(name = "getProductsByCategory", description = "This is to get products by category name")
//    public List<ProductDTO> getProductsByCategory(
//            @ToolParam(description = "value for category name") String name) throws ResourceNotFoundException {
//        return productService.getByCategory(name).getProducts();
//    }



}
