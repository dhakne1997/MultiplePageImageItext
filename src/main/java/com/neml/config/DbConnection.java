package com.neml.config;

import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.SQLException;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

@Service
public class DbConnection implements DbConnectionUtil  {
//	private static Logger log = LogMstr.setLogPath("DBUtil", "DBUtil",
//			Util.getValueFromProp(UserMain.cfgProperties, "logging.path", DefaultProperty.LOG_PATH));
	private static final Logger log = LoggerFactory.getLogger("DbConnection");
	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;
	public static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
	public static HikariPoolMXBean poolProxy;
	
	
	Environment env;
	
	@Autowired
	public DbConnection(@Autowired Environment env) {
		this.env=env;
		this.DBUtil();
	}

	public void DBUtil(){
		try {
			log.info("Initializing Database Pool");
			config.setJdbcUrl(env.getProperty("spring.datasource.url"));
	        config.setUsername(env.getProperty("spring.datasource.username"));
	        config.setPassword(env.getProperty("spring.datasource.password"));
	        config.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
	        config.addDataSourceProperty("cachePrepStmts" ,"true");
	        config.addDataSourceProperty("prepStmtCacheSize","250");
	        config.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
	        config.setRegisterMbeans(true);
	        config.setPoolName("UserService");
	        ds = new HikariDataSource(config);
			Connection con = ds.getConnection();
			con.close();
			ObjectName poolName = new ObjectName("com.zaxxer.hikari:type=Pool ("+ds.getPoolName()+")");
			poolProxy = JMX.newMXBeanProxy(mBeanServer, poolName, HikariPoolMXBean.class);
			log.info("Initialized Database Pool");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	@Override
	public  Connection getDBConnection(String str) throws Exception {
		log.info("Before Getting Database Connection :: Method and FileName :: "+str+" :: Active : " + poolProxy.getActiveConnections() + " Waiting : "
				+ poolProxy.getThreadsAwaitingConnection() + " Total : " + poolProxy.getTotalConnections());
		Connection conn = ds.getConnection();
		log.info("After Getting Database Connection :: Active : " + poolProxy.getActiveConnections() + " Waiting : "
				+ poolProxy.getThreadsAwaitingConnection() + " Total : " + poolProxy.getTotalConnections());
		return conn;
	}

	@Override
	public  void closeConnection(Connection conn,String str) {
		if (conn != null) {
			log.info("Inside closeConnection :: Method and FileName :: "+str);
			try {
				conn.close();
				log.info("Closed connection :: Method and FileName :: "+str);
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("Error in closeRsPstmtConn " + e.getMessage());
			}
		}
	}



}

