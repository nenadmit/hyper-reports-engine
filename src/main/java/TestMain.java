import com.sun.xml.bind.v2.model.core.ID;
import database.CompanyDataProcessor;
import database.FlywayConfiguration;
import database.service.interfaces.CompanyService;
import database.service.interfaces.StoreService;
import database.specification.QueryInfo;
import deserialization.pojo.company.Company;
import deserialization.pojo.company.Store;
import org.apache.poi.ss.formula.functions.T;
import org.flywaydb.core.Flyway;
import sftp.SftpDownloader;
import sftp.SftpDownloaderConfigurer;
import sftp.XmlFilesService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

public class TestMain<T> {



    public static void main(String[] args) throws FileNotFoundException {

        Flyway flyway = FlywayConfiguration.getFlyway();
        flyway.migrate();

        CompanyDataProcessor processor = new Main().dataProcessor();

        SftpDownloader downloader = new SftpDownloader(new SftpDownloaderConfigurer(), new XmlFilesService());
        downloader.downloadFiles("data/");

        long start = System.nanoTime();

        for (File file: new File("data").listFiles()){
            System.out.println("parsing: " + file.getName());
            processor.saveData(new FileInputStream(file));
        }

        long end = System.nanoTime();

        System.out.println("Runtime: " + ((end-start)/1000000000 +" seconds!"));

    }

}
