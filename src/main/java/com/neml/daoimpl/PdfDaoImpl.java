package com.neml.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.stereotype.Repository;

import com.neml.dao.PdfDao;
import com.neml.entity.PdfModel;
import com.neml.entity.PdfModelRequest;

@Repository
public class PdfDaoImpl implements PdfDao {

	@Override
	public List<PdfModel> fetchPdfData(PdfModelRequest request, Connection conn, Logger log) {
	    List<PdfModel> pdfModelList = new ArrayList<>();
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
        PdfModel pdfModel = new PdfModel();

	    try {
	        // Create a prepared statement with a SQL query
	    	String sql = "SELECT auction_id, auction_date ,material FROM auctions  ";
	    	pstmt = conn.prepareStatement(sql);

	    	// Set the parameters for the prepared statement
	    //	pstmt.setInt(1, Integer.valueOf(request.getAuctionId())); // tmId = 1


	        // Execute the query and get the result set
	        rs = pstmt.executeQuery();
	        // Iterate over the result set and create PdfModel objects
	        while (rs.next()) {
	        	PdfModel pdf=new PdfModel();
	        	pdf.setAuctionId(String.valueOf(rs.getInt("auction_id")));
	        	pdf.setAuctionDate((rs.getDate("auction_date")));   
	        	pdf.setMaterial(rs.getString("material"));
	            pdfModelList.add(pdf);
	        }
	    } catch (SQLException e) {
	        log.error("Error fetching PDF data", e);
	    } finally {
	        // Close the resources
	        try {
	            if (rs != null) {
	                rs.close();
	            }
	            if (pstmt != null) {
	                pstmt.close();
	            }
	        } catch (SQLException e) {
	            log.error("Error closing resources", e);
	        }
	    }

	    return pdfModelList;
	}

}
