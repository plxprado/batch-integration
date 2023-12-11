package br.com.plx.integration.batchintegration.controllers;

import br.com.plx.integration.batchintegration.model.ProcessorInfo;
import br.com.plx.integration.batchintegration.service.ProcessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/v1/processor")
public class ProcessorController {

    @Autowired
    private ProcessorService processorService;

    @PostMapping
    public ResponseEntity<ProcessorInfo> executeProcessor(@RequestHeader("folderPath") final String path) throws FileNotFoundException {
        return ResponseEntity.ok(processorService.execute(path));
    }

}
