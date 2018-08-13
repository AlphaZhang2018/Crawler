package professor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProfessorCrawler {
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String name = null,unit = null;
		name = "陈向群";
		unit = "";
		
		String idUUID =  GenerateUUID.getUUID();
		String baseInfoUUID = GenerateUUID.getUUID();
		String newsUUID = GenerateUUID.getUUID();
		String paperInfoUUID = GenerateUUID.getUUID();
		String id = null;
		//创建一个专家
		JDBCconneter.insertProCrawler(idUUID,name,unit,baseInfoUUID,newsUUID,paperInfoUUID);
		//百度页
		String baiduUrls = "https://www.baidu.com/baidu?wd="+name + unit +"&tn=monline_dg&ie=utf-8";
		List<String> list1 = getPagesUrl(baiduUrls);
		//设置一个变量，只需要修改这个变量就可以随意想解析几页就解析几页
		int needCrawledPages = 5;
		//百度学术页
		String xueshuUrls = 	"http://xueshu.baidu.com/s?wd="+ name +"&ie=utf-8";
		List<String> list2 = getPagesUrl(xueshuUrls);
		for(int i = 0;i < needCrawledPages;i++) {
			String pagelink1 ="http://www.baidu.com" + list1.get(i);
			//从百度页面获取到了新闻的title和链接
			List<List<String>> baiduNews = getNewsUrls(pagelink1);
			String pagelink2="http://xueshu.baidu.com" + list2.get(i);
			//从百度学术获取到了新闻的title和链接
			List<List<String>> articleNews = getArticleUrls(pagelink2);
			//要把解析出来的新闻的title和链接解析出来，存到数据库存到proNews表
			//百度新闻部分
			for(int j = 0 ;j < baiduNews.get(0).size();j++) {
				String title = baiduNews.get(0).get(j);
				String link = baiduNews.get(1).get(j);
				JDBCconneter.insertProNews(id,newsUUID,name,title,link);
			}
			//学术新闻部分
			for(int j = 0 ;j < articleNews.get(0).size();j++) {
				String title = articleNews.get(0).get(j);
				String link = articleNews.get(1).get(j);
				JDBCconneter.insertProNews(id,newsUUID,name,title,link);
			}
			//从百度百科解析数据，解析个人数据
			//还没做，暂时不做了，百度学术已经很多了
		}	
		//从百度学术解析出专家的基本信息和专家的论文分布信出来，存到数据库到ProBaseInfo表和PaperInfo表
		List<List<String>> paperInfo = getProfessorInfo(name,unit);
		//专家的基本信息，存到数据库到ProBaseInfo表
		if(paperInfo.get(0).size() > 0) {
			for(int j = 0 ;j < paperInfo.get(0).size();j++) {
				String image = paperInfo.get(0).get(0);
				String proname = paperInfo.get(0).get(1);
				String volum = paperInfo.get(0).get(2);
				int volumNum = Integer.parseInt(volum.split("人")[0]);
				String ScholarID = paperInfo.get(0).get(3);
				String affiliate = paperInfo.get(0).get(4);
				String quotednum = paperInfo.get(0).get(5);
				String resultnum = paperInfo.get(0).get(6);
				String Hnum = paperInfo.get(0).get(7);
				String Gnum = paperInfo.get(0).get(8);
				String filed = paperInfo.get(0).get(9);
				JDBCconneter.insertBaseInfo(id,baseInfoUUID, name,ScholarID,unit,Integer.parseInt(quotednum),Integer.parseInt(resultnum),Integer.parseInt(Hnum),Integer.parseInt(Gnum),filed, volumNum,image);
			}
		}else JDBCconneter.insertBaseInfo(id,baseInfoUUID, name,null,unit,0,0,0,0,null, 0,null);
		
		//专家的论文分布，存到数据库到PaperInfo表
		if(paperInfo.get(1).size() > 0) {
			for(int j = 0 ;j < paperInfo.get(1).size();j++) {
				String journalper = paperInfo.get(1).get(0);
				Float journalNum = Float.parseFloat(journalper.split("%")[0]);
				String meetingper = paperInfo.get(1).get(1);
				Float meetingNum = Float.parseFloat(meetingper.split("%")[0]);
				String treatiseper = paperInfo.get(1).get(2);
				Float treatiseNum = Float.parseFloat(treatiseper.split("%")[0]);
				String oherper = paperInfo.get(1).get(3);
				Float otherNum = Float.parseFloat(oherper.split("%")[0]);
				String TotalArticle = paperInfo.get(1).get(4);
				int total = Integer.parseInt(TotalArticle);
				JDBCconneter.insertPaperInfo(id,paperInfoUUID,name,journalNum,meetingNum,treatiseNum,otherNum,total);
			}
		}else JDBCconneter.insertPaperInfo(id,paperInfoUUID,name);
		
		
		/**
		 * 这个功能完成了根据关键字搜索后得到百度百科的页面，并且从这个页面中获取所有专家的信息与内容
		 * 
		 * */
		//这个只能搜人明，带单位搜索不行，会有错误
		String keywordsforBaike = null;
		String baikeUrls = "http://baike.baidu.com/search/word?word=" + keywordsforBaike;
	}
	/**根据关键字先百度一下，先获取前面5页的地址------>baidu
	 * @author feizhang
	 * @param urls
	 * @return list
	 * @throws IOException
	 */
	public static List<String> getPagesUrl(String urls) throws IOException{
		List<String> list = new ArrayList<String>();
		Document doc = Jsoup.connect(urls).get();
		Element pages = doc.getElementById("page");
		Elements page = pages.getElementsByTag("a");
		for(Element p : page) {
			String pagelink = p.attr("href");
			list.add(pagelink);
		}
		return list;
	}
	/**根据前面获取的到前五页的地址，挨页去解析新闻链接-------->baidu
	 * 之后要存数据库
	 * @author feizhang
	 * @param urls
	 * @throws IOException
	 * @return list
	 */
	public static List<List<String>> getNewsUrls(String urls) throws IOException {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<List<String>> list = new ArrayList<List<String>>();
		Document doc = Jsoup.connect(urls).get();
		Element element = doc.getElementById("content_left");
		Elements e = element.getElementsByClass("result c-container ");
		for(Element data : e) {
			//新闻链接
			String link = data.select("h3 a").attr("href");
			//新闻标题
			String title = data.select("h3 a").text();
			list1.add(title);
			list2.add(link);
//			System.out.println(link);
//			System.out.println(title);
		}
		list.add(list1);
		list.add(list2);
		return list;
	}
	/**
	 * 百度学术------------>xueshu
	 * @todo 获取学术新闻标题和链接
	 * @param urls
	 * @throws IOException
	 */
	public static List<List<String>> getArticleUrls(String urls) throws IOException {
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		List<List<String>> list = new ArrayList<List<String>>();

		Document doc = Jsoup.connect(urls).get();
		Elements e = doc.select("div.sc_content");
		for(Element data : e) {
			String href="http://www.baidu.com/" + data.select("h3[class=t c_font]").select("a").attr("href");
			String title= data.select("h3[class=t c_font]").select("a").text();
			System.out.println(href);
			System.out.println(title);
			list1.add(title);
			list2.add(href);
		}
		list.add(list1);
		list.add(list2);
		return list;
	}

	/**
	 * 获取学术频道链接
	 * @param key
	 * @throws IOException
	 * @return map
	 * @author feizhang
	 */
	public static HashMap<String,String> getProfessorPage(String key) throws IOException {
		HashMap<String,String> map = new HashMap<String,String>();
		String url = "http://xueshu.baidu.com/s?wd="+ key + "&ie=utf-8";
		Document doc = Jsoup.connect(url).get();
		Elements e = doc.select("span[class=op-scholar-authorcard-info]");
		System.out.println(e.html());
		for(Element data : e) {
			String name = data.select("a").text();
			String href = data.select("a").attr("href");
			String institution = data.select("p").text();
			String ins = null;
			if(institution.length() >= 11) {
				ins = institution.substring(0, 7);
			}else ins = institution;
//			System.out.println(name);
//			System.out.println(href);
//			System.out.println(ins+ins.length());
			map.put(institution, href);
		}
		return map;
	}
	/**
	 * 
	 * @todo 根据姓名和单位查询百度学术的信息
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param name
	 * @param insitution
	 * @return void
	 */
	public static List<List<String>> getProfessorInfo(String name,String institution) throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		String keywords = null;
		if(institution.length() > 9) {
			keywords = institution.substring(0, 7);
		}else keywords = institution;
		HashMap<String,String> map = getProfessorPage(name);
		List<String> keylist = likeKey(map,keywords);
		if(keylist.size() > 0) {
			for(String unit : keylist) {
				String href = map.get(unit);
				list = PraseInfo(href);
			}
		}else System.out.println("查无此人");
		return list;
	}
	/**
	 * 
	 * @todo 解析专家学术信息
	 * @author feizhang
	 * @date 2018年8月10日
	 * @param urls
	 * @return void
	 */
	public static List<List<String>> PraseInfo(String urls) throws IOException {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();
		Document doc = Jsoup.connect(urls).followRedirects(true).execute().parse();
		Elements e = doc.select("div[id=pagemain_content]");
		Elements intro = e.select("div[id=author_intro_wr]");
		String image = intro.select("div[class=person_image]").select("img").attr("src");
		list1.add(image);
//		String name = intro.select("div[class=person_baseinfo").select("div[class=p_name]").text();
		String name = intro.select("div[class=p_name]").text();
		list1.add(name);
//		String volume = intro.select("div[class=person_baseinfo").select("div[class=p_volume]").text();
		String volume = intro.select("div[class=p_volume]").text();
		list1.add(volume);
//		String ScholarID = intro.select("div[class=person_baseinfo").select("span[class=p_scholarID_id]").text();
		String ScholarID = intro.select("span[class=p_scholarID_id]").text();
		list1.add(ScholarID);
//		String affiliate = intro.select("div[class=person_baseinfo").select("div[class=p_affiliate]").text();
		String affiliate = intro.select("div[class=p_affiliate]").text();
		list1.add(affiliate);
		Elements achWr = intro.select("li[class=p_ach_item]");
		Element aa = achWr.get(0);
		String quoted = achWr.get(0).select("p[class=p_ach_type c_gray]").text();
//		list1.add(quoted);
		String num1 = achWr.get(0).select("p[class=p_ach_num]").text();
		list1.add(num1);
		String result = achWr.get(1).select("p[class=p_ach_type c_gray]").text();
//		list1.add(result);
		String num2 = achWr.get(1).select("p[class=p_ach_num]").text();
		list1.add(num2);
		String H = achWr.get(2).select("p[class=p_ach_type c_gray]").text();
//		list1.add(H);
		String num3 = achWr.get(2).select("p[class=p_ach_num]").text();
		list1.add(num3);
		String G = achWr.get(3).select("p[class=p_ach_type c_gray]").text();
//		list1.add(G);
		String num4 = achWr.get(3).select("p[class=p_ach_num]").text();
		list1.add(num4);
//		String person_editinfo = intro.select("div[class=person_baseinfo").select("div[class=person_editinfo]").text();
		String person_editinfo = intro.select("div[class=person_editinfo]").text();
		list1.add(person_editinfo);
		
		Elements articleData = e.select("div[class=pie_map_container]");
		if(articleData.size() != 0) {
			String journal = articleData.get(0).select("div[class=pieText]").text();
//			list2.add(journal);
			String journalnum = articleData.get(0).select("p[class=number]").text();
			list2.add(journalnum);
			String meeting = articleData.get(1).select("div[class=pieText]").text();
//			list2.add(meeting);
			String meetingnum = articleData.get(1).select("p[class=number]").text();
			list2.add(meetingnum);
			String treatise = articleData.get(2).select("div[class=pieText]").text();
//			list2.add(treatise);
			String treatisenum = articleData.get(2).select("p[class=number]").text();
			list2.add(treatisenum);
			String other = articleData.get(3).select("div[class=pieText]").text();
//			list2.add(other);
			String ohernum = articleData.get(3).select("p[class=number]").text();
			list2.add(ohernum);
			String TotalArticle = e.select("div[class=pieMapTotal]").select("p[class=number]").text();
			String total = TotalArticle.split("篇")[0];
//			String TotalArticle = articleData.select("p[class=number]").text();
			list2.add(total);
		}

		list.add(list1);
		list.add(list2);
		return list;
	}
	/**
	 * 判断输入的机构有没有在百度学术里给出来的相匹配
	 */
	public static List<String> likeKey(HashMap<String,String> map,String keywords) {
		List<String> list = new ArrayList<String>();
		for(String key : map.keySet()) {
			if(key.contains(keywords)) {
				list.add(key);
			}
		}
		return list;
	}
	
}
