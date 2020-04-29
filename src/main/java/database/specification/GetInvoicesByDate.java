package database.specification;

public class GetInvoicesByDate implements Specification {

    private int storeId;
    private int month;
    private int year;

    public GetInvoicesByDate(int storeId, int month, int year) {
        this.storeId = storeId;
        this.month = month;
        this.year = year;
    }

    @Override
    public QueryInfo toQueryInfo() {

        String sql = "SELECT i.* FROM invoices i " +
                "join stores s " +
                "on i.fk_invoice_store = s.id " +
                "where year(i.date_time) = ? " +
                "and month(i.date_time) = ? " +
                "and s.id = ? " +
                "group by i.id ";

        return new QueryInfo(sql,new Object[]{year,month,storeId});
    }
}
