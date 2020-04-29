package database.specification;

public class GetReceiptsByDate implements Specification {

    private int storeId;
    private int month;
    private int year;

    public GetReceiptsByDate(int storeId, int month, int year) {
        this.storeId = storeId;
        this.month = month;
        this.year = year;
    }

    @Override
    public QueryInfo toQueryInfo() {

        String sql = "SELECT r.* FROM receipts r " +
                "join stores s " +
                "on r.fk_receipt_store = s.id " +
                "where year(r.date_time) = ? " +
                "and month(r.date_time) = ? " +
                "and s.id = ? " +
                "group by r.id ";

        return new QueryInfo(sql,new Object[]{year,month,storeId});
    }
}
