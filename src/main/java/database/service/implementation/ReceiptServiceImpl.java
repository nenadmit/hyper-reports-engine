package database.service.implementation;

import database.repositoryy.Repository;
import database.service.interfaces.ReceiptService;
import database.specification.FindBySingleFieldSpec;
import database.specification.GetReceiptsByDate;
import deserialization.pojo.company.Invoice;
import deserialization.pojo.company.Receipt;
import deserialization.pojo.company.Store;

import java.util.List;

public class ReceiptServiceImpl extends GenericService<Receipt> implements ReceiptService {

    private Repository<Receipt> repository;

    public ReceiptServiceImpl(Repository<Receipt> repository) {
        super(repository, Receipt.class);
        this.repository = repository;
    }


    @Override
    public List<Receipt> getAllByStore(int storeId) {
        return repository.query(new FindBySingleFieldSpec<>(storeId,
                Receipt.Fields.TABLE.getValue(),
                Receipt.Fields.FK_RECEIPT_STORE.getValue()));
    }

    @Override
    public List<Receipt> getAllByCard(int cardId) {
        return repository.query(new FindBySingleFieldSpec<>(cardId,
                Receipt.Fields.TABLE.getValue(),
                Receipt.Fields.FK_RECEIPT_CARD.getValue()));
    }

    @Override
    public List<Receipt> saveAll(List<Receipt> receipts) {
        return repository.createAll(receipts);
    }

    @Override
    public List<Receipt> findAllByStoreAndDate(int storeId, int month, int year) {
        return repository.query(new GetReceiptsByDate(storeId,month,year));
    }

}
