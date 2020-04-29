package database;

import database.service.interfaces.*;
import deserialization.XmlParser;
import deserialization.pojo.company.*;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CompanyDataProcessor {

    private CompanyService companyService;
    private StoreService storeService;
    private CustomerService customerService;
    private CardService cardService;
    private ReceiptService receiptService;
    private InvoiceService invoiceService;
    private XmlParser<Company> parser;

    public CompanyDataProcessor(CompanyService companyService,
                                StoreService storeService,
                                CustomerService customerService,
                                CardService cardService,
                                ReceiptService receiptService,
                                InvoiceService invoiceService,
                                XmlParser parser) {
        this.companyService = companyService;
        this.storeService = storeService;
        this.customerService = customerService;
        this.cardService = cardService;
        this.receiptService = receiptService;
        this.invoiceService = invoiceService;
        this.parser = parser;
    }

    public void saveData(InputStream fileInputStream) {

        Company company = new Company();

        try {
            company = parser.parse(fileInputStream);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        List<Invoice> invoices = new ArrayList<>();
        List<Receipt> receipts = new ArrayList<>();
        List<Store> stores = company.getStores();

        company = companyService.save(company);

        for (Store store : stores) {
            store.setCompanyId(company.getId());
        }
        stores = storeService.createAll(stores);

        for (Store store : stores) {

            for (Receipt receipt : store.getReceipts()) {
                receipt.setStoreId(store.getId());
                if (receipt.getPaymentType().equals("card")) {

                    Card card = cardService.save(receipt.getCard());
                    receipt.setCardId(card.getId());
                    receipts.add(receipt);
                }
            }
            for (Invoice invoice : store.getInvoices()) {
                invoice.setStoreId(store.getId());
                if (invoice.getPaymentType().equals("card")) {

                    Card card = cardService.save(invoice.getCard());
                    invoice.setCardId(card.getId());
                }
                Customer customer = customerService.save(invoice.getCustomer());
                invoice.setCustomerId(customer.getId());
                invoices.add(invoice);
            }

        }

        receiptService.createAll(receipts);
        invoiceService.createAll(invoices);
    }


    public CompanyService getCompanyService() {
        return companyService;
    }

    public StoreService getStoreService() {
        return storeService;
    }

    public CustomerService getCustomerService() {
        return customerService;
    }

    public CardService getCardService() {
        return cardService;
    }

    public ReceiptService getReceiptService() {
        return receiptService;
    }

    public InvoiceService getInvoiceService() {
        return invoiceService;
    }

    public XmlParser<Company> getParser() {
        return parser;
    }
}
