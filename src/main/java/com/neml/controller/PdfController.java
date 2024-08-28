package com.neml.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.neml.entity.PdfModelRequest;
import com.neml.service.PdfService;

@RestController
@RequestMapping(value = "/Ncdfipdf")
public class PdfController {
	
	private static Logger log = LoggerFactory.getLogger("brokerpdf");

	@Autowired
	private PdfService pdfService;
	
	
	@RequestMapping(value = "/generatePdf", method = RequestMethod.POST)
	public ResponseEntity<PdfModelRequest> pdfGenerate (@RequestBody PdfModelRequest request) {
		PdfModelRequest pdfModelObj=pdfService.generatepdf(request,log);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("message", "PDF generated successfully");
	    return new ResponseEntity<PdfModelRequest>(pdfModelObj, headers, HttpStatus.OK);

	}

}
