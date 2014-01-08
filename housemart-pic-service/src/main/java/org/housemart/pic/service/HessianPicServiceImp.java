/** 
* @Title: HessianPicServiceImp.java
* @Package org.housemart.pic.service
* @Description: TODO
* @author Pie.Li
* @date 2013-2-24 下午9:25:13
* @version V1.0 
*/
package org.housemart.pic.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.housemart.pic.api.HessianPicServiceInterface;
import org.housemart.pic.api.PicSaveResult;
import com.caucho.hessian.server.HessianServlet;
import com.qiniu.qbox.auth.DigestAuthClient;
import com.qiniu.qbox.rs.PutAuthRet;
import com.qiniu.qbox.rs.PutFileRet;
import com.qiniu.qbox.rs.RSService;


/**
 * @ClassName: HessianPicServiceImp
 * @Description: TODO
 * @date 2013-2-24 下午9:25:13
 * 
 */
public class HessianPicServiceImp extends HessianServlet implements HessianPicServiceInterface {
	
	private static final String bucketName = "housemart";
	
	private static Logger logger = LogManager.getLogger(HessianPicServiceImp.class);
	
	/**
	* @Fields serialVersionUID 
	*/
	private static final long serialVersionUID = 3336838400609488713L;

	private static final String _domain = "http://housemart.qiniudn.com/";
	
	/**
	 * 
	 * @see org.housemart.pic.api.HessianPicServiceInterface#savePicToCloud(int, java.lang.String, byte[])
	 */
	@SuppressWarnings("unused")
	public PicSaveResult savePicToCloud(int picId, String fileName, String fileType, String localFilePath) {
		
		PicSaveResult result = new PicSaveResult();
		
		try {
			DigestAuthClient conn = new DigestAuthClient();
			RSService rs = new RSService(conn, bucketName);
			
			PutAuthRet putAuthRet = rs.putAuth();				
			PutFileRet putFileRet = rs.putFile(fileName, fileType, localFilePath, "");
			
			if(putFileRet.statusCode == 200){
				result.setCode(putFileRet.getStatusCode());
				result.setUrl(_domain + fileName);
			} else {
				result.setCode(putFileRet.getStatusCode());
				logger.error("name:" + fileName + ";pic response:" + putFileRet.response);
			}
//			GetRet getRet = rs.get(fileName, fileName);
//			result.setCode(getRet.getStatusCode());
//			result.setUrl(getRet.getUrl());
			
		} catch (Exception e) {
			logger.error("Pic Save Error", e);
		}
		
		return result;
	}

	
	
	
}
