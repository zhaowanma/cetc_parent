package com.cetc.project.service;

import com.cetc.common.core.entity.Result;
import org.springframework.web.multipart.MultipartFile;

public interface CompensateService {

    Result importTemplate(MultipartFile multipartFile);

}
