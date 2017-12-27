package indi.liudalei.eidea.devs.db;

import indi.liudalei.eidea.core.entity.bo.ColumnMetaDataBo;
import indi.liudalei.eidea.core.entity.bo.ForeignKeyBo;
import indi.liudalei.eidea.core.entity.bo.UniqueIndexBo;
import indi.liudalei.eidea.core.dao.TableDao;
import indi.liudalei.eidea.util.StringUtil;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/9/28 10:13.
 * 生成数据库字典 excel文件
 */
public class ExportDbStructureMain {
    private static ApplicationContext applicationContext;
    private TableDao tableDao;
    private HSSFWorkbook wb;

    ExportDbStructureMain() {

        wb = new HSSFWorkbook();
        tableDao = applicationContext.getBean(TableDao.class);
        ExcelStyle.init(wb);
    }

    public static void main(String[] args) {
        applicationContext = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        ExportDbStructureMain exportDbStructureMain = new ExportDbStructureMain();
        exportDbStructureMain.doExport();
    }

    public void doExport() {

        List<String> allTables = tableDao.getAllTableName();
        exportCatalog(allTables);
        for (String table : allTables) {
            exportTableDetail(table);
        }
        writeReport();
    }

    private void exportCatalog(List<String> allTables) {
        Sheet catalog = wb.createSheet("目录");
        catalog.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 1));
        Cell title = cell(catalog, 0, 0, ExcelStyle.getTitleStyle());
        title.setCellValue("数据字典目录");
        Cell tableNameHeader = cell(catalog, 1, 0, ExcelStyle.getHeadStyle());
        tableNameHeader.setCellValue("表名");
        Cell tableDescrHeader = cell(catalog, 1, 1, ExcelStyle.getHeadStyle());
        tableDescrHeader.setCellValue("备注");

        catalog.getRow(0).setHeight((short) 700);
        catalog.getRow(1).setHeight((short) 450);
        for (int i = 0; i < allTables.size(); i++) {
            String name = allTables.get(i);
            Cell tableName = cell(catalog, i + 2, 0, ExcelStyle.getHeadStyle());
            tableName.setCellStyle(ExcelStyle.getContentStyle());
            tableName.setCellValue(name);
            Hyperlink hyperlink = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
            // "#"表示本文档    "明细页面"表示sheet页名称  "A10"表示第几列第几行
            hyperlink.setAddress("#" + name + "!A1");
            tableName.setHyperlink(hyperlink);

            String descr = tableDao.getCommentByTableName(name);
            if (descr != null) {
                Cell descrCel = cell(catalog, i + 2, 1, ExcelStyle.getHeadStyle());
                descrCel.setCellStyle(ExcelStyle.getContentStyle());
                descrCel.setCellValue(descr);
            }

        }

        catalog.autoSizeColumn(0, true);
        catalog.autoSizeColumn(1, true);


    }

    private void exportTableDetail(String tableName) {
        Sheet sheet = wb.createSheet(tableName);
        sheet.addMergedRegion(new CellRangeAddress(0, (short) 0, 0, (short) 5));
        Cell title = cell(sheet, 0, 0, ExcelStyle.getTitleStyle());

        String remark_ = tableDao.getCommentByTableName(tableName);
        if (StringUtil.isEmpty(remark_)) {
            remark_ = "";
        } else {
            remark_ = "(" + remark_ + ")";
        }
        title.setCellValue(tableName + remark_);
        Cell columnNameHeader = cell(sheet, 1, 0, ExcelStyle.getHeadStyle());
        columnNameHeader.setCellValue("字段名");
        Cell columnTypeHeader = cell(sheet, 1, 1, ExcelStyle.getHeadStyle());
        columnTypeHeader.setCellValue("字段类型");
        Cell columnSizeHeader = cell(sheet, 1, 2, ExcelStyle.getHeadStyle());
        columnSizeHeader.setCellValue("字段长度");
        Cell nullableHeader = cell(sheet, 1, 3, ExcelStyle.getHeadStyle());
        nullableHeader.setCellValue("是否为空");
        Cell defaultHeader = cell(sheet, 1, 4, ExcelStyle.getHeadStyle());
        defaultHeader.setCellValue("默认值");
        Cell remarkHeader = cell(sheet, 1, 5, ExcelStyle.getHeadStyle());
        remarkHeader.setCellValue("备注");
        sheet.getRow(0).setHeight((short) 700);
        sheet.getRow(1).setHeight((short) 450);
        List<ColumnMetaDataBo> columnMetaDataDtoList = tableDao.getTableColumns(tableName);
        for (int i = 0; i < columnMetaDataDtoList.size(); i++) {
            ColumnMetaDataBo columnMetaDataDto = columnMetaDataDtoList.get(i);
            Cell columnName = cell(sheet, i + 2, 0, ExcelStyle.getContentStyle());
            columnName.setCellValue(columnMetaDataDto.getColumnName());
            Cell columnType = cell(sheet, i + 2, 1, ExcelStyle.getContentStyle());
            columnType.setCellValue(columnMetaDataDto.getType());
            Cell columnSize = cell(sheet, i + 2, 2, ExcelStyle.getContentStyle());
            columnSize.setCellValue(columnMetaDataDto.getColumnSize());
            Cell nullable = cell(sheet, i + 2, 3, ExcelStyle.getContentStyle());
            nullable.setCellValue(columnMetaDataDto.getNullable() ? "是" : "否");
            Cell defaultValue = cell(sheet, i + 2, 4, ExcelStyle.getContentStyle());
            defaultValue.setCellValue(columnMetaDataDto.getColumnDefault());
            Cell remark = cell(sheet, i + 2, 5, ExcelStyle.getContentStyle());
            remark.setCellValue(columnMetaDataDto.getRemarks());
        }

        int rowNumber = columnMetaDataDtoList.size() + 2;

        Cell back = cell(sheet, rowNumber, 2, ExcelStyle.getContentStyle());
        Hyperlink hyperlink = new HSSFHyperlink(Hyperlink.LINK_DOCUMENT);
        // "#"表示本文档    "明细页面"表示sheet页名称  "A10"表示第几列第几行
        hyperlink.setAddress("#目录!A1");
        back.setHyperlink(hyperlink);
        back.setCellValue("返回目录");
        rowNumber++;
        String primarkKey = tableDao.getPrimaryKey(tableName);
        Cell primarkKeyLabel = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        primarkKeyLabel.setCellValue("主键");
        Cell primarkKeyValue = cell(sheet, rowNumber, 8, ExcelStyle.getContentStyle());
        primarkKeyValue.setCellValue(primarkKey);
        rowNumber++;
        rowNumber++;
        sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, (short) 7, (short) 8));
        Cell indexTitle = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        indexTitle.setCellValue("索引");

        rowNumber++;
        List<UniqueIndexBo> uniqueIndexDtos = tableDao.getUniqueIndex(tableName);
        Cell colNameLabel = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        Cell indexNameLabel = cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        colNameLabel.setCellValue("字段");
        indexNameLabel.setCellValue("索引名");
        rowNumber++;
        for (UniqueIndexBo uniqueIndexDto : uniqueIndexDtos) {
            Cell indexColumn = cell(sheet, rowNumber, 7, ExcelStyle.getContentStyle());
            indexColumn.setCellValue(uniqueIndexDto.getIndexName());
            Cell indexName = cell(sheet, rowNumber, 8, ExcelStyle.getContentStyle());
            indexName.setCellValue(uniqueIndexDto.getIndexColumnName());
            rowNumber++;
        }
        rowNumber++;
        List<ForeignKeyBo> foreignKeyList = tableDao.getImportedKeys(tableName);
        Cell foreignTitle = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 9, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 10, ExcelStyle.getContentTitleStyle());
        sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, (short) 7, (short) 10));
        foreignTitle.setCellValue("外键");
        rowNumber++;


        Cell fkColumnName = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        Cell pkTableName = cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        Cell pkColumnName = cell(sheet, rowNumber, 9, ExcelStyle.getContentTitleStyle());
        Cell fkName = cell(sheet, rowNumber, 10, ExcelStyle.getContentTitleStyle());
        fkColumnName.setCellValue("字段");
        pkTableName.setCellValue("关联表");
        pkColumnName.setCellValue("关联字段");
        fkName.setCellValue("外键名");

        rowNumber++;
        for (ForeignKeyBo foreignKey : foreignKeyList) {
            Cell fkColumnNameValue = cell(sheet, rowNumber, 7, ExcelStyle.getContentStyle());
            Cell pkTableNameValue = cell(sheet, rowNumber, 8, ExcelStyle.getContentStyle());
            Cell pkColumnNameValue = cell(sheet, rowNumber, 9, ExcelStyle.getContentStyle());
            Cell fkNameValue = cell(sheet, rowNumber, 10, ExcelStyle.getContentStyle());

            pkTableNameValue.setCellValue(foreignKey.getPkTableName());
            pkColumnNameValue.setCellValue(foreignKey.getPkColumnName());
            fkColumnNameValue.setCellValue(foreignKey.getFkColumnName());
            fkNameValue.setCellValue(foreignKey.getFkName());
            rowNumber++;
        }
        rowNumber++;
        Cell exportTitle = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 9, ExcelStyle.getContentTitleStyle());
        cell(sheet, rowNumber, 10, ExcelStyle.getContentTitleStyle());
        sheet.addMergedRegion(new CellRangeAddress(rowNumber, rowNumber, (short) 7, (short) 10));
        exportTitle.setCellValue("外表引用字段");
        rowNumber++;
        List<ForeignKeyBo> exportKeys = tableDao.getExportedKeys(tableName);
        Cell exColumn = cell(sheet, rowNumber, 7, ExcelStyle.getContentTitleStyle());
        Cell exFKTable = cell(sheet, rowNumber, 8, ExcelStyle.getContentTitleStyle());
        Cell exFKColumn = cell(sheet, rowNumber, 9, ExcelStyle.getContentTitleStyle());
        Cell exFkName = cell(sheet, rowNumber, 10, ExcelStyle.getContentTitleStyle());
        exColumn.setCellValue("字段");
        exFKTable.setCellValue("表");
        exFKColumn.setCellValue("表字段");
        exFkName.setCellValue("外键名称");
        rowNumber++;
        for (ForeignKeyBo foreignKey : exportKeys) {
            Cell exColumnValue = cell(sheet, rowNumber, 7, ExcelStyle.getContentStyle());
            Cell exFKTableValue = cell(sheet, rowNumber, 8, ExcelStyle.getContentStyle());
            Cell exFKColumnValue = cell(sheet, rowNumber, 9, ExcelStyle.getContentStyle());
            Cell exFkNameValue = cell(sheet, rowNumber, 10, ExcelStyle.getContentStyle());
            exColumnValue.setCellValue(foreignKey.getPkColumnName());
            exFKTableValue.setCellValue(foreignKey.getFkTableName());
            exFKColumnValue.setCellValue(foreignKey.getFkColumnName());
            exFkNameValue.setCellValue(foreignKey.getFkName());
            rowNumber++;
        }


        sheet.autoSizeColumn(0, true);
        sheet.autoSizeColumn(1, true);
        sheet.autoSizeColumn(2, true);
        sheet.autoSizeColumn(3, true);
        sheet.autoSizeColumn(4, true);
        sheet.autoSizeColumn(5, true);
        sheet.autoSizeColumn(7, true);
        sheet.autoSizeColumn(8, true);
        sheet.autoSizeColumn(9, true);
        sheet.autoSizeColumn(10, true);
        sheet.autoSizeColumn(11, true);
        sheet.autoSizeColumn(12, true);
    }

    private Cell cell(Sheet sheet, int rowIndex, int columnIndex,
                      CellStyle style) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.createCell(columnIndex);
        cell.setCellStyle(style);

        return cell;
    }

    private void writeReport() {
        try {
            FileOutputStream fileOut = new FileOutputStream("DatabaseDir.xls");
            wb.write(fileOut);
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
