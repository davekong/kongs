package com.kongs.common.util;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.kongs.common.Constants;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;




public class FileUtil {
	
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	
	private static final String ENCODING="UTF-8";
	
	public static final String LEFT_FLASH="/";
	
	
	//��stringд�뵽�ļ���
	public static boolean  writeContentToFile(String file,String content) throws IOException{
		if(null==file||null==content) return false;
		
		File f=new File(file);
		File parent=f.getParentFile();
		if(!f.exists()) parent.mkdirs();
		
		FileOutputStream fos=new FileOutputStream(f);;
		fos.write(content.getBytes(ENCODING));
		fos.close();
		
		return true;
	}
	
	//���ļ�����ȡstring 
	public static String getContentFromFile(File file) throws IOException{
		
		if(!file.isFile()||!file.exists()) return null;
		
		StringBuffer sb=new StringBuffer();
		
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), CharsetUtil.detectorByFile(file));
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			sb.append(lineTxt);
		}
		bufferedReader.close();
		read.close();
		
		return sb.toString();
	}
	
	//��ȡ�ļ��ֽ�
	public static byte[] getFileBytes(File file){
		try {
			if(!file.isFile()||!file.exists()) return null;
			return FileCopyUtils.copyToByteArray(new FileInputStream(file));
		} catch (Exception e) {
			logger.error(e.fillInStackTrace());
			e.printStackTrace();
		} 
		return null;
	}
	
	
	/***********************************�����ļ�*******************************************/
	public static boolean copyFileToFile(String source,String desc)throws IOException{
		if (StringUtils.isBlank(source) || StringUtils.isBlank(desc))
			return false;

		BufferedInputStream bs = null;
		BufferedOutputStream os = null;
		try {
			File sourceFile = new File(source);

			if (!sourceFile.exists()) return false;
				
			File descFile = new File(desc);
			// ����Ŀ¼��new File("/tmp/one/two/three").mkdirs();ִ�к󣬻Ὠ��tmp/one/two/three�ļ�Ŀ¼
			// ����Ŀ¼��new File("/tmp/one/two/three").mkdir();ִ�к��򲻻Ὠ���κ�Ŀ¼����Ϊ�Ҳ���/tmp/one/twoĿ¼
			// �����ļ���newFile("/tmp/one/two/three/aa").createNewFile;ִ�к���/tmp/one/two/three/Ŀ¼�����ڣ����ļ��ᴴ��ʧ�ܣ���������һ�㲻����ʽ�Ĵ����ļ���
			// �ж��ļ���Ŀ¼�Ƿ���ڣ�exists;������ж��ļ���Ҳ�����ж�Ŀ¼��
			descFile.getParentFile().mkdirs();
			// descFile.createNewFile();û��Ҫ��ʽ���������»��Զ�����

			bs = new BufferedInputStream(new FileInputStream(sourceFile));
			os = new BufferedOutputStream(new FileOutputStream(descFile));

			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = bs.read(b)) != -1) {
				os.write(b, 0, len);
			}
			os.flush();

			return true;
		} finally {
			if (bs != null) {
				bs.close();
			}
			if (os != null) {
				os.close();
			}
		}
		
	}
	public static boolean copyFileToPath(String source,String desc) throws IOException{
		if(StringUtils.isBlank(source)||StringUtils.isBlank(desc)) return false;
		
		BufferedInputStream bs = null;
		BufferedOutputStream os = null;
		try{
			
			File sourceFile=new File(source);
			if(!sourceFile.exists()) return false;
			
			
			String filename = source.substring(source.lastIndexOf("/") + 1);
			File path = new File(desc);
			if (!path.exists()) {
				path.mkdirs();
			}
			
			bs = new BufferedInputStream(new FileInputStream(source));
			os = new BufferedOutputStream(new FileOutputStream(desc + "/" + filename));
	
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = bs.read(b)) != -1) {
				os.write(b, 0, len);
			}
			os.flush();
			
			return true;
		} finally {
			if (bs != null){
				bs.close();				
			}
			if (os != null){
				os.close();				
			}
		}
	}
	
	
	/************��ȡ�ļ���׺��ǰ׺**************/
	public static String getFilePrefix(String fileName) {
		if(StringUtils.isBlank(fileName)) return null;
		int index = fileName.lastIndexOf(".");
		if(-1==index)return fileName;
		return fileName.substring(0, index);
	}
	public static String getFileSuffix(String fileName) {
		if(StringUtils.isBlank(fileName)) return null;
		int index = fileName.lastIndexOf(".");
		if(-1==index)return "";
		return fileName.substring(index + 1);
	}
	public static String getSmpleFileName(String fileName){
		File file=new File(fileName);
		return file.getName();
//		if(StringUtils.isBlank(fileName)) return null;
//		int index=fileName.lastIndexOf("/");
//		if(-1==index) return fileName;
//		return fileName.substring(index + 1);
	}
	
	
	
	
	/************ɾ���ļ�**************
	 * 
	 * ɾ����ļ�ʱ������ע�⣬��ִ��file.delete()ʱ,
	 * �����ļ�����������������Ӧ��ʹ�ã���file.delete()����false,
	 * ����false,��ζ�Ÿ��ļ���û�б�ɾ�����ԣ������ע��
	 * ��ִ�и÷���ʹ��Ҫ��ִ�йر����ȡ�������
	 * 
	 * ********************************/
	public static boolean deleteFile(String file){
		if(null==file) return false;
		return deleteFile(new File(file));
	}
	public static boolean deleteFiles(String[] files){
		if(null==files) return false;
		
		List l=new ArrayList();
		for(String file:files){
			l.add(new File(file));
		}
		return deleteFiles((File[])l.toArray(new File [l.size()]));
	}
	public static boolean deleteFile(File file){
		if(file==null) return false;
		if (file.isDirectory()) {
			String[] children = file.list();
			 for (int i=0; i<children.length; i++)  deleteFile(new File(file, children[i]));
	                
		}
		return file.delete();
	}
	public static boolean  deleteFiles(File[] files) {
		if(files==null) return false;
		boolean success=false;
		for (int i = 0; i < files.length; i++) {
			success=deleteFile(files[i]);
		}
		return success;
	}
	
	/**
	 *  Ϊ�˽��sun jar���ѹ��ʱ�������������⣨���������������ָ��ѹ�����ļ�������������룩��
	 *  ����ʹ��apache��˾��ant.jar������ѹ����
	 *  
	 *  1. ˵������a.zip���������ļ��У�aaa��bbb,����aaa�ļ�����
	 * 	   shang.txt��xue.txt,��bbb�ļ�������tang.txt����ôzip��Ŀ��
	 *     ���ٸ���
	 *     �ش� 5�����ļ����������ļ������
	 *  2. �����ļ���a����b.zip,��ôb.zip��ѹ������ļ�λ���������a�ļ����£�
	 *     ������a�ļ����µ�b�ļ��£��������Ϊ��Ҫ��
	 *     
	 *  3. �����������������
	 *  
	 *  4. ֻ�ܽ�ѹzip����rar  java�޷���ѹ��
	 */ 
	//zipToFile
	public static void  zipToFile(String zipName, String toPath) {
		try {
				// ����zip�ļ�
				org.apache.tools.zip.ZipFile zf= new org.apache.tools.zip.ZipFile(zipName);
				// zipEntryָ��ѹ�����Ŀ
				org.apache.tools.zip.ZipEntry ze = null;
				java.util.Enumeration e = zf.getEntries();
				while (e.hasMoreElements()) {
					ze = (org.apache.tools.zip.ZipEntry) e.nextElement();
					// ��Ŀ-->�ļ�
					File zfile = new File(toPath + File.separator
							+ ze.getName());
					//�ļ���·���ļ�
					if (ze.isDirectory()) {// ����Ŀ�Ƿ���·��
						if (!zfile.exists())// ��·������ôzfile����ʱ����·���ļ���·���Ƿ��Ѿ����ڣ�
							zfile.mkdirs();// ������ -->����
					}else{// �������Ŀ�Ƿ�·���ļ�
						File fpath = zfile.getParentFile();// ��ý�ѹ����ļ��ĸ��ļ�
						if (!fpath.exists())// ���ļ�����һ����·����·���Ƿ����
							fpath.mkdirs();// �����ڣ�����
						//����������
						InputStream in = zf.getInputStream(ze);
						// ���������
						FileOutputStream out = new FileOutputStream(zfile);
						byte ch[] = new byte[256];
						int j;
						while ((j = in.read(ch)) != -1)
							out.write(ch, 0, j);
						out.close();
						in.close();
						//�ļ������zip�����ѹ
						if (zfile.getAbsolutePath().endsWith(".zip")) {
							zipToFile( zfile.getAbsolutePath(),
									zfile.getParent());
							//������Ҫ����ɾ�����zip�ļ��Ĳ����𣿲���Ҫ�����ע�⣬������
							//��ʲô�ǵ��������� �����������˼!!!
							//deleteFile(zfile);//,ɾ�������zip�ļ�
						}
					}
				}
				zf.close();
				deleteFiles(new File[]{new File(zipName)});//ɾ�������zip�ļ�
				//deleteFile(new File(zipName));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("��ѹʧ�ܣ�");
		}
	}
	
	
	
	//�������ǻ��src�����б�������ļ��ĵ�·���������src�±�������ĳ�ļ�����·��
	//�����propertie�ļ����Һͱ�����ͬһ���£������ֱ�ӻ��properties����������ʹ������ķ���
	//InputStream in=FileOperation.class.getResourceAsStream("hotLable.properties");
	
	//���� ����ע�⣺resouceName������Ҳ����Ҫ��ģ�
	//���������:
	//�����ļ���src���£���resourceName���ֿ����ǡ�struts.xml�����������ǡ�log4j.xml��,�������ǡ�a.txt�����ߡ�b.java����
	//�����ļ�����src���£������ڰ��£���resouceName,���ǡ�����/�ļ���
	
	//��ʵ����ǰ��µ��ļ�������ͬ�÷������ڵ��ദ��ͬһ���£���resouceName�ǿ��Լ�дΪ�ļ���
	
	//�������һ�����⣺src�µ��ļ�������֮��λ�����WebRoot�µ��ļ�������֮������λ�����
	//�ٶ�����Ŀ������OA,src�ļ��б������ڡ�F:\workspace1\OA����
	
	//ϸ�ĵ�ͬ־�ǻ��ᷢ�֣�
	//��FileOperation.java ·���ǡ�F:\workspace1\OA\src\com\ utils\FileOperation.java��;
	//�������֮����ļ�FileOperation.class����·����
	//"F:\workspace1\OA\WebRoot\WEB-INF\classes\com\ utils\FileOperation.class" 
	//ͬ��λ��src�µ������ļ�������xxx.properties����xxx.hbm.xml����applicationContext-xxx.xml��
	//������֮���·��ͬ��
	
	//��λ��F:\workspace1\OA\WebRoot�µ�js��images��jsp���ļ����Լ���Щ�ļ����µ��������ļ��м��ļ�..
	//��λ�������֮ǰ��ȫ��ͬ��
	
	//��ʱ�����ٿ�һ�����⣬��ִ�д���ʱ��Ҫ���ĳ�����ļ���·��
	//��ô��·�������Ǳ���ǰ���Ǳ����ģ�
	//�ش𣺱�����.
	public static String getUrlOfsrcOfCompile(String resourceName) {
		if (null == resourceName || "".equals(resourceName))
			return null;//����ļ�����Ϊ""����Ϊnull ��ֱ�ӷ���
		
		Class clazz = FileUtil.class;
		java.net.URL url;
		
		//.class�������ļ���url��ȡ������ͬ��
		if("java".equals(getFileSuffix(resourceName))){//�����.javaΪ��׺!�����Ϊ.class��׺��
			resourceName=getFilePrefix(resourceName)+".class";
		}
		url = clazz.getResource(resourceName);//�Ӱ��»�ȡ��Դ
		if(null==url)//�Ӹ��»�ȡ��Դ
			url=clazz.getClassLoader().getResource(resourceName);
		if(null==url){
			System.out.println("�ļ�������;\n������resourceName���淶:\n" +
					"  �����src���µ���Դ��ֱ�Ӹ�������Դ�����֣�����struts.xml;\n" +
					"  ����ǰ��µ���Դ����������+�ļ�������com/split/hotLable.properties;");
			return null;
		}
		//System.out.println(url);//  file:/F:/workspace1/OA/WebRoot/WEB-INF/classes/com/utils/FileOperation.class
		String absolute = url.getPath();
		//System.out.println(absolute);//  /F:/workspace1/OA/WebRoot/WEB-INF/classes/com/utils/FileOperation.class
		return absolute.substring(1);// ȥ����ͷ�ġ�/��
	}
	
	
	//���webroot���ļ����ļ��е�url
	//resourceName�������WebRoot��·�������磺 
	//WebRoot�µ�photo�ļ����µ�newsPhoto�ļ��У���/photo/newsPhoto��
	
	//���������ĳ��������ڷ������е����и�򵥵ķ�������ѡ��
	//ServletActionContext.getServletContext().getRealPath("/photo/newsPhoto");
	//����ķ�����õ���WebRoot�µ��ļ���photo���ļ��µ�newPhoto�ļ���·����
	public static String getWebRootUrl(String resourceName){
		String path=getUrlOfsrcOfCompile("FileUtils.class");
		//System.out.println(path);
		if('/'!=resourceName.charAt(0))
			resourceName="/"+resourceName;
		int index=path.indexOf("/WEB-INF/classes/");
		//System.out.println(index);
		return path.substring(0,index)+resourceName;
	}
	
	//��ȡproperties�ļ���������key-value������map�У�
	//ע��propertiesName���������⣺
	
	//���������:
	//�����ļ���src���£���resourceName���ֿ����ǡ�struts.xml�����������ǡ�log4j.xml��,�������ǡ�a.txt�����ߡ�b.java����
	//�����ļ�����src���£������ڰ��£���resouceName,���ǡ�����/�ļ�����ߡ�/����/�ļ���
	
	//��ʵ����ǰ��µ��ļ�������ͬ�÷������ڵ��ദ��ͬһ���£���resouceName�ǿ��Լ�дΪ�ļ���
	public static Map readProperties(String propertiesName){
		Map map=new HashMap();
		Properties properties =new Properties();
		String path=getUrlOfsrcOfCompile(propertiesName);
		System.out.println(path);
		try {
			//��ȡ.properties�ļ�
			InputStream in=new FileInputStream(path);
			//�����ж�ȡ��ֵ�ԣ��ŵ�properties�У�
			properties.load(in);
		} catch (IOException e) {
			System.out.println("����properties�ļ�ʧ��");
			e.printStackTrace();
		}
		//���properties�����еļ�ö������
		Enumeration enu=properties.keys();
		while(enu.hasMoreElements()){
			//���һ��key
			String key=enu.nextElement().toString();
			//��� ��key ��Ӧ��  value
			String value=properties.getProperty(key);
			//�� key �� value ��utf-8�ķ�ʽ���б���ת��
			try {
				key=new String(key.getBytes("ISO8859-1"),"UTF-8");
				value=new String(value.getBytes("ISO8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				System.out.println("����ʧ��");
				e.printStackTrace();
			}
			//����map
			map.put(key,value);
		}
		return map;
	}
	
	// ����ļ��������з��ĳҪ��������ļ�,���磬�ļ������ֺ�׺�ǡ�.avi����
	public static File[] getAnyFile(String dir, String req) {
		// dir = "F:/workspace1/dom4j/WebRoot/WEB-INF/classes";
		// req = "com";
		File file = new File(dir);// �ļ��ж��󣬴Ӹ��ļ������ҳ����Ҫ����ļ���
		File array[];// ���� ��ŷ��Ҫ����ļ�.
		File temp[] = file.listFiles();// ��ȡdir�µ������ļ���·���ļ��ͷ�·���ļ���
		File dic[] =null;// ���ڴ�� dir�·��Ҫ���·���ļ��µķ�·���ļ���
		File nonDic[];// ���ڴ�� dir�·��Ҫ��ķ�·���ļ���
		
		ArrayList list=new ArrayList();
		for (int i = 0; i < temp.length; i++) {// �����Ŀ¼�򡣡�
			File d[]=null;
			if (temp[i].isDirectory()) {// �����Ŀ¼�򡣡�
				d=getAnyFile(temp[i].getAbsolutePath(), req);
				File f=null;
				for(int j=0;j<d.length;j++){
					f=d[j];
					list.add(f);
					
				}
			}
		}
		//System.out.println(list);
		dic=new File[list.size()];
		for(int i=0;i<list.size();i++){
			dic[i]=(File)list.get(i);
		}
		
		// ����Ŀ¼����ô�����к�׺���ļ�����ô������Щ�ļ�
		nonDic = file.listFiles(new DirFilter(req));
		array = new File[dic.length + nonDic.length];
		System.arraycopy(dic, 0, array, 0, dic.length);
		System.arraycopy(nonDic, 0, array, dic.length, nonDic.length);
		return array;
	}
	// �ļ�������
	static class DirFilter implements FilenameFilter {
		String str;
		DirFilter(String str) {
			this.str = str;
		}
		public boolean accept(File dir, String name) {
			if (null == str || "".equals(str))// ���û���κι��Ҫ����ô����true
				return true;
			String f = new File(name).getName();// ����...
			return f.indexOf(str) != -1;
		}
	}
	
	public static String toLeftSlash(String path){
		if(null==path) return path;
		return path.replaceAll("\\\\", "/");
	}
	
	/**   
	* @Description ͼƬѹ�� 
	* ���ȱ���ѹ������Ҫ����ѹ������
	* ���̶����ѹ������Ҫ����outputWidth��outputHeight
	* ������ã���ᰴĬ�ϵ�150*150
	* @param sourcePath ԭ·��
	* @param targetPath	Ŀ��·��
	* @param proportion	�Ƿ�ȱ���ѹ��
	* @param rate	ѹ������ >1��Ŵ�<1��ѹ��
	* @param outputWidth ѹ������
	* @param outputHeight ѹ����߶�
	* @return      
	*   
	*/
	public static boolean compressionImage(String sourcePath,String targetPath,boolean proportion,Double rate,Integer outputWidth,Integer outputHeight) { 
        File file = new File(targetPath);  
        FileOutputStream tempout = null;  
        try {  
            tempout = new FileOutputStream(file);  
        } catch (Exception ex) {  
            System.out.println(ex.toString());  
        }  
        Image img = null;  
        Toolkit tk = Toolkit.getDefaultToolkit();  
        Applet app = new Applet();  
        MediaTracker mt = new MediaTracker(app);  
        try {  
            img = tk.getImage(sourcePath);  
            mt.addImage(img, 0);  
            mt.waitForID(0);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        if (img.getWidth(null) == -1) {  
        	logger.error(" can't read,retry!" + "<BR>");
            return false;  
        } else {  
            int new_w;  
            int new_h;  
            if (proportion) { // �ж��Ƿ��ǵȱ�����.  
                // Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�  
               /* double rate1 = ((double) img.getWidth(null))  
                        / (double) outputWidth + 0.1;  
                double rate2 = ((double) img.getHeight(null))  
                        / (double) outputHeight + 0.1;  
                double rate = rate1 > rate2 ? rate1 : rate2;  */
                new_w = (int) (((double) img.getWidth(null)) * rate);  
                new_h = (int) (((double) img.getHeight(null)) * rate);  
            } else {  
            	outputWidth = outputWidth==null?Constants.IMAGE_COMPRESSION_DEFAULT_WIDTH:outputWidth;
        		outputHeight = outputHeight==null?Constants.IMAGE_COMPRESSION_DEFAULT_HEIGHT:outputHeight;
                new_w = outputWidth; // �����ͼƬ���  
                new_h = outputHeight; // �����ͼƬ�߶�  
            }  
            BufferedImage buffImg = new BufferedImage(new_w, new_h,  
                    BufferedImage.TYPE_INT_RGB);  
            Graphics g = buffImg.createGraphics();  
            g.setColor(Color.white);  
            g.fillRect(0, 0, new_w, new_h);  
            g.drawImage(img, 0, 0, new_w, new_h, null);  
            g.dispose();  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(tempout);  
            try {  
                encoder.encode(buffImg);  
                tempout.close();  
            } catch (IOException ex) {  
                System.out.println(ex.toString());  
            }  
        }  
        return true;  
    }  
	public static void main(String args[]) throws IOException{
		//
		//F:\\svnSpace\\lyl\\doc\\99_temp\\accessory\\5_105.html
		//System.out.println(getFileContent(new File("F:\\svnSpace\\lyl\\doc\\99_temp\\accessory\\5_105.html")));
		
		//System.out.println(createHtmlFile(null,null));
		
		//Collection<MimeType> mimeTypes = MimeUtil.getMimeTypes(new File("C:/Users/Edward\\Desktop\\01_spring3.0-beanע��.htm"));  
		//System.out.println(mimeTypes);
		
		//System.out.println(createUploadFileName("aaa.html",""));
		
		File file=new File("C:/Users/Edward\\Desktop\\a\\");
		
		File parent=file.getParentFile();
		parent.mkdirs();
		file.createNewFile();
		
		//System.out.println(file.getParentFile());
		
		
		System.out.println(file.getName());
		System.out.println(getSmpleFileName("C:/Users/Edward\\Desktop\\a\\a"));
		
		//if(!file.exists()) file.createNewFile();;
	}
}
