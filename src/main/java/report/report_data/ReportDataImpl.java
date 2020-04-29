package report.report_data;

import java.util.LinkedHashMap;
import java.util.Map;

public class ReportDataImpl implements ReportData {

    private String name;
    private Map<String,Double> monthTurnoverMap = new LinkedHashMap<>();
    private double turnover = 0;
    private static final  String[] MONTH_NAMES = {"January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October",
            "November", "December"};



    public String getName() {
        return name;

    }

    public void setName(String storeName) {
        this.name = storeName;
    }

    public Map<String, Double> getMonthTurnoverMap() {
        return monthTurnoverMap;
    }

    public void setMonthTurnoverMap(Map<String, Double> monthTurnoverMap) {
        this.monthTurnoverMap = monthTurnoverMap;
    }

    public void setTurnover(double total) {
        this.turnover = total;
    }

    public double getTurnover() {
        return turnover;
    }

    public void put(int month,double turnover){
        this.monthTurnoverMap.put(MONTH_NAMES[month-1],turnover);
    }

    public void calculateTurnover(){

        for(Map.Entry<String,Double> entry:monthTurnoverMap.entrySet()){
            turnover = turnover + entry.getValue();

        }
    }

    @Override
    public int compareTo(ReportData o) {
        return Double.compare(this.turnover,o.getTurnover());
    }


}
