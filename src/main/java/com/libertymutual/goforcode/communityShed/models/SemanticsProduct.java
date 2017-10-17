package com.libertymutual.goforcode.communityShed.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SemanticsProduct {
	
	private String name;
	private String brand;
	private String color;
	private String description;
	private Double height;
	private Double length;
	private Double width;
	private Double weight;
	private String size;
	private String[] images;
	private Feature features;
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class Feature	{
		private String blob;

		public String getBlob() {
			return blob;
		}

		public void setBlob(String blob) {
			this.blob = blob;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String[] getImages() {
		return images;
	}

	public void setImages(String[] images) {
		this.images = images;
	}

	public Feature getFeatures() {
		return features;
	}

	public void setFeatures(Feature features) {
		this.features = features;
	}

}
