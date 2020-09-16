package com.cetc.activiti.feign.impl;

import com.cetc.activiti.feign.ProjectClient;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProjectClientImpl implements FallbackFactory<ProjectClient> {
    private static final Logger logger = LoggerFactory.getLogger(ProjectClientImpl.class);

    @Override
    public ProjectClient create(Throwable throwable) {
        return new ProjectClient() {
            @Override
            public Result updateCodeStatus(Code code) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.OK,"服务降级");
            }
            @Override
            public Result updateProjectStatus(Project project) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.OK,"服务降级");
            }

            @Override
            public Result deleteProjectById(Long id) {
                logger.info("fallback,原因：",throwable);
                return new Result(false, StatusCode.OK,"服务降级");
            }
        };
    }
}

