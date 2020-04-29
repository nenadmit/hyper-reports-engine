package database;


import database.service.interfaces.InvoiceService;
import deserialization.pojo.company.Invoice;

import java.util.List;

public class CreateInvoicesThread implements Runnable {

    List<Invoice> invoiceList;
    public InvoiceService service;

    public CreateInvoicesThread(List<Invoice> invoiceList, InvoiceService service) {
        this.invoiceList = invoiceList;
        this.service = service;
    }

    @Override
    public void run() {

        service.createAll(invoiceList);

    }
}
