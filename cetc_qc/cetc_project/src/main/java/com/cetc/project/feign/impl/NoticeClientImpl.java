package com.cetc.project.feign.impl;

import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.notice.Notice;
import com.cetc.project.feign.NoticeClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NoticeClientImpl implements FallbackFactory<NoticeClient> {

    private static final Logger logger = LoggerFactory.getLogger(NoticeClientImpl.class);

    @Override
    public NoticeClient create(Throwable throwable) {
        return new NoticeClient() {
            @Override
            public Result saveNotice(Notice notice) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.ERROR,"服务降级");
            }
        };
    }
}
