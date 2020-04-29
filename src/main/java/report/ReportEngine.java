package report;

import database.service.interfaces.InvoiceService;
import database.service.interfaces.ReceiptService;
import database.service.interfaces.StoreService;
import deserialization.pojo.company.Company;
import deserialization.pojo.company.Invoice;
import deserialization.pojo.company.Receipt;
import deserialization.pojo.company.Store;
import report.report_data.Report;
import report.report_data.ReportData;
import report.report_data.ReportDataImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReportEngine {

    private StoreService storeService;
    private InvoiceService invoiceService;
    private ReceiptService receiptService;

    public ReportEngine(StoreService storeService, InvoiceService invoiceService, ReceiptService receiptService) {
        this.storeService = storeService;
        this.invoiceService = invoiceService;
        this.receiptService = receiptService;
    }

    //@TODO add LIMIT functionality

    public Report createStoreReport(int[] months, int year, Company company,boolean desc,int limit) {


        List<Store> stores = storeService.findByCompany(company.getId());

        Report report = new Report();

        for (Store store : stores) {
            ReportData reportData = new ReportDataImpl();

            for (int month : months) {

                List<Receipt> receipts = receiptService
                        .findAllByStoreAndDate(store.getId(), month, year);
                List<Invoice> invoices = invoiceService
                        .findAllByStoreAndDate(store.getId(), month, year);

                reportData.put(month, calculateTurnover(receipts,invoices));
                reportData.setName(store.getName());
                reportData.calculateTurnover();

            }
            report.getReportDataList().add(reportData);
        }

        Collections.sort(report.getReportDataList());
        if(!desc){
            Collections.reverse(report.getReportDataList());
        }

        report.setCellFields("STORE");

        return report;
    }

    public Report createInvoiceReport(int[] months, int year, Company company){

        List<Store> stores = storeService.findByCompany(company.getId());
        Report report = new Report();
        ReportData data = new ReportDataImpl();

        for(int month: months){

            List<Invoice> allInvoices = new ArrayList<>();
            for(Store store: stores) {

                List<Invoice> invoices = invoiceService.findAllByStoreAndDate(store.getId(), month, year);
                allInvoices.addAll(invoices);

            }
            data.put(month,calculateTurnover(new ArrayList<>(),allInvoices));
        }

        data.calculateTurnover();
        report.getReportDataList().add(data);

        report.setCellFields("INVOICE");
        return report;

    }

    public Report createReceiptReport(int[] months, int year, Company company){

        List<Store> stores = storeService.findByCompany(company.getId());
        Report report = new Report();
        ReportData data = new ReportDataImpl();

        for(int month: months){

            List<Receipt> allReceipts= new ArrayList<>();
            for(Store store: stores) {

                List<Receipt> receipts = receiptService.findAllByStoreAndDate(store.getId(), month, year);
                allReceipts.addAll(receipts);

            }
            data.put(month,calculateTurnover(allReceipts,new ArrayList<>()));
        }

        data.calculateTurnover();
        report.getReportDataList().add(data);

        report.setCellFields("RECEIPT");
        return report;

    }


    public Report createPaymentTypeReport(){
        return null;
    }


    private static double calculateTurnover(List<Receipt> receipts, List<Invoice> invoices) {

        double total = 0;
        for (Receipt receipt : receipts) {
            total = total + receipt.getTotal();
        }

        for (Invoice invoice : invoices) {
            total = total + invoice.getTotal();
        }

        return total;

    }

}
