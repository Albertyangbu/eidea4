package indi.liudalei.eidea.base.dao.test;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.UserPo;
import indi.liudalei.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class UserDaoTest {
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo,Integer> userDao;
    @Test
    @Transactional
    public void testSearch()
    {
        Search search=new Search();
        List<UserPo> userPoList=userDao.search(search);
        for(UserPo userPo:userPoList)
        {
            log.debug(userPo.toString());
        }

    }
}
