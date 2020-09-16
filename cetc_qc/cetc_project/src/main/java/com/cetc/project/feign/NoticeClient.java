package com.cetc.project.feign;

import com.cetc.common.core.entity.Result;
import com.cetc.model.notice.Notice;
import com.cetc.project.feign.impl.NoticeClientImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "cetc-notice",fallbackFactory = NoticeClientImpl.class)
public interface NoticeClient {

    @PostMapping("/notice/saveNotice")
     Result saveNotice(@RequestBody Notice notice);
}
