package demo.infrastructure.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**  
 * @Title: OSSUtils.java
 * @Package mall.common.util.oss
 * @company：     社区580
 * @Description: OSS文件操作工具类
 * @author zfs
 * @date 2016年11月2日 下午5:41:08
 * @version V1.0  
 */
@Getter
@Slf4j
@Component
public class OSSUtils {

	public static String JPG = "jpg";
	@Value("${oss_endpoint}")
	private String ENDPOINT;
	@Value("${oss_accesskeyid}")
	private String ACCESSKEYID;
	@Value("${oss_accesskeysecret}")
	private String ACCESSKEYSECRET;
	@Value("${oss_bucketname}")
	private String BUCKETNAME;
	@Value("${oss_network}")
	private String NETWORK;
	@Value("${oss_protocal}")
	private String PROTOCAL;
	
	/**
	* @Title: uploadStream
	* @Description: 上传流
	* @param @param url
	* @param @return    
	* @return String    
	* @throws
	 */
	public String uploadStream(InputStream inputstream, String filename, String filetype, String folder) {
		OSSClient ossClient = new OSSClient(PROTOCAL + ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);
    	try {
    		// 文件夹是否存在
			boolean found = ossClient.doesObjectExist(BUCKETNAME, folder + "/");
			if (!found) {
				ossClient.putObject(BUCKETNAME, folder + "/", new ByteArrayInputStream("".getBytes()));
			}
			// 开始上传
			ossClient.putObject(BUCKETNAME, folder + "/" + filename + "." + filetype, inputstream);
		} catch (OSSException oe) {
		    log.error("Caught an OSSException, which means your request made it to OSS, "
		            + "but was rejected with an error response for some reason.");
		    log.error("Error Message: " + oe.getErrorCode());
		    log.error("Error Code:       " + oe.getErrorCode());
		    log.error("Request ID:      " + oe.getRequestId());
		    log.error("Host ID:           " + oe.getHostId());
		    return oe.getErrorCode().toString();
		} catch (ClientException ce) {
		    log.error("Caught an ClientException, which means the client encountered "
		            + "a serious internal problem while trying to communicate with OSS, "
		            + "such as not being able to access the network.");
		    log.error("Error Message: " + ce.getMessage());
		    return ce.getErrorCode().toString();
		} finally {
		    if(ossClient != null) {
		    	ossClient.shutdown();
		    }
		    if(inputstream != null) {
		    	try {
					inputstream.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
		    }
		}
        return PROTOCAL + NETWORK + "/" + folder + "/" + filename + "." + filetype;
	}
	
	/**
	* @Title: imgResize
	* @Description: 小图处理
	* @param @param imgname
	* @param @param filetype
	* @param @param folder
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public String imgResize(String imgname, String filetype, String folder) {
		StringBuffer url = new StringBuffer();
		url.append(PROTOCAL);
		url.append(NETWORK);
		url.append("/");
		url.append(folder + "/");
		url.append(imgname);
		url.append(filetype);
		return url.toString();
	}
	
	/**
	* @Title: unilateralAdapter
	* @Description: 单边缩略（某边固定，另一边按比例处理）
	* @param @param smallImg 预处理小图
	* @param @param unilateral 宽为w高为h
	* @param @param length 缩放长度
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String unilateralAdapter(String smallImg, String unilateral, int length) {
		return smallImg + "?x-oss-process=image/resize," + unilateral.toLowerCase() + "_" + length;
	}
	
	/**
	* @Title: fixedAdapter
	* @Description: 强制宽高缩略
	* @param @param smallImg
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String fixedAdapter(String smallImg, int width, int height) {
		return smallImg + "?x-oss-process=image/resize," + "m_fixed,h_" + height + ",w_" + width;
	}
	
	/**
	* @Title: lfitAdapter
	* @Description: 等比缩放，限定在矩形框内（长边优先）
	* @param @param smallImg
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String lfitAdapter(String smallImg, int width, int height) {
		return smallImg + "?x-oss-process=image/resize," + "m_lfit,h_" + height + ",w_" + width;
	}

	/**
	* @Title: mlfitAdapter
	* @Description: 等比缩放，限定在矩形框外（按短边优先）
	* @param @param smallImg
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String mfitAdapter(String smallImg, int width, int height) {
		return smallImg + "?x-oss-process=image/resize," + "m_mfit,h_" + height + ",w_" + width;
	}
	
	/**
	* @Title: mfitAdapter
	* @Description: 固定宽高，自动裁剪
	* @param @param smallImg
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String fillAdapter(String smallImg, int width, int height) {
		return smallImg + "?x-oss-process=image/resize," + "m_fill,h_" + height + ",w_" + width;
	}
	
	/**
	* @Title: padAdapter
	* @Description: 固定宽高，缩略填充
	* @param @param smallImg
	* @param @param width
	* @param @param height
	* @param @return    
	* @return String    
	* @throws
	 */
	public static String padAdapter(String smallImg, int width, int height) {
		return smallImg + "?x-oss-process=image/resize," + "m_pad,h_" + height + ",w_" + width;
	}
	
}