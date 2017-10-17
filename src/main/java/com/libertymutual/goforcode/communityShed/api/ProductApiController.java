package com.libertymutual.goforcode.communityShed.api;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@ApiOperation("Get list of products returned from Semantic3 service")
	@PostMapping("")
	public List<SemanticsProduct> getProducts(@RequestBody SearchTerm searchTerm)	{
		return productService.getProducts(searchTerm.getSearchString());
	}
	
	static class SearchTerm	{
		private String searchString;

		public String getSearchString() {
			return searchString;
		}

		public void setSearchString(String searchString) {
			this.searchString = searchString;
		}
		
	}
	

}
