package com.example.OrTools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrToolsController {

    @Autowired
    private OrToolsService orToolsService;

    @PostMapping("/orTools")
    public ResponseEntity<?> VehicleRoutingController(@RequestBody OrToolsDto orToolsDto) throws Exception {
        orToolsResponse orToolsResponse = orToolsService.handleOrTools(orToolsDto);
        return new ResponseEntity<orToolsResponse>(orToolsResponse, HttpStatus.OK);

    }
}
