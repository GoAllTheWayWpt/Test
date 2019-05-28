package com.feicui.utils;

  
  
import java.io.BufferedWriter;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;  
import java.io.Writer;  
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;  
import freemarker.template.Configuration;  
import freemarker.template.Template;  
  
public class WordUtils {  
  
    /** 
     * @param dataMap 
     *            word中需要展示的动态数据，用map集合来保存 
     * @param templateName 
     *            word模板名称，例如：teample.ftl 
     * @param filePath 
     *            文件生成的目标路径，例如：D:/ 
     * @param fileName 
     *            生成的文件名称 
     */  
    @SuppressWarnings("unchecked")  
    public static void createWord(Map dataMap, String templateName,  
            String filePath, String fileName,HttpServletResponse response) {  
        try {  
            // 创建配置实例  
            Configuration configuration = new Configuration();  
  
            // 设置编码  
            configuration.setDefaultEncoding("UTF-8");  
  
            // ftl模板文件  
            configuration.setClassForTemplateLoading(WordUtils.class,  
                    "/com/feicui/ftls");  
  
            // 获取模板  
            Template template = configuration.getTemplate(templateName);
            OutputStream os = response.getOutputStream();// 取得输出流
    		response.reset();// 清空输出流
            response.setHeader("Content-disposition", "attachment; filename="
    				+ fileName); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
    		response.setContentType("application/msword");
  
            // 输出文件  
            File outFile = new File(filePath + File.separator + fileName);  
  
            // 如果输出目标文件夹不存在，则创建  
            if (!outFile.getParentFile().exists()) {  
                outFile.getParentFile().mkdirs();  
            }  
  
            // 将模板和数据模型合并生成文件  
            Writer out = new BufferedWriter(new OutputStreamWriter(  
                    os, "UTF-8"));  
  
            // 生成文件  
            template.process(dataMap, out);  
  
            // 关闭流  
            out.flush();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
      
      
    /** 
     * 将图片转换为BASE64字符串 
     * @param filename 
     * @return 
     * @throws IOException 
     */  
    public static String getImageString(String filename) throws IOException {    
        InputStream in = null;    
        byte[] data = null;    
        try {    
            in = new FileInputStream(filename);    
            data = new byte[in.available()];    
            in.read(data);    
            in.close();    
        } catch (IOException e) {    
            throw e;    
        } finally {    
            if(in != null) in.close();    
        }    
        BASE64Encoder encoder = new BASE64Encoder();    
        return data != null ? encoder.encode(data) : "";    
   }   
  
}  