package com.neml.serviceimpl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.neml.config.DbConnectionUtil;
import com.neml.dao.PdfDao;
import com.neml.entity.PdfModel;
import com.neml.entity.PdfModelRequest;
import com.neml.service.PdfService;
import com.neml.util.Constant;
import com.neml.util.Util;

@Service
public class PdfServiceImpl implements PdfService {

	@Autowired
	DbConnectionUtil dbUtil;
	
	@Autowired
	PdfDao pdfDao;
	
	
	@Override
	public PdfModelRequest generatepdf(PdfModelRequest request, Logger log) {
		PdfModelRequest modelResp=new PdfModelRequest();
		
		List<PdfModel>PdfModelList=new ArrayList<PdfModel>();
		Connection conn = null;
		try {
			if (Util.isNeitherNullNorEmpty(request.getAuctionId())) {
				conn = dbUtil.getDBConnection("generatepdf");	
				PdfModelList = pdfDao.fetchPdfData(request, conn, log);
				modelResp.setAuctionId(request.getAuctionId());
				modelResp.setPdfPath(initializePDF(PdfModelList,request.getAuctionId()));
				modelResp.setErrorCode(Constant.ErrorCode.SUCCESS);
			}
				else {
					modelResp.setErrorCode(Constant.ErrorCode.TM_ID_INVALID);
		} 
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			try {
				if (!conn.isClosed()) {
					dbUtil.closeConnection(conn, " ");;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return modelResp;
	}
		
	

	public String initializePDF(List<PdfModel> pdfModelList,String auctionId) {

		Document document = null;
		PdfWriter writer = null;
		String filePath = "D:\\pdffiles\\";
		try {
			document = new Document(PageSize.LEDGER);
			filePath = filePath + "_Ncdfi_Auction_info.pdf";
			writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
		    writer.setPageEvent(new PdfPageEventHelperc()); // Add 
			document.open();
			
			
			 document.add(new Paragraph(" "));
			
			initializeSchemeBody(document, pdfModelList);
			return filePath;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (Objects.nonNull(document))
				document.close();
			if (Objects.nonNull(writer))
				writer.close();
		}
		return null;

	}



	public void initializeSchemeBody(Document document, List<PdfModel> pdfModelList) {
		try {
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 13);
		PdfPTable table = new PdfPTable(11);
		PdfPCell headingCell = generateHeadingCell();
		List<PdfPCell> headingCells = generateTableHeader();
		table.setWidthPercentage(100);

		// Set the widths of the columns
		float[] columnWidths = new float[11];
		columnWidths[0] = 5f; 
		columnWidths[1] = 15f; 
		columnWidths[2] = 15f;
		columnWidths[3] = 15f; 
		columnWidths[4] = 15f; 
		columnWidths[5] = 15f;
		columnWidths[6] = 15f; 
		columnWidths[7] = 15f; 
		columnWidths[8] = 15f; 
		columnWidths[9] = 15f;
		columnWidths[10] =15f;
		table.setWidths(columnWidths);
		
		
		table.addCell(headingCell);
		for (PdfPCell cell : headingCells) {
			table.addCell(cell);
		}


	
		
		if (pdfModelList != null) {
		    for (PdfModel pdfObj : pdfModelList) {
		        table.addCell(new Paragraph(pdfObj.getAuctionId(), font));
		        table.addCell(new Paragraph(String.valueOf(pdfObj.getAuctionDate())));
		        table.addCell(new Paragraph(pdfObj.getMaterial()));
		        table.addCell(new Paragraph(pdfObj.getLocation()));
		        table.addCell(new Paragraph(pdfObj.getBidderName()));
		        table.addCell(new Paragraph(String.valueOf(pdfObj.getLowestRate())));
		        table.addCell(new Paragraph(pdfObj.getQuantity()));
		        table.addCell(new Paragraph(pdfObj.getProductName()));
		        table.addCell(new Paragraph(pdfObj.getProductDescription()));
		        table.addCell(new Paragraph(pdfObj.getAuctionDescription()));
		        table.addCell(new Paragraph(pdfObj.getOrderStatus()));





		        table.completeRow();
		    }
		} else {
		    // Handle the case where pdfModelList is null
		    // You can add a message or log an error, for example:
		    System.out.println("pdfModelList is null");
		}
		
		

		document.add(table);
		//document.newPage();

	} catch (Exception e) {
		e.printStackTrace();
	}

}



	public List<PdfPCell> generateTableHeader() {
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);

		Paragraph auctionId = new Paragraph("Auction ID", font); //1
		auctionId.setAlignment(Element.ALIGN_LEFT);
		PdfPCell auctionIdCell = new PdfPCell(auctionId);
		auctionIdCell.setPaddingBottom(1f);
		auctionIdCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph material = new Paragraph("Material", font); //2
		material.setAlignment(Element.ALIGN_LEFT);
		PdfPCell materialCell = new PdfPCell(material);
		materialCell.setPaddingBottom(5f);
		materialCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph auctionDate = new Paragraph("Auction Date", font); //2
		auctionDate.setAlignment(Element.ALIGN_LEFT);
		PdfPCell auctionDateCell = new PdfPCell(auctionDate);
		auctionDateCell.setPaddingBottom(5f);
		auctionDateCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph location = new Paragraph("Location", font); //2
		auctionDate.setAlignment(Element.ALIGN_LEFT);
		PdfPCell locationCell = new PdfPCell(location);
		locationCell.setPaddingBottom(5f);
		locationCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph bidderName = new Paragraph("Bidder name", font); //2
		auctionDate.setAlignment(Element.ALIGN_LEFT);
		PdfPCell bidderNameCell = new PdfPCell(bidderName);
		bidderNameCell.setPaddingBottom(5f);
		bidderNameCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph lowestRate = new Paragraph("Lowest rate", font); //2
		auctionDate.setAlignment(Element.ALIGN_LEFT);
		PdfPCell lowestRateCell = new PdfPCell(lowestRate);
		lowestRateCell.setPaddingBottom(5f);
		lowestRateCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph quantity = new Paragraph("Quantity", font); //2
		quantity.setAlignment(Element.ALIGN_LEFT);
		PdfPCell quantityCell = new PdfPCell(quantity);
		quantityCell.setPaddingBottom(5f);
		quantityCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph productName = new Paragraph("Product Name", font); //2
		productName.setAlignment(Element.ALIGN_LEFT);
		PdfPCell productNameCell = new PdfPCell(productName);
		productNameCell.setPaddingBottom(5f);
		productNameCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		
		Paragraph productDesc = new Paragraph("Product Description", font); //2
		productDesc.setAlignment(Element.ALIGN_LEFT);
		PdfPCell productDescCell = new PdfPCell(productDesc);
		productDescCell.setPaddingBottom(5f);
		productDescCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph AuctionDescription = new Paragraph("Auction Description", font); //2
		AuctionDescription.setAlignment(Element.ALIGN_LEFT);
		PdfPCell AuctionDescriptionCell = new PdfPCell(AuctionDescription);
		AuctionDescriptionCell.setPaddingBottom(5f);
		AuctionDescriptionCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		Paragraph orderstatus = new Paragraph("Order Status", font); //2
		orderstatus.setAlignment(Element.ALIGN_LEFT);
		PdfPCell orderstatusCell = new PdfPCell(orderstatus);
		orderstatusCell.setPaddingBottom(5f);
		orderstatusCell.setBackgroundColor(new BaseColor(237, 203, 123));
		
		
		
		return Arrays.asList(auctionIdCell,auctionDateCell,materialCell,bidderNameCell,
				productNameCell,lowestRateCell,quantityCell,productNameCell,productDescCell
				,AuctionDescriptionCell,orderstatusCell);
	}



	public PdfPCell generateHeadingCell() {
		Font font = new Font(Font.FontFamily.TIMES_ROMAN, 28, Font.BOLD);
		
		Paragraph schemeHeading = new Paragraph("AuctionReport ", font);

		schemeHeading.setAlignment(Element.ALIGN_CENTER);
		PdfPCell headingCell = new PdfPCell(schemeHeading);
		headingCell.setPaddingRight(50f);
		headingCell.setPaddingBottom(5f);
		headingCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		headingCell.setBackgroundColor(new BaseColor(94, 224, 205));
		headingCell.setColspan(14);
		
		
		
		
		return headingCell;
	}

	
}