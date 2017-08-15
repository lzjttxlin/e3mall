package cn.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.SearchItemMapper;

public class ItemAddMessageListener implements MessageListener {
	
	@Autowired
	private SearchItemMapper searchItemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public void onMessage(Message message) {
		
		try {
			TextMessage textMessage=(TextMessage) message;
			String text = textMessage.getText();
			long itemId=Long.parseLong(text);
			Thread.sleep(1000);
			SearchItem searchItem = searchItemMapper.getItemById(itemId);
			SolrInputDocument document = new SolrInputDocument();
			//想文本域中添加对象
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			//将文档对象写入到索引库中
			solrServer.add(document);
			//提交
			solrServer.commit();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

}
