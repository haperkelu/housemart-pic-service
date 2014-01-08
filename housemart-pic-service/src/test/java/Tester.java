
import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import com.qiniu.qbox.auth.DigestAuthClient;
import com.qiniu.qbox.rs.GetRet;
import com.qiniu.qbox.rs.PutAuthRet;
import com.qiniu.qbox.rs.PutFileRet;
import com.qiniu.qbox.rs.RSService;
import com.qiniu.qbox.rs.StatRet;


public class Tester {

	@Test
	public void test() throws Exception {
		
		DigestAuthClient conn = new DigestAuthClient();
		RSService rs = new RSService(conn, "housemart");
		
		PutAuthRet putAuthRet = rs.putAuth();				
		PutFileRet putFileRet = rs.putFile("1-11021Q51A8_1369082760083.jpg", "jpg", "/Users/user/Pictures/1-11021Q51A8.jpg", "");
		
		
		//PutAuthRet putAuthRet = rs.putAuth();				
		//PutFileRet putFileRet = rs.putFile(key, "image/png", "C:\\qq_test.png", "");
		System.out.println(putFileRet.statusCode);
		System.out.println(putFileRet.getResponse());	
	}

	@Test
	public void testSave() throws Exception {
		
		/**
		String URL = "http://localhost:8080/picService";
		HessianProxyFactory factory = new HessianProxyFactory();  
		HessianPicServiceInterface service = 
		(HessianPicServiceInterface) factory.create(HessianPicServiceInterface.class, URL);
		
		PicSaveResult result = service.savePicToCloud(1 , "Chrysanthemum_1", "image/jpg", "c:/Chrysanthemum_1.jpg");
		**/
		File file = new File("/Users/user/Pictures/1-11021Q51A8.jpg");	
		FileBody fileBody = new FileBody(file);		
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);  
        entity.addPart("imageFile", fileBody);  
        entity.addPart("picId", new StringBody("1"));  
        entity.addPart("token", new StringBody("817"));  
        
		HttpPost httppost = new HttpPost("http://42.121.87.85:8080/repositoryPicUpload.controller");
		httppost.setEntity(entity);
		httppost.setHeader("Cookie", "user=30_1369019984014");
		HttpResponse httpResponse = new DefaultHttpClient().execute(httppost); 
		
		HttpEntity entitys = httpResponse.getEntity();
		System.out.println(httpResponse.getStatusLine().getStatusCode());
		System.out.println(EntityUtils.toString(entitys));
		
		
	}
	
	
}
