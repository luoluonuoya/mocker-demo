/**
 * Project Name:medical-instrument-market;<br/>
 * File Name:BASE64DecodedMultipartFile;<br/>
 * Package Name:common.web;<br/>
 * Date: 2019-12-20 15:56;<br/>
 * Copyright (c) 2019, www.sq580.com All Rights Reserved.;<br/>
 */
package demo.common.web;

import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.*;

/**
 * ClassName: BASE64DecodedMultipartFile<br/>
 * Function: TODO ADD FUNCTION(可选)<br/>
 * Reason: TODO ADD REASON(可选)<br/>
 * Date: 2019-12-20 15:56<br/>
 *
 * @author zhangfs
 */
public class BASE64DecodedMultipartFile implements MultipartFile {

    private final byte[] imgContent;
    private final String header;

    public BASE64DecodedMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    @Override
    public String getName() {
        return System.currentTimeMillis() + Math.random() + "." + header.split("/")[1];
    }

    @Override
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int) Math.random() * 10000 + "." + header.split("/")[1];
    }

    @Override
    public String getContentType() {
        return header.split(":")[1];
    }

    @Override
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    public long getSize() {
        return imgContent.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return imgContent;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    public static MultipartFile base64ToMultipart(String base64) {
        try {
            String[] baseStrs = base64.split(",");

            BASE64Decoder decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = decoder.decodeBuffer(baseStrs[1]);

            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            return new BASE64DecodedMultipartFile(b, baseStrs[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static MultipartFile base64ToMultipart(String base64, String format) {
        String header = "data:image/" + format + ";base64,";
        base64 = base64.replace(header, "");
        return base64ToMultipart(header + base64);
    }

}