package br.com.plx.integration.batchintegration.controllers;

import br.com.plx.integration.batchintegration.model.BucketDetail;
import br.com.plx.integration.batchintegration.model.FileMetaDataInfo;
import br.com.plx.integration.batchintegration.service.AWSS3Service;
import br.com.plx.integration.batchintegration.utils.CSVFileUtil;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/csv")
public class CSVFileController {

    @Autowired
    private S3Client amazonS3Client;

    @Autowired
    private AWSS3Service awss3Service;


    private Logger logger =  Logger.getLogger("jsonLogger");

    @PostMapping("/convert/arrays")
    public ResponseEntity<List<List<String>>> convertToArrayString() throws CsvValidationException, IOException {
        CSVFileUtil.listCSVFileFromPath("temp");
        return ResponseEntity.ok(CSVFileUtil.csvToArrayListFromFile("temp/teste.csv"));
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileMetaDataInfo>> listFilesFromPath(@RequestHeader("pathName") final String path) throws FileNotFoundException {
        logger.info("REQUISICAO");
        return ResponseEntity.ok(CSVFileUtil.listCSVFileFromPath(path));
    }

    @GetMapping("/aws/list/buckets")
    public ResponseEntity<List<BucketDetail>> listBuckets(){
        return ResponseEntity.ok(amazonS3Client.listBuckets().buckets()
                .stream().map(b-> BucketDetail.builder().name(b.name()).build())
                .toList());
    }


    @PostMapping("/aws/download/objects")
    public ResponseEntity<BucketDetail> downloadObjectsFromBucket(@RequestBody  BucketDetail bucketDetail){
        return ResponseEntity.ok(awss3Service.downloadObjectsFromBucket(bucketDetail.getName()));

    }


}
