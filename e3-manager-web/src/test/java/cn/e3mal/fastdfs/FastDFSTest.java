package cn.e3mal.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class FastDFSTest {
	
	@Test
	public void testFastDFS() throws Exception{
		
		
		//加载配置文件
		ClientGlobal.init("E:/workspace-javaee/e3-manager-web/src/main/resources/conf/client.properties");
		//创建一个TrackerClient对象，直接new可以得到
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackerClient对象得到TrackerServer
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StroageClient对象
		StorageClient storageClient = new StorageClient(trackerServer, null);
		//使用StorageClient上传文件，得到上传文件的文件名和上传路径
		String[] strings = storageClient.upload_file("C:/Users/pc/Desktop/hdImg_9eb6281723eba7084bf05830b09c556615016571715.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testFastClientDFS() throws Exception{
		//创建一个FastDFSClient对象
		FastDFSClient fastDFSClient = new FastDFSClient("E:/workspace-javaee/e3-manager-web/src/main/resources/conf/client.properties");
		//使用对象上传对象，获得文件的路径
		String string = fastDFSClient.uploadFile("C:/Users/pc/Desktop/bdabad3d1112368409c54e8153dbeeba.jpg");
		System.out.println(string);
	}
}
