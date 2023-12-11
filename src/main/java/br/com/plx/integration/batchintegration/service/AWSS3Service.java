package br.com.plx.integration.batchintegration.service;

import br.com.plx.integration.batchintegration.model.BucketDetail;
import br.com.plx.integration.batchintegration.utils.CSVFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.IOException;
import java.util.List;

@Service
public class AWSS3Service {

    @Autowired
    private S3Client amazonS3Client;

    private Integer MAX_PAGE_SIZE = 50;

    public BucketDetail downloadObjectsFromBucket(final String bucketName) throws S3Exception {

        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .maxKeys(MAX_PAGE_SIZE) // Set the maxKeys parameter to control the page size
                .build();

        ListObjectsV2Iterable listObjectsV2Iterable = amazonS3Client.listObjectsV2Paginator(listObjectsV2Request);
        long totalObjects = 0;

        for (ListObjectsV2Response page : listObjectsV2Iterable) {
            long retrievedPageSize = page.contents().stream()
                    .peek(System.out::println)
                    .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
            totalObjects += retrievedPageSize;
            System.out.println("Page size: " + retrievedPageSize);
            List<S3Object> s3Objects =  page.contents().stream().toList();
            s3Objects.forEach(o-> {
                try {
                    this.download(o, bucketName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        System.out.println("Total objects in the bucket: " + totalObjects);

        amazonS3Client.close();

        return BucketDetail.builder().build();
    }

    private void download(final S3Object s3Object, final String bucketName) throws IOException {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Object.key())
                .build();

        ResponseBytes<GetObjectResponse> responseResponseBytes = amazonS3Client.getObjectAsBytes(objectRequest);
        byte[] data = responseResponseBytes.asByteArray();
        CSVFileUtil.writeCSVFile(s3Object.key(), data);
    }


}
