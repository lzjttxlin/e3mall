package cn.e3mall.solrj;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	
	@Test
	public void addSolrj() throws Exception{
		//创建一个solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
		//创建一个document对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.addField("id", "1");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 2000);
		//将文档对象写入到索引库中
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	@Test
	public void deleteDocument() throws Exception{
		//创建一个solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
		//根据id删除
		solrServer.deleteById("1");
		solrServer.commit();
	}
	
	@Test
	public void deleteQueryDocument() throws Exception{
		//创建一个solrServer对象
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.132:8080/solr");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}
