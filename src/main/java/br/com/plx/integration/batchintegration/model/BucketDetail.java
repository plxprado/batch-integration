package br.com.plx.integration.batchintegration.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BucketDetail {

    @JsonProperty("name")
    private String name;

}
