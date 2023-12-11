package br.com.plx.integration.batchintegration.utils;

import br.com.plx.integration.batchintegration.model.FileMetaDataInfo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;


public class CSVFileUtil {


   public static List<List<String>> csvToArrayListFromFile(final String csvFileName) throws IOException, CsvValidationException {
       List<List<String>> arrayLines = new ArrayList<>();
       File file = ResourceUtils.getFile("classpath:" + csvFileName);
       try(CSVReader csvReader = new CSVReader(new FileReader(file));) {
           String []values =  null;
            while ((values = csvReader.readNext()) != null){
                arrayLines.add(Arrays.asList(values));

            }
       }
       return arrayLines;
   }

   public static List<FileMetaDataInfo> listCSVFileFromPath(final String pathName) throws FileNotFoundException {
       final File path = ResourceUtils.getFile("classpath:" + pathName);
       Predicate<File> nameContainsCSVFormat = fileNameFormat -> fileNameFormat.getName().contains(".csv");
       return Arrays.stream(Objects.requireNonNull(path.listFiles()))
               .filter(nameContainsCSVFormat)
               .map(FileMetaDataInfo::convert)
               .toList();

   }


    public static void writeCSVFile(final String key, final byte[] data) throws IOException {
        File myFile = new File("src/main/resources/" +key);
        OutputStream os = new FileOutputStream(myFile);
        os.write(data);
        os.close();
    }
}
