package net.redleon

import org.apache.http.entity.mime.MultipartEntity
import org.apache.http.entity.mime.HttpMultipartMode
import org.apache.http.entity.mime.content.InputStreamBody
import org.apache.http.entity.mime.content.StringBody
import groovyx.net.http.*
import org.springframework.web.multipart.commons.CommonsMultipartFile
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockMultipartHttpServletRequest
import org.springframework.mock.web.MockMultipartFile
import org.apache.commons.fileupload.disk.DiskFileItem
import org.apache.commons.fileupload.disk.DiskFileItemFactory

class EnviarController {

    def index() { 

		final File TEST_FILE = new File("/Users/leon/tmp/send.csv");
		print "EXISTS:"+TEST_FILE.exists()
		print "SIZE:"+TEST_FILE.length()
		print "FILENAME:"+TEST_FILE.getName()
		DiskFileItem fileItem = new DiskFileItem("archivo", "text/csv", true, TEST_FILE.getName(), 1,  TEST_FILE.getParentFile());
		fileItem.getOutputStream();
		print "File:"+fileItem
		CommonsMultipartFile file = new CommonsMultipartFile(fileItem)	

		sendMultiPartFile(file , 'test')


    }

	void sendMultiPartFile(CommonsMultipartFile multipartImageFile, String cityName) {

	 def http = new HTTPBuilder("http://0.0.0.0:3000/upload")

	 http.request(Method.POST) { req ->

		 requestContentType: "multipart/form-data"
		 MultipartEntity multiPartContent = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE)
		 // Adding Multi-part file parameter "imageFile"
		 multiPartContent.addPart("archivo", new InputStreamBody(multipartImageFile.inputStream, multipartImageFile.contentType, multipartImageFile.originalFilename))
		 // Adding another string parameter "city"
		 //multiPartContent.addPart("city", new StringBody(cityName))
		 req.setEntity(multiPartContent)
		 response.success = { resp ->
		        if (resp.statusLine.statusCode == 200) {
		                  // response handling
		                   }
		            }
	      }
	}    
}
