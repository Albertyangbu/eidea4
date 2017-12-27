package indi.liudalei.eidea.base.service.test;

import indi.liudalei.eidea.base.entity.bo.OrgBo;
import indi.liudalei.eidea.base.entity.po.OrgPo;
import indi.liudalei.eidea.base.service.OrgService;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/16 8:15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrgServiceTest {
    @Autowired
    private OrgService orgService;
    @Test
    public void testGetOrg()
    {
        orgService.getOrgBo(1);
    }
    @Test
    public  void TestGetOrgInit()
    {
        OrgPo orgPo=orgService.getOrg(1);
        System.out.println(orgPo.getSysClient().getSysOrgs().size());
    }
    @Test
    public void testGetOrgs()
    {
       List<OrgBo> orgBoList= orgService.findOrgList(new Search(),new QueryParams()).getData();
        System.out.println(orgBoList.size());
    }

}
