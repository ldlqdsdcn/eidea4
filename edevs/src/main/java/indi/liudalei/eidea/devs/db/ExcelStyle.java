package indi.liudalei.eidea.devs.db;

import lombok.Getter;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Created by 刘大磊 on 2016/9/28 10:24.
 */

public class ExcelStyle {
    @Getter
    private static CellStyle titleStyle; // 标题行样式
    private static Font titleFont; // 标题行字体
    @Getter
    private static CellStyle dateStyle; // 日期行样式
    private static Font dateFont; // 日期行字体
    @Getter
    private static CellStyle headStyle; // 表头行样式
    private static Font headFont; // 表头行字体
    @Getter
    private static CellStyle contentTitleStyle;
    @Getter
    private static CellStyle contentStyle; // 内容行样式
    private static Font contentFont; // 内容行字体

    public static void init(HSSFWorkbook wb) {
        titleFont = wb.createFont();
        titleStyle = wb.createCellStyle();
        dateStyle = wb.createCellStyle();
        dateFont = wb.createFont();
        headStyle = wb.createCellStyle();
        headFont = wb.createFont();
        contentStyle = wb.createCellStyle();
        contentFont = wb.createFont();
        contentTitleStyle=wb.createCellStyle();
        initTitleCellStyle();
        initTitleFont();
        initDateCellStyle();
        initDateFont();
        initHeadCellStyle();
        initHeadFont();
        initContentCellStyle();
        initContentFont();
    }
    /**
     * @Description: 初始化标题行样式
     */
    private static void initTitleCellStyle() {
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setFont(titleFont);
        titleStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * @Description: 初始化日期行样式
     */
    private static void initDateCellStyle() {
        dateStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
        dateStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dateStyle.setFont(dateFont);
        dateStyle.setFillBackgroundColor(IndexedColors.SKY_BLUE.getIndex());
    }

    /**
     * @Description: 初始化表头行样式
     */
    private static void initHeadCellStyle() {
        headStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        headStyle.setFont(headFont);
        headStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
        headStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        headStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * @Description: 初始化内容行样式
     */
    private static void initContentCellStyle() {
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentStyle.setFont(contentFont);
        contentStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        contentStyle.setWrapText(true); // 字段换行


        contentTitleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentTitleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        contentTitleStyle.setFont(headFont);
        contentTitleStyle.setBorderTop(CellStyle.BORDER_THIN);
        contentTitleStyle.setBorderBottom(CellStyle.BORDER_THIN);
        contentTitleStyle.setBorderLeft(CellStyle.BORDER_THIN);
        contentTitleStyle.setBorderRight(CellStyle.BORDER_THIN);
        contentTitleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
        contentTitleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        contentTitleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        contentTitleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
        contentTitleStyle.setWrapText(true); // 字段换行
    }

    /**
     * @Description: 初始化标题行字体
     */
    private static void initTitleFont() {
        titleFont.setFontName("华文楷体");
        titleFont.setFontHeightInPoints((short) 20);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setCharSet(Font.DEFAULT_CHARSET);
        titleFont.setColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * @Description: 初始化日期行字体
     */
    private static void initDateFont() {
        dateFont.setFontName("隶书");
        dateFont.setFontHeightInPoints((short) 10);
        dateFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        dateFont.setCharSet(Font.DEFAULT_CHARSET);
        dateFont.setColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * @Description: 初始化表头行字体
     */
    private static void initHeadFont() {
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 10);
        headFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headFont.setCharSet(Font.DEFAULT_CHARSET);
        headFont.setColor(IndexedColors.BLACK.getIndex());
    }

    /**
     * @Description: 初始化内容行字体
     */
    private static void initContentFont() {
        contentFont.setFontName("宋体");
        contentFont.setFontHeightInPoints((short) 10);
        contentFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        contentFont.setCharSet(Font.DEFAULT_CHARSET);
        contentFont.setColor(IndexedColors.BLACK.getIndex());
    }

    public static void calcAndSetRowHeigt(HSSFRow sourceRow) {
        //原行高
        short height = sourceRow.getHeight();
        //计算后的行高
        double maxHeight = height;
        for (int cellIndex = sourceRow.getFirstCellNum(); cellIndex <= sourceRow.getPhysicalNumberOfCells(); cellIndex++) {
            HSSFCell sourceCell = sourceRow.getCell(cellIndex);
            //单元格的内容
            String cellContent = getCellContentAsString(sourceCell);
            if(null == cellContent || "".equals(cellContent)){
                continue;
            }
            //单元格的宽度
            int columnWidth = getCellWidth(sourceCell);
            System.out.println("单元格的宽度 : " + columnWidth + "    单元格的高度 : " + maxHeight + ",    单元格的内容 : " + cellContent);
            HSSFCellStyle cellStyle = sourceCell.getCellStyle();
            HSSFFont font = cellStyle.getFont(sourceRow.getSheet().getWorkbook());
            //字体的高度
            short fontHeight = font.getFontHeight();

            //cell内容字符串总宽度
            double cellContentWidth = cellContent.getBytes().length * 2 * 256;

            //字符串需要的行数 不做四舍五入之类的操作
            double stringNeedsRows =(double)cellContentWidth / columnWidth;
            //小于一行补足一行
            if(stringNeedsRows < 1.0){
                stringNeedsRows = 1.0;
            }

            //需要的高度 			(Math.floor(stringNeedsRows) - 1) * 40 为两行之间空白高度
            double stringNeedsHeight = (double)fontHeight * stringNeedsRows;
            if(stringNeedsHeight > maxHeight){
                maxHeight = stringNeedsHeight;
            }
            System.out.println("字体高度 : " + fontHeight + ",    字符串宽度 : " + cellContentWidth + ",    字符串需要的行数 : " + stringNeedsRows + ",   需要的高度 : " + stringNeedsHeight);
            System.out.println();
        }
        //超过原行高三倍 则为3倍 实际应用中可
        if(maxHeight/height > 5){
            maxHeight = 5 * height;
        }
        //最后取天花板防止高度不够
        maxHeight = Math.ceil(maxHeight);
        sourceRow.setHeight((short)maxHeight);
    }


    private static String getCellContentAsString(HSSFCell cell) {
        if(null == cell){
            return "";
        }
        String result = "";
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_NUMERIC:
                String s = String.valueOf(cell.getNumericCellValue());
                if (s != null) {
                    if (s.endsWith(".0")) {
                        s = s.substring(0, s.length() - 2);
                    }
                }
                result = s;
                break;
            case Cell.CELL_TYPE_STRING:
                result =String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                result = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_ERROR:
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 获取单元格及合并单元格的宽度


     * @return
     */
    private static int getCellWidth(HSSFCell cell) {
        int result = 0;
        HSSFSheet sheet = cell.getSheet();
        int rowIndex = cell.getRowIndex();
        int columnIndex = cell.getColumnIndex();

        boolean isPartOfRegion = false;
        int firstColumn = 0;
        int lastColumn = 0;
        int firstRow = 0;
        int lastRow = 0;
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            Region ca = sheet.getMergedRegionAt(i);
            firstColumn = ca.getColumnFrom();
            lastColumn = ca.getColumnTo();
            firstRow = ca.getRowFrom();
            lastRow = ca.getRowTo();
            if (rowIndex >= firstRow && rowIndex <= lastRow) {
                if (columnIndex >= firstColumn && columnIndex <= lastColumn) {
                    isPartOfRegion = true;
                    break;
                }
            }
        }
        if(isPartOfRegion){
            for (int i = firstColumn; i <= lastColumn; i++) {
                result += sheet.getColumnWidth(i);
            }
        }else{
            result = sheet.getColumnWidth(columnIndex);
        }
        return result;
    }
}
