package com.libertymutual.goforcode.communityShed.controllers;

import com.amazonaws.services.s3.model.*;
import com.libertymutual.goforcode.communityShed.components.AmazonS3Template;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/s3")

public class S3Controller {

	private AmazonS3Template amazonS3Template;
	private String bucketName;

	public S3Controller(AmazonS3Template amazonS3Template, @Value("${amazon.s3.default-bucket}") String bucketName) {
		this.amazonS3Template = amazonS3Template;
		this.bucketName = bucketName;
		System.out.println("S3Controller: bucket " + bucketName);
	}


    @RequestMapping(value = "list", method = RequestMethod.POST)
	public List<Resource<S3ObjectSummary>> getBucketResources() {
		System.out.println("S3Controller: List");
		ObjectListing objectListing = amazonS3Template.getAmazonS3Client()
				.listObjects(new ListObjectsRequest().withBucketName(bucketName));

		return objectListing.getObjectSummaries().stream()
				.map(a -> new Resource<>(a,
						new Link(String.format("https://s3.amazonaws.com/%s/%s", a.getBucketName(), a.getKey()))
								.withRel("url")))
				.collect(Collectors.toList());
	}
 
    
	@RequestMapping(value = "upload", method = RequestMethod.POST)
//	public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
	public @ResponseBody String handleFileUpload(
			@RequestParam("file") MultipartFile file) {
		System.out.println("S3Controller: Upload");
		String name = file.getOriginalFilename();
		System.out.println("S3Controller filename: " + name);
		if (!file.isEmpty()) {
			
			try {
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(file.getContentType());

				// Upload the file for public read
				
//				amazonS3Template.getAmazonS3Client()
//						.putObject(new PutObjectRequest(bucketName, name, file.getInputStream(), objectMetadata)
//								.withCannedAcl(CannedAccessControlList.PublicRead));
//				String imageURL = String.valueOf(amazonS3Template.getAmazonS3Client().getUrl(bucketName, name));
//				return "You successfully uploaded " + name + "with URL: " + imageURL;
				String imageURL = "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/Husky+3-Ton+Low+Profile+Floor+Jack+With+Speedy+Lift.jpg";
				return imageURL;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

}


