package com.libertymutual.goforcode.communityShed.controllers;

import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.libertymutual.goforcode.communityShed.components.AmazonS3Template;
import com.libertymutual.goforcode.communityShed.models.Tool;
import com.libertymutual.goforcode.communityShed.repositories.ToolRepo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/tools/{toolId}/s3")


public class S3Controller {

	private AmazonS3Template amazonS3Template;
	private String bucketName;
	private ToolRepo toolRepo;
	private Tool tool;

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
	public @ResponseBody String handleFileUpload(
			@RequestParam("file") MultipartFile file, @PathVariable long toolId, HttpServletResponse response) throws IOException {
			System.out.println("S3Controller: upload start");
		String name = file.getOriginalFilename();
		System.out.println("S3Controller: filename: " + name);
		if (!file.isEmpty()) {
			
			try {
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(file.getContentType());
				
//				amazonS3Template.getAmazonS3Client()
//						.putObject(new PutObjectRequest(bucketName, name, file.getInputStream(), objectMetadata)
//								.withCannedAcl(CannedAccessControlList.PublicRead));
				System.out.println("S3Controller: upload complete");
//				String imageURL = String.valueOf(amazonS3Template.getAmazonS3Client().getUrl(bucketName, name));
				String imageURL = "https://s3-us-west-2.amazonaws.com/goforcode-oct2017-communityshade/aimage.jpg";
				System.out.println("S3Controller: URL: " + imageURL);
				System.out.println("S3Controller: toolId: " + toolId);
				toolRepo.getOne(toolId);
				System.out.println("S3Controller: After getOne");
				tool.setImage(imageURL);
				System.out.println("S3Controller: After setImage");
				toolRepo.save(tool);
				System.out.println("S3Controller: After DB save");
				ObjectMapper mapper =new ObjectMapper();
				System.out.println("S3Controller: S3 Completed");
				return mapper.writeValueAsString(tool);

			} catch (Exception e) {
				response.sendError(503, "Cannot upload image");
				System.out.println("S3Controller: Upload Exception" + e.getMessage());
				return null;
			}
		}
		response.sendError(504, "Empty File");
		System.out.println("S3Controller: Upload Error EmptyFile");
		return null;
	}
}


