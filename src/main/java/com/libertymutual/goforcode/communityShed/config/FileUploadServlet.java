package com.libertymutual.goforcode.communityShed.config;

import java.util.logging.Logger;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

    private final static Logger LOGGER = 
            Logger.getLogger(FileUploadServlet.class.getCanonicalName());

	public static Logger getLogger() {
		return LOGGER;
	}

}