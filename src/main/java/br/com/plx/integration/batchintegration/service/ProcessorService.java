package br.com.plx.integration.batchintegration.service;

import br.com.plx.integration.batchintegration.model.FileMetaDataInfo;
import br.com.plx.integration.batchintegration.model.ProcessorInfo;
import br.com.plx.integration.batchintegration.persistence.entity.DataEntity;
import br.com.plx.integration.batchintegration.persistence.entity.DataExceptionEntity;
import br.com.plx.integration.batchintegration.persistence.repository.DataExceptionRepository;
import br.com.plx.integration.batchintegration.persistence.repository.DataRepository;
import br.com.plx.integration.batchintegration.persistence.repository.ReportRepository;
import br.com.plx.integration.batchintegration.utils.CSVFileUtil;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProcessorService {

    private Logger logger = Logger.getLogger("jsonLoggerInfo");

    @Autowired
    private DataRepository dataRepository;
    @Autowired
    private DataExceptionRepository dataExceptionRepository;
    @Autowired
    private ReportRepository reportRepository;

    public ProcessorInfo execute(String path) throws FileNotFoundException {

        CSVFileUtil.listCSVFileFromPath(path).stream().parallel().forEach(file ->{

            try {
                loadRegisterToDatabase(file);
            } catch (IOException ioException) {
                logger.log(Level.ALL, "Error of ioException: " + ioException.getLocalizedMessage());
                dataExceptionRepository.save(DataExceptionEntity.builder()
                                .fileName(file.getFileName())
                                .erroMessage(ioException.getLocalizedMessage())
                                .insetDate(LocalDate.now())

                        .build());
            } catch (CsvValidationException csvValidationException) {
                logger.log(Level.ALL, "Error of csvFormat: " + csvValidationException.getLocalizedMessage());
                dataExceptionRepository.save(DataExceptionEntity.builder()
                                .fileName(file.getFileName())
                                .lineError(csvValidationException.getLineNumber())
                                .erroMessage(csvValidationException.getLocalizedMessage())
                                .insetDate(LocalDate.now())
                                .payload(Optional.ofNullable(csvValidationException.getLine()).isPresent() ?
                                        Arrays.asList(csvValidationException.getLine()).toString() : "NO DATA")
                        .build());

            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){
                logger.log(Level.ALL, "Error of arrayIndexOutOfBoundsException: " + arrayIndexOutOfBoundsException.getLocalizedMessage());
                dataExceptionRepository.save(DataExceptionEntity.builder()
                        .fileName(file.getFileName())
                        .erroMessage(arrayIndexOutOfBoundsException.getLocalizedMessage())
                        .insetDate(LocalDate.now())
                        .build()) ;

            }

        });



        return null;
    }

    private void loadRegisterToDatabase(FileMetaDataInfo file) throws IOException, CsvValidationException, ArrayIndexOutOfBoundsException {
        final CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();


        try(CSVReader csvReader =  new CSVReaderBuilder(new FileReader(file.getFile()))
                .withSkipLines(1)
                .withCSVParser(parser)
                .build()) {
            String []values =  null;

            while ((values = csvReader.readNext()) != null){

                if(values.length ==3){
                    final String identifier = values[0];
                    final String name = values[1];
                    final String version = values[2];
                    dataRepository.save(DataEntity.builder()
                            .fileName(file.getFileName())
                            .insetDate(LocalDate.now())
                            .identifier(identifier)
                            .name(name)
                            .version(version)
                            .reprocessed(false)
                            .build());
                    values = null;
                    logger.info("ITEM SAVED => " + identifier);
                }else {
                    dataExceptionRepository.save(DataExceptionEntity.builder()
                                    .fileName(file.getFileName())
                                    .payload(String.valueOf(values))
                                    .insetDate(LocalDate.now())
                                    .skiped(Boolean.TRUE)
                            .build());
                    logger.info("ITEM SKIP => " + values);

                }

            }
        }

    }
}
