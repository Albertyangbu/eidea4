package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.base.entity.bo.FileRelationBo;
import indi.liudalei.eidea.base.entity.po.FileRelationPo;
import indi.liudalei.eidea.base.service.FileRelationService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bobo on 2017/5/17.
 */
@Service("fileRelationService")
public class FileRelationServiceImpl implements FileRelationService{
    @DataAccess(entity =FileRelationPo.class)
    private CommonDao<FileRelationPo,Integer> commonFileRelationDao;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<FileRelationBo> getFileRelationList(Search search) {
        List<FileRelationPo> fileRelationPoList = commonFileRelationDao.search(search);
        return modelMapper.map(fileRelationPoList,new TypeToken<List<FileRelationBo>>(){}.getType());
    }
}
