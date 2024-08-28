package com.neml.dao;

import java.sql.Connection;
import java.util.List;

import org.slf4j.Logger;

import com.neml.entity.PdfModel;
import com.neml.entity.PdfModelRequest;

public interface PdfDao {


	List<PdfModel> fetchPdfData(PdfModelRequest request, Connection conn, Logger log);

}
