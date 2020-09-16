package com.xxl.job.admin.dao;

import com.xxl.job.admin.core.model.XxlJobInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;



@Mapper
public interface XxlJobInfoDao {

	 List<XxlJobInfo> pageList(@Param("offset") int offset,
									 @Param("pagesize") int pagesize,
									 @Param("jobGroup") int jobGroup,
									 @Param("jobDesc") String jobDesc,
									 @Param("executorHandler") String executorHandler);
	 int pageListCount(@Param("offset") int offset,
							 @Param("pagesize") int pagesize,
							 @Param("jobGroup") int jobGroup,
							 @Param("jobDesc") String jobDesc,
							 @Param("executorHandler") String executorHandler);
	
	 int save(XxlJobInfo info);

	 XxlJobInfo loadById(@Param("id") int id);
	
	 int update(XxlJobInfo item);
	
	 int delete(@Param("id") int id);

	 List<XxlJobInfo> getJobsByGroup(@Param("jobGroup") int jobGroup);

	 int findAllCount();

}
