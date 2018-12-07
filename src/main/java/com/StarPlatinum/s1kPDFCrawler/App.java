package com.StarPlatinum.s1kPDFCrawler;



import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {

		System.out.println( "Hi,如果遇到问题请开issue" );
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入课程ID，网页地址栏中从sdResIdCaseId=到&的中间部分，这串字符串即为ID");
        String caseId = scanner.next();
        scanner.close();
        String html = "http://1s1k.eduyun.cn/resource/resource/RedesignCaseView/viewCaseBbs1s1k.jspx?code=-1&sdResIdCaseId="+caseId;
        Document page = Jsoup.connect(html).get();
        String docId = page.getElementsByClass("docCode").first().val();
        String urlGetDocPage = "http://doc.baidubce.com/v2/reader?getdocinfo&type=json&idlist="+docId;
        Document docPageJson = Jsoup.connect(urlGetDocPage).get();
		JSONObject docPage = new JSONObject(docPageJson.getElementsByTag("body").text());
		int pageCount = docPage.getJSONObject("data").getJSONObject(docId).getInt("page");
		
		//file
        String outFileName = "./" + page.getElementsByTag("h1").get(0).text()+ "课程文本.txt";
	    File out = CreateFileUtil.createFile(outFileName, "txt");
	    OutputStream outputStream = new FileOutputStream(out);
	    
		for(int i = 1 ;i <= pageCount; i++) {
			Document pageJson = Jsoup.connect("http://doc.baidubce.com/v2/reader?getcontent&doc_id=" 
											+ docId+ "&pn=" + i+
											"&v=1&host=BCEDOC&token=TOKEN&type=xreader").ignoreContentType(true).get();
			JSONObject pageOb = new JSONObject(pageJson.getElementsByTag("body").get(0).text());
			JSONArray contents =  pageOb.getJSONObject("data").getJSONArray("json").getJSONObject(0).getJSONObject("content").getJSONArray("body");
			for (int j = 0 ;j < contents.length(); j ++) {
				Object object = contents.getJSONObject(j).get("c");
				if (object instanceof String) {
					outputStream.write(((String) object).getBytes());
				}
			}
		}
		outputStream.close();
        System.out.println("程序结束，请到程序所在目录下寻找文件：" + outFileName);

    }
}
