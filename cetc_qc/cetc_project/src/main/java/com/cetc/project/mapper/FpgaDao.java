package com.cetc.project.mapper;

import com.cetc.model.project.FpgaStandard;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface FpgaDao {
    void addFpgaStandard(FpgaStandard fpgaStandard);

    void updateFpgaStandard(FpgaStandard fpgaStandard);

    List<FpgaStandard> findFpgaStandard(@Param("kingdomId") Integer kingdomId, @Param("codeId")Integer codeId, @Param("projectId")Integer projectId);

    void delFpgaStandard(Long id);

    List<FpgaStandard> findFpgaStandardByPid(Long parentId);

    List<FpgaStandard> findByMonth(@Param("begin") Date begin, @Param("end") Date end);

    List<FpgaStandard> findByKingdom(@Param("kingdomId")Long kid);
}
