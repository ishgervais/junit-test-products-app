package com.academic.productmis.controllers;

import com.academic.productmis.models.Product;
import com.academic.productmis.models.dto.ProductDto;
import com.academic.productmis.services.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @MockBean
    private ProductService productServiceMock;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void getAll_success() throws Exception {
        List<Product> asList = Arrays.asList(new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Mango", 100, 10),
                new Product(UUID.fromString("edcd99ae-157a-4c88-8a44-405d93b4f18a"), "Orange", 300, 20));

        when(productServiceMock.getAll()).thenReturn(asList);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-products")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Mango\",\"price\":100,\"quantity\":10}," +
                        "{\"id\":\"edcd99ae-157a-4c88-8a44-405d93b4f18a\",\"name\":\"Orange\",\"price\":300,\"quantity\":20}]"))
                .andReturn();

    }

    @Test
    public void getById_Success() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"),"Orange", 300, 20);

        when(productServiceMock.getById(product.getId())).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-products/16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Orange\",\"price\":300,\"quantity\":20}"))
                .andReturn();
    }

    @Test
    public void getById_WhenGivenIdNotFound() throws Exception {
        Product product = new Product(UUID.fromString("8f352825-e13f-4f3f-b0ad-e3d2fceccfbc"),"Orange", 300, 20);

        when(productServiceMock.getById(product.getId())).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-products/8f352825-e13f-4f3f-b0ad-e3d2fcebcfbc")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"product not found\"}"))
                .andReturn();
    }

    @Test
    public void registerProduct_Success() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"),"Orange", 300, 20);
        ProductDto productDto = new ProductDto("Orange",300, 20);

        when(productServiceMock.create(productDto)).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post("/save-product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Orange\",\"price\":\"300\",\"quantity\":20}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":\"16e1f6fb-fae5-4dd2-9b15-622914827bdc\",\"name\":\"Orange\",\"price\":300,\"quantity\":20}"))
                .andReturn();
    }

    @Test
    public void updateProduct_Success() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"),"Orange", 300, 20);
        Product updateProduct = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"), "Orange", 300, 30);
        ProductDto productDto = new ProductDto("Orange", 300, 30);

        when(productServiceMock.update(product.getId(), productDto)).thenReturn(updateProduct);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/update-product?id=16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Orange\",\"price\":300,\"quantity\":30}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\":\"Orange\",\"price\":300,\"quantity\":30}"))
                .andReturn();
    }

    @Test
    public void updateProduct_404() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-623914827bdc"),"Orange", 300, 20);
        ProductDto productDto = new ProductDto("Orange", 300, 20);

        when(productServiceMock.update(product.getId(), productDto)).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put("/update-product?id=16e1f6fb-fae5-4dd2-9b15-623514827bdd")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Orange\",\"price\":300,\"quantity\":20}");

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"product not found\"}"))
                .andReturn();
    }

    @Test
    public void deleteProduct_Success() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"),"Orange", 300, 20);

        when(productServiceMock.deleteProduct(product.getId())).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete("/delete-product?id=16e1f6fb-fae5-4dd2-9b15-622914827bdc")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\":\"Orange\",\"price\":300,\"quantity\":20}"))
                .andReturn();
    }
    @Test
    public void deleteProduct_Failed() throws Exception {
        Product product = new Product(UUID.fromString("16e1f6fb-fae5-4dd2-9b15-622914827bdc"),"Orange", 300, 20);

        when(productServiceMock.getById(product.getId())).thenReturn(product);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/all-products/8f352825-e13f-4f3f-b0ad-e3d2fcebcfbc")
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc
                .perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"status\":false,\"message\":\"product not found\"}"))
                .andReturn();
        when(productServiceMock.deleteProduct(product.getId())).thenReturn(product);
    }
}
