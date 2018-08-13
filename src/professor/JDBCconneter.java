package professor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author feizhang
 *
 */
public class JDBCconneter {
	static Connection conn = null;
	static PreparedStatement pre = null;
	/**
	 * 
	 * @todo 建立JDBC链接
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @return
	 * @return Connection
	 */
	public static Connection getConnection() {
		String driverName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/ProCrawler?useUnicode=true&amp;characterEncoding=utf-8&useSSL=true";
		String user = "root";
		String password = "root";
		
		try{
			Class.forName(driverName);
			conn = DriverManager.getConnection(url,user,password);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * 
	 * @todo 该表为总表，添加了一个专家的姓名后，就往这个专家的表里面插入一条数据
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param id
	 * @param @param proID
	 * @param @param proName
	 * @param @param baseInfoID
	 * @param @param newsID
	 * @param @param paperInfoID
	 * @return void
	 */
	public static  void insertProCrawler(String id,String proName,String unit,String baseInfoID,
			String newsID,String paperInfoID) {
		conn = getConnection();
		
		String insetSql = "insert into CrawlerInfo( id, proName,unit, baseInfoID,newsID,paperInfoID) value ("
				+ "?,?,?,?,?,?)";
		try {
			pre = conn.prepareStatement(insetSql);
			pre.setString(1, id);
			pre.setString(2,proName);
			pre.setString(3,unit);
			pre.setString(4,baseInfoID);
			pre.setString(5,newsID);
			pre.setString(6,paperInfoID);
//			System.out.println(pre.executeUpdate());
			if(pre.executeUpdate() == 0) {
				System.out.println("insert CrawlerInfo failed");
			}
			pre.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @todo 在专家基本信息表中插入一条数据
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param id
	 * @param @param proID
	 * @param @param proName
	 * @param @param scholarId
	 * @param @param queoted
	 * @param @param papers
	 * @param @param Hindex
	 * @param @param Gindex
	 * @param @param filed
	 * @param @param volum
	 * @return void
	 */
	public static void insertBaseInfo(String id,String baseInfoUUID,String proName,String scholarId,String unit,
			int queoted,int papers,int Hindex,int Gindex,String filed,int volum,String image) {
		conn = getConnection();
		id = GenerateUUID.getUUID();
		String insertSql = "insert into ProbaseInfo( id,baseInfoUUID,proName, scholarId,unit,\n" + 
				"quoted, papers, Hindex, Gindex, filed, volum,proImage) value ("
				+ "?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			pre = conn.prepareStatement(insertSql);
			pre.setString(1, id);
			pre.setString(2, baseInfoUUID);
			pre.setString(3,proName);
			pre.setString(4,scholarId);
			pre.setString(5,unit);
			pre.setInt(6,queoted);
			pre.setInt(7,papers);
			pre.setInt(8,Hindex);
			pre.setInt(9,Gindex);
			pre.setString(10,filed);
			pre.setInt(11,volum);
			pre.setString(12, "https://www.baidu.com"+image);
			
			if(pre.executeUpdate() == 0) {
				System.out.println("insert ProbaseInfo failed");
			}
			System.out.println(pre.toString());
			pre.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @todo 在论文信息统计表中插入一条数据
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param id
	 * @param @param proID
	 * @param @param scholarId
	 * @param @param proName
	 * @param @param JournalSrticle
	 * @param @param MeetingArticle
	 * @param @param treatise
	 * @param @param other
	 * @param @param total
	 * @return void
	 */
	public static void insertPaperInfo(String id,String paperInfoUUID,String proName,
			Float JournalSrticle,Float MeetingArticle,Float treatise,Float other,int total) {
		conn = getConnection();
		id = GenerateUUID.getUUID();
		String insertSql = "insert into PaperInfo( id, paperInfoUUID,proName, \n" + 
				"			 JournalArticle, MeetingArticle, treatise, other, total) value ("
				+ "?,?,?,?,?,?,?,?)";
		try {
			pre = conn.prepareStatement(insertSql);
			pre.setString(1, id);
			pre.setString(2, paperInfoUUID);
			pre.setString(3,proName);
			pre.setFloat(4,JournalSrticle);
			pre.setFloat(5,MeetingArticle);
			pre.setFloat(6,treatise);
			pre.setFloat(7,other);
			pre.setFloat(8,total);
			
			if(pre.executeUpdate() == 0) {
				System.out.println("insert PaperInfo failed");
			}
			System.out.println(pre.toString());
			pre.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void insertPaperInfo(String id,String paperInfoUUID,String proName) {
		conn = getConnection();
		id = GenerateUUID.getUUID();
		String insertSql = "insert into PaperInfo( id, paperInfoUUID,proName) value ("
				+ "?,?,?)";
		try {
			pre = conn.prepareStatement(insertSql);
			pre.setString(1, id);
			pre.setString(2, paperInfoUUID);
			pre.setString(3,proName);
			
			if(pre.executeUpdate() == 0) {
				System.out.println("insert PaperInfo failed");
			}
			System.out.println(pre.toString());
			pre.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @todo 在专家新闻表中插入一条数据
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param id
	 * @param @param proID
	 * @param @param proName
	 * @param @param proLink
	 * @return void
	 */
	public static void insertProNews(String id,String newsUUID,String proName,String title,String proLink) {
		conn = getConnection();
		id = GenerateUUID.getUUID();
		String insertSql = "insert into proNews( id, newsUUID, proName, title, proLink) value ("
				+ "?,?,?,?,?)";
		try {
			pre = conn.prepareStatement(insertSql);
			pre.setString(1, id);
			pre.setString(2,newsUUID);
			pre.setString(3,proName);
			pre.setString(4,title);
			pre.setString(5,proLink);
			
			if(pre.executeUpdate() == 0) {
				System.out.println("insert PaperInfo failed");
			}
			System.out.println(pre.toString());
			pre.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @todo 根据专家的proID获取专家论文基本信息表ID和专家名字
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param proID
	 * @param @return
	 * @return List<String>
	 */
	public List<String> getBaseInfoID(String proID) {
		conn = getConnection();
		String proName,baseInfoID;
		List<String> list = new ArrayList<String>();
		String selectSql = "select proName,baseInfoID from CrawlerInfo where proID='"+proID+"'";
		
		try {
			pre = conn.prepareStatement(selectSql);
			ResultSet result = pre.executeQuery();
			if(result != null) {
				proName = result.getString("proName");
				baseInfoID = result.getString("baseInfoID");
				list.add(proName);
				list.add(baseInfoID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * @todo 根据专家的proID获取专家新闻表ID和专家名字
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param proID
	 * @param @return
	 * @return List<String>
	 */
	public List<String> getNewsID(String proID) {
		conn = getConnection();
		String proName,newsID;
		List<String> list = new ArrayList<String>();
		String selectSql = "select proName,newsID from CrawlerInfo where proID='"+proID+"'";
		
		try {
			pre = conn.prepareStatement(selectSql);
			ResultSet result = pre.executeQuery();
			if(result != null) {
				proName = result.getString("proName");
				newsID = result.getString("newsID");
				list.add(proName);
				list.add(newsID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 
	 * @todo 根据专家的proID获取专家论文信息表ID和专家名字
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param @param proID
	 * @param @return
	 * @return List<String>
	 */
	public List<String> getPaperInfoID(String proID) {
		conn = getConnection();
		String proName,paperInfoID;
		List<String> list = new ArrayList<String>();
		String selectSql = "select proName,paperInfoID from CrawlerInfo where proID='"+proID+"'";
		
		try {
			pre = conn.prepareStatement(selectSql);
			ResultSet result = pre.executeQuery();
			if(result != null) {
				proName = result.getString("proName");
				paperInfoID = result.getString("paperInfoID");
				list.add(proName);
				list.add(paperInfoID);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
