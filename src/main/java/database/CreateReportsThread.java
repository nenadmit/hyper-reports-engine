package database;

import database.service.interfaces.ReceiptService;
import deserialization.pojo.company.Receipt;

import java.util.List;

public class CreateReportsThread implements Runnable {

    private List<Receipt> receipts;
    private ReceiptService service;

    public CreateReportsThread(List<Receipt> receipts, ReceiptService service) {
        this.receipts = receipts;
        this.service = service;
    }

    @Override
    public void run() {
        service.createAll(receipts);
    }
}
