package report;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import report.report_data.Report;
import report.report_data.ReportData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class XlsxParser {

    public void exportAsXlsx(Report report, String filename) throws IOException {


        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("test");

        int rowCounter=0;
        int cellCounter=0;

        List<String> cellFields = report.getCellFields();
        Row row = sheet.createRow(rowCounter++);

        for(String field:cellFields)
            row.createCell(cellCounter++).setCellValue(field);


        cellCounter = 0;
        for(ReportData data:report.getReportDataList()){
            row = sheet.createRow(rowCounter++);
            row.createCell(cellCounter++).setCellValue(data.getName());

            for(Map.Entry<String,Double> entry:data.getMonthTurnoverMap().entrySet()){

                row.createCell(cellCounter++).setCellValue(entry.getValue());
            }
            row.createCell(cellCounter++).setCellValue(data.getTurnover());
            cellCounter = 0;
        }

        workbook.write(new FileOutputStream(new File("export/"+filename+".xlsx")));
        workbook.close();
    }
}
