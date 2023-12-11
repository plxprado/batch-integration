package br.com.plx.integration.batchintegration.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FileRegisterInput {

    private String identifier;

    private String name;

    private String version;

}
