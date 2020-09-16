package com.cetc.activiti.feign;

import com.cetc.activiti.feign.impl.ProjectClientImpl;
import com.cetc.common.core.entity.Result;
import com.cetc.model.project.Code;
import com.cetc.model.project.Project;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value="cetc-project",fallbackFactory = ProjectClientImpl.class )
public interface ProjectClient {
    @PostMapping("/code/updateCodeStatus")
    Result updateCodeStatus(@RequestBody Code code);

    @PostMapping("/project/updateProjectStatus")
    Result updateProjectStatus(@RequestBody Project project);

    @GetMapping("/project/delProjectAct/{id}")
    Result deleteProjectById(@PathVariable Long id);
}
