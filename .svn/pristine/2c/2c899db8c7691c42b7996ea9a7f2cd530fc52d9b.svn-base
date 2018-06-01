package com.hframwork.init;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;

public class tableInit {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private String[] createTableSql;
	private String[] initDateSql;
	private String initTable;
	private Boolean isOpen = false;
	@Autowired
	private ResourcePatternResolver ResourcePatternResolver;
	private static final String DEFAULT_CREATE_TABLE_SQL = "classpath*:script/create.mysql.sql";
	private static final String DEFAULT_INIT_DATA_SQL = "classpath*:script/init.mysql.sql";

	/**
	 * 
	 * @Title: init
	 * @Description:建表初始方法
	 * @return: void
	 */
	public void init() {
		if (!isOpen) {
			logger.debug("数据库初始化未开启");
			return;
		}
		boolean flag;
		try {
			flag = getAllTableName(jdbcTemplate, initTable);
			if (!flag) {
				initTable();
				initDate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initTable() throws IOException {
		if (createTableSql == null || createTableSql.length == 0) {
			createTableSql = new String[] { DEFAULT_CREATE_TABLE_SQL };
		}
		for (String str : createTableSql) {
			try {
				Resource[] rs = ResourcePatternResolver.getResources(str);
				this.execute(rs);
			} catch (Exception e) {
				logger.debug(e.getMessage());
				logger.info("没有建表sql文件可以执行");
			}

		}
	}

	public void initDate() throws IOException {
		if (initDateSql == null || initDateSql.length == 0) {
			initDateSql = new String[] { DEFAULT_INIT_DATA_SQL };
		}
		for (String str : initDateSql) {
			try {
				Resource[] rs = ResourcePatternResolver.getResources(str);
				this.execute(rs);
			} catch (Exception e) {
				logger.debug(e.getMessage());
				logger.info("没有初始化sql文件可以执行");
			}
		}
	}

	public void execute(Resource[] rs) throws IOException {
		ArrayUtils.reverse(rs);
		for (Resource r : rs) {
			InputStream inputStream = r.getInputStream();
			String sql = IOUtils.toString(inputStream);
			jdbcTemplate.execute(sql);
		}

	}

	/**
	 * 查询数据库是否有某表
	 * 
	 * @param cnn
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static boolean getAllTableName(JdbcTemplate jt, String tableName) throws Exception {
		Connection conn = jt.getDataSource().getConnection();
		ResultSet tabs = null;
		try {
			DatabaseMetaData dbMetaData = conn.getMetaData();
			String[] types = { "TABLE" };
			tabs = dbMetaData.getTables(null, null, tableName, types);
			if (tabs.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tabs.close();
			conn.close();
		}
		return false;
	}

	public String[] getCreateTableSql() {
		return createTableSql;
	}

	public void setCreateTableSql(String[] createTableSql) {
		this.createTableSql = createTableSql;
	}

	public String[] getInitDateSql() {
		return initDateSql;
	}

	public void setInitDateSql(String[] initDateSql) {
		this.initDateSql = initDateSql;
	}

	public String getInitTable() {
		return initTable;
	}

	public void setInitTable(String initTable) {
		this.initTable = initTable;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

}
