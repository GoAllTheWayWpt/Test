package com.feicui.utils;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.feicui.model.Down;

public class DownPOIUtils {
 
	/**
	 * 
	 * @param response:响应对象，类型是HttpServletResponse
	 * @param map:要封装的信息的map容器，其中key为Student，value为String类型的，在这里代表分数
	 * @throws Exception:代表异常对象
	 */
	public static void downPoi(HttpServletResponse response,
			List<Down> map) throws Exception {
		String fname = "bookshopping" + getTimeStamp();// Excel文件名
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setHeader("Content-disposition", "attachment; filename="
				+ fname + ".xls"); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
		response.setContentType("application/msexcel");
		try {
			new DownPOIUtils().new POIS().createFixationSheet(os, map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	/**
	 * 该方法用来产生一个时间字符串（即：时间戳）
	 * @return
	 */
	public static String getTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:MM:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	class POIS {
		 
		public void createFixationSheet(OutputStream os,
				List<Down> map) throws Exception {
			// 创建工作薄
			HSSFWorkbook wb = new HSSFWorkbook();
			// 在工作薄上建一张工作表
			HSSFSheet sheet = wb.createSheet();
			HSSFRow row = sheet.createRow((short) 0);
			sheet.createFreezePane(0, 1);
			cteateCell(wb, row, (short) 0, "名称");
			cteateCell(wb, row, (short) 1, "价格");
			cteateCell(wb, row, (short) 2, "类别");
			cteateCell(wb, row, (short) 3, "库存");
			cteateCell(wb, row, (short) 4, "描述");
			cteateCell(wb, row, (short) 5, "日期");
			cteateCell(wb, row, (short) 6, "热度");
			int i = 0;
			Iterator<Down> iterator = map.iterator();
			while (iterator.hasNext()) {
				HSSFRow rowi = sheet.createRow((short) (++i));
				Down down = iterator.next();
				for (int j = 0; j < 6; j++) {
					cteateCell(wb, rowi, (short) 0, down.getName());
					cteateCell(wb, rowi, (short) 1, down.getPrice().toString());
					cteateCell(wb, rowi, (short) 2, down.getCategory());
					cteateCell(wb, rowi, (short) 3, down.getPnum().toString());
					cteateCell(wb, rowi, (short) 4, down.getDescription());
					cteateCell(wb, rowi, (short) 5, down.getCbtime());
					cteateCell(wb, rowi, (short) 6, down.getNum().toString());
				}
			}
			wb.write(os);
			os.flush();
			os.close();
			System.out.println("文件生成");
 
		}
 
		@SuppressWarnings("deprecation")
		private void cteateCell(HSSFWorkbook wb, HSSFRow row, short col,
				String val) {
			HSSFCell cell = row.createCell(col);
			cell.setCellValue(val);
			HSSFCellStyle cellstyle = wb.createCellStyle();
			cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
			cell.setCellStyle(cellstyle);
		}
	}
}
