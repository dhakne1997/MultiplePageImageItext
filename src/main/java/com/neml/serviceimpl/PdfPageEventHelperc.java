package com.neml.serviceimpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfPageEventHelperc extends PdfPageEventHelper {
	int page = 0;

	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		try {

			SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM  dd yyyy HH:mm:ss ");
			String formattedDate = dateFormat.format(new Date());
			Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

			ColumnText columnText = new ColumnText(writer.getDirectContent());
			columnText.setSimpleColumn(document.getPageSize().getLeft() + 14.4f, // shift 2f to the right
					document.getPageSize().getTop() - 20, // adjust the y-position to your liking
					document.getPageSize().getRight(), document.getPageSize().getBottom());

			// Add the date paragraph to the ColumnText object
			columnText.addElement(new Paragraph("CurrentDate:" + formattedDate, boldFont));
			columnText.go();

			// Add the image as before
			Image img = Image
					.getInstance("https://auction.ncdfiemarket.com/SpotLite/assets/img/login-bg/ncdfi-logo.jpg");
			img.scaleToFit(65, 65);
			float x = document.getPageSize().getWidth() - 60;
			img.setAbsolutePosition(x - 72, document.getPageSize().getHeight() - 60);
			writer.getDirectContent().addImage(img);
			document.add(new Paragraph(" "));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
	//Only first page print date
	
//	public class PdfPageEventHelperc extends PdfPageEventHelper { 
//		int page = 0;
//
//	@Override
//	public void onStartPage(PdfWriter writer, Document document) {
//	    try {
//	        page++;
//	        if (page == 1) {
//	            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy");
//	            String formattedDate = dateFormat.format(new Date());
//	            Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
//
//	            Paragraph dateParagraph = new Paragraph("CurrentDate:" +formattedDate, boldFont);
//	            dateParagraph.setAlignment(Element.ALIGN_LEFT);
//	            document.add(dateParagraph);
//
//	            Image img = Image.getInstance("https://auction.ncdfiemarket.com/SpotLite/assets/img/login-bg/ncdfi-logo.jpg");
//	            img.scaleToFit(65, 65);
//	            float x = document.getPageSize().getWidth() - 60;
//	            img.setAbsolutePosition(x - 72, document.getPageSize().getHeight() - 60);
//	            writer.getDirectContent().addImage(img);
//	            document.add(new Paragraph(" "));
//	        } else {
//	        	Image img = Image.getInstance("https://auction.ncdfiemarket.com/SpotLite/assets/img/login-bg/ncdfi-logo.jpg");
//	        	img.scaleToFit(65, 65);
//	        	float x = document.getPageSize().getWidth() - 60;
//	        	img.setAbsolutePosition(x - 72, document.getPageSize().getHeight() - 60);
//	        	writer.getDirectContent().addImage(img);
//	        	document.add(new Paragraph(" "));
//	            
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }}}
