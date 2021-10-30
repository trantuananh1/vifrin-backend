package com.vifrin.mediaservice.controller;

import com.vifrin.mediaservice.service.CdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/upload")
public class UploadController {

    @Autowired
    private CdnService cdnSv;

    @GetMapping("/test")
    public final String upload() {
        String pathFile = "C:\\Users\\hungq\\Desktop\\onepunchman.jpg";
        // VD: C:\Users\hungq\Desktop\onepunchman.jpg
        return cdnSv.uploadToCdn(pathFile);
    }
}
