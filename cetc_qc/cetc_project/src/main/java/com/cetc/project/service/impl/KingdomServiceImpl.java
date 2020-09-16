package com.cetc.project.service.impl;
import com.cetc.common.core.entity.Result;
import com.cetc.common.core.entity.StatusCode;
import com.cetc.model.project.Code;
import com.cetc.model.project.Kingdom;
import com.cetc.model.project.Project;
import com.cetc.project.mapper.CodeDao;
import com.cetc.project.mapper.KingdomDao;
import com.cetc.project.mapper.ProjectDao;
import com.cetc.project.service.KingdomService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class KingdomServiceImpl implements KingdomService {
    @Autowired
    private KingdomDao kingdomDao;

    @Autowired
    private CodeDao codeDao;

    @Autowired
    private ProjectDao projectDao;

    /**
     * 保存
     * @param kingdom
     * @return
     */
    @Override
    @Transactional
    public Result save(Kingdom kingdom) {
        String principal = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Date current = new Date();
        kingdom.setUpdateBy(principal);
        kingdom.setUpdateDate(current);
        if(kingdom.getId()==null){
            kingdom.setCreateBy(principal);
            kingdom.setCreateDate(current);
            kingdomDao.addKingdom(kingdom);
        }else {
            kingdomDao.update(kingdom);
            Long kingdomId = kingdom.getId();
            List<Code> codeList = codeDao.findByKingdom(kingdomId);
            for (Code code: codeList) {
                code.setKingdom(kingdom.getName());
                codeDao.updateCode(code);
            }
            Project project = new Project();
            project.setKingdomId(kingdom.getId());
            List<Project> projects = projectDao.findProjectByField(project);
            for (Project project2: projects) {
                project2.setKingdom(kingdom.getName());
                projectDao.update(project2);
            }

        }
       return new Result(true, StatusCode.OK,"");
    }
    /**
     * 查询所有
     * @return
     */
    @Override
    public Result findAll() {
        List<Kingdom> kingdomList = kingdomDao.findAll();
        return new Result(true,StatusCode.OK,"",kingdomList);
    }
    /**
     * 分页查询
     * @param map
     * @return
     */
    @Override
    public Result page(Map<String, Integer> map) {
        PageHelper.startPage(map.get("pageNum"),map.get("pageSize"));
        List<Kingdom> list = kingdomDao.findAll();
        PageInfo<Kingdom> pageInfo = new PageInfo<>(list);
        return new Result(true,StatusCode.OK,"",pageInfo);
    }
    /**
     * 根据名称查询
     * @param name
     * @return
     */
    @Override
    public Result findByName(String name) {
        return new Result(true,StatusCode.OK,"",kingdomDao.findByName(name));
    }
    @Override
    public Result delete(Long id) {
        //删除领域
        kingdomDao.deleteById(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }
}
