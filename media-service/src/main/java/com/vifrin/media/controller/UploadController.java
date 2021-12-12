package com.vifrin.media.controller;

import com.vifrin.common.dto.MediaDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.media.service.CdnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping(path = "/media")
public class UploadController {

    @Autowired
    private CdnService cdnSv;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestBody MultipartFile file, @AuthenticationPrincipal Principal principal) {
        MediaDto mediaDto = cdnSv.uploadToCdn(file, principal.getName());
        return mediaDto != null ?
                ResponseEntity.ok(new ResponseTemplate<MediaDto>(ResponseType.SUCCESS, mediaDto)) :
                ResponseEntity.badRequest().body(new ResponseTemplate<>(ResponseType.CANNOT_UPLOAD_FILE, null));
    }
}
