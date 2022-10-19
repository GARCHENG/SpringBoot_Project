package com.gec;

import static org.junit.Assert.assertTrue;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gec.hawaste.entity.AppVersion;
import com.gec.hawaste.entity.Qualification;
import com.gec.hawaste.entity.SysRole;
import com.gec.hawaste.service.IAppVersionService;
import com.gec.hawaste.service.IExamineService;
import com.gec.hawaste.service.IQualificationService;
import com.gec.hawaste.service.ISysRoleService;
import com.gec.hawaste.vo.ExamineVo;
import com.gec.hawaste.vo.RoleVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit test for simple App.
 */
@SpringBootTest(classes = HawasteApp.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AppTest 
{


    @Autowired
    private IQualificationService iQualificationService;

    @Autowired
    private IAppVersionService iAppVersionService;

    @Autowired
    private IExamineService iExamineService;

    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void fun1(){
        List<AppVersion> list = iAppVersionService.list();
        if (list != null) {
            for (AppVersion appVersion : list) {
                System.out.println(appVersion);
            }
        }

    }

    @Test
    public void fun2(){
        Page<AppVersion> page = new Page<>(2,2);
        page = iAppVersionService.page(page);
        List<AppVersion> records = page.getRecords();
        if (records != null) {
            for (AppVersion record : records) {
                System.out.println(record);
            }
        }
    }

    @Test
    public void  fun3(){
        Page<ExamineVo> page = new Page<>(1,10);
        Map<String, Object> map = new HashMap<>();
        map.put("type",1);
        map.put("name","人员");
        map.put("officeId",56);

        IPage<ExamineVo> examineByCondition = iExamineService.selectExamineByCondition(page, map);
        List<ExamineVo> records = examineByCondition.getRecords();
        if (records != null) {
            for (ExamineVo record : records) {
                System.out.println(record);
            }
        }
    }
    @Test
    public  void  fun4 (){
        Qualification qualification = iQualificationService.selectQualificationById(1);
        System.out.println(qualification);
    }

    @Test
    public  void  fun5(){
        RoleVo roleVo = iSysRoleService.selectRoleVoById(27);

    }


}
