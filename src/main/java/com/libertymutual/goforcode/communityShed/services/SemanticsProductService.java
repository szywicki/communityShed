package com.libertymutual.goforcode.communityShed.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.libertymutual.goforcode.communityShed.models.SemanticsProduct;
import com.semantics3.api.Products;

import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

@Service
public class SemanticsProductService {
	
	@Value("${SEMANTICS_API_KEY}")
	private String key;
	
	@Value("${SEMANTICS_API_SECRET}")
	private String secret;
	
	public List<SemanticsProduct> getProducts(String searchString)	{
		Products products = new Products(key, secret);

		products.productsField( "search", searchString );
		JSONObject results = null;
		try {
			results = products.getProducts();
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException
				| IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONArray jsonProducts = results.getJSONArray("results");
		String jsonString = jsonProducts.toString();
		
		List<SemanticsProduct> actualProducts = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			actualProducts = mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, SemanticsProduct.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonProducts);
		System.out.println(actualProducts);
		return actualProducts;
	}

}
