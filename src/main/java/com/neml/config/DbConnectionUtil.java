package com.neml.config;

import java.sql.Connection;

public interface DbConnectionUtil {
	Connection getDBConnection(String str) throws Exception;

	void closeConnection(Connection conn, String str);

}
