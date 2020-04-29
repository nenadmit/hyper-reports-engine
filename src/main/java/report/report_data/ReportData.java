package report.report_data;

import java.util.List;
import java.util.Map;

public interface ReportData extends Comparable<ReportData>  {

    String getName();
    Map<String,Double> getMonthTurnoverMap();
    void put(int month,double turnoverValue);
    double getTurnover();
    void setName(String name);
    void calculateTurnover();

}
