package com.libertymutual.goforcode.communityShed.api;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.communityShed.models.SemanticsProduct;
import com.libertymutual.goforcode.communityShed.services.SemanticsProductService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/products")
public class ProductApiController {
	
	private SemanticsProductService productService;
	
	public ProductApiController(SemanticsProductService productService)	{
		this.productService = productService;
	}
	
	@ApiOperation("Get list of products returned from Semantic3 service using offset value for mock pagination")
	@GetMapping("{searchString}")
	public List<SemanticsProduct> getProducts(@PathVariable String searchString) throws UnsupportedEncodingException	{
		searchString = URLDecoder.decode(searchString, "UTF-8");
		return productService.getProducts(searchString);
	}

}
