package com.libertymutual.goforcode.communityShed.controllers;

import com.amazonaws.services.s3.model.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

	public S3Controller(AmazonS3Template amazonS3Template, @Value("${amazon.s3.default-bucket}") String bucketName, ToolRepo toolRepo) {
		this.amazonS3Template = amazonS3Template;
		this.bucketName = bucketName;
		this.toolRepo = toolRepo;
	}

    @RequestMapping(value = "list", method = RequestMethod.POST)
	public List<Resource<S3ObjectSummary>> getBucketResources() {

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

		String name = file.getOriginalFilename();
		if (!file.isEmpty()) {
			
			try {
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(file.getContentType());
				
				amazonS3Template.getAmazonS3Client()
						.putObject(new PutObjectRequest(bucketName, name, file.getInputStream(), objectMetadata)
								.withCannedAcl(CannedAccessControlList.PublicRead));

				String imageURL = String.valueOf(amazonS3Template.getAmazonS3Client().getUrl(bucketName, name));
				Tool tool = toolRepo.getOne(toolId);
				tool.setImage(imageURL);
				toolRepo.save(tool);
				ObjectMapper mapper =new ObjectMapper();
				mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
				return mapper.writeValueAsString(tool);

			} catch (Exception e) {
				response.sendError(503, "Cannot upload image");
				return null;
			}
		}
		response.sendError(504, "Empty File");
		return null;
	}
}


