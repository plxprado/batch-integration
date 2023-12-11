package br.com.plx.integration.batchintegration.model;

import lombok.Builder;
import lombok.Getter;

import java.io.File;

@Builder
@Getter
public class FileMetaDataInfo {

    private String fileName;
    private File file;
    private Long length;


    public static FileMetaDataInfo convert(final File file) {
        return FileMetaDataInfo.builder()
                .fileName(file.getName())
                .file(file)
                .length(file.length())
                .build();
    }
}
