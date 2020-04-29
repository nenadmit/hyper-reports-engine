import com.beust.jcommander.ParameterException;
import database.CompanyDataProcessor;
import database.FlywayConfiguration;
import database.repositoryy.v2.RepositoryImpl;
import database.service.implementation.*;
import database.service.interfaces.*;
import deserialization.XmlParser;
import deserialization.pojo.company.*;
import jcommander.CommandLineParameters;
import jcommander.CommandLineParser;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import sftp.SftpDownloader;
import sftp.SftpDownloaderConfigurer;
import sftp.XmlFilesService;


public class Main {

    static final CommandLineParameters parameters = new CommandLineParameters();

    public static void main(String[] args) {

        Flyway flyway = FlywayConfiguration.getFlyway();

        try{
            flyway.validate();
        }catch (FlywayException e){
            flyway.migrate();
        }

        CommandLineParser parser = getParser();
        parser.setParameters(parameters);

        try {
            parser.parse(args);
        }catch (ParameterException e){
            System.out.println(e.getMessage());
            System.exit(1);
        }


    }

    private static CommandLineParser getParser() {

        Main main = new Main();

        SftpDownloaderConfigurer downloaderConfigurer = new SftpDownloaderConfigurer();
        SftpDownloader downloader = new SftpDownloader(downloaderConfigurer, new XmlFilesService());

        return new CommandLineParser(downloader, main.dataProcessor());
    }

    public CompanyDataProcessor dataProcessor() {
        CompanyService companyService = new CompanyServiceImpl(new RepositoryImpl(Company.class));
        StoreService storeService = new StoreServiceImpl(new RepositoryImpl(Store.class));
        CustomerService customerService = new CustomerServiceImpl(new RepositoryImpl(Customer.class));
        CardService cardService = new CardServiceImpl(new RepositoryImpl<>(Card.class));
        ReceiptService receiptService = new ReceiptServiceImpl(new RepositoryImpl<>(Receipt.class));
        InvoiceService invoiceService = new InvoiceServiceImpl(new RepositoryImpl<>(Invoice.class));

        return new CompanyDataProcessor(
                companyService, storeService,
                customerService, cardService,
                receiptService, invoiceService,
                new XmlParser(Company.class));


    }

}
