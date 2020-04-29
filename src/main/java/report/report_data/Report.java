package report.report_data;

import java.util.ArrayList;
import java.util.List;

public class Report {

    private List<String> cellFields;
    private List<ReportData> reportDataList;


    public List<ReportData> getReportDataList() {
        if(reportDataList == null){
            reportDataList = new ArrayList<>();
        }

        return reportDataList;
    }

    public void setReportDataList(List<ReportData> reportDataList) {
        this.reportDataList = reportDataList;
    }

    public void setCellFields(String firstField){

        cellFields = new ArrayList<>();

        cellFields.add(firstField);
        for(String month:reportDataList.get(0).getMonthTurnoverMap().keySet()){
            cellFields.add(month);
        }
        cellFields.add("TURNOVER");

    }

    public List<String> getCellFields(){
        return this.cellFields;
    }

    @Override
    public String toString() {
        return "Report{" +
                "cellFields=" + cellFields +
                ", reportDataList=" + reportDataList +
                '}';
    }
}
