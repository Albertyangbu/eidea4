package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.bo.DirectoryBo;
import indi.liudalei.eidea.base.entity.po.DirectoryPo;
import indi.liudalei.eidea.base.service.DirectoryService;
import indi.liudalei.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {

    @DataAccess(entity = DirectoryPo.class)
    private CommonDao<DirectoryPo,Integer> directoryDao;
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public PaginationResult<DirectoryBo> findDirectory(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<DirectoryBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<DirectoryPo> searchResult = directoryDao.searchAndCount(search);
            List<DirectoryBo> list = modelMapper.map(searchResult.getResult(),new TypeToken<List<DirectoryBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<DirectoryPo> userPoList = directoryDao.search(search);
            List<DirectoryBo> userBoList = modelMapper.map(userPoList,new TypeToken<List<DirectoryBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(userBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public void deleteDirectoryById(Integer[] ids) {
        directoryDao.removeByIdsForLog(ids);
    }

    @Override
    public void save(DirectoryBo directoryBo) {
        DirectoryPo  directoryPo = modelMapper.map(directoryBo,DirectoryPo.class);
        directoryDao.saveForLog(directoryPo);
        directoryBo.setId(directoryPo.getId());

    }

    @Override
    public boolean findExistId(Integer id) {
        DirectoryPo pp=  directoryDao.find(id);
        if (pp != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DirectoryBo getDirectoryBo(Integer id) {
        DirectoryPo pdirectoryPo=  directoryDao.find(id);
        DirectoryBo directoryBo=modelMapper.map(pdirectoryPo,DirectoryBo.class);
        return directoryBo;
    }

    @Override
    public List<DirectoryBo> findAllDirectory(Search search) {
        List<DirectoryPo> userPoList = directoryDao.search(search);
        return modelMapper.map(userPoList,new TypeToken<List<DirectoryBo>>(){}.getType());
    }
}
