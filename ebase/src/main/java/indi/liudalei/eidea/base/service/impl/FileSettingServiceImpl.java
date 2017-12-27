/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.base.entity.bo.ModuleBo;
import indi.liudalei.eidea.base.service.ModuleService;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.base.entity.po.FileSettingPo;
import indi.liudalei.eidea.base.service.FileSettingService;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-02 13:07:50
 */
@Service("fileSettingService")
public class FileSettingServiceImpl  implements	FileSettingService {
	@DataAccess(entity =FileSettingPo.class)
	private CommonDao<FileSettingPo,Integer> fileSettingDao;
	@Autowired
	private ModuleService moduleService;
	public PaginationResult<FileSettingPo> getFileSettingListByPaging(Search search, QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FileSettingPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FileSettingPo> searchResult = fileSettingDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FileSettingPo> fileSettingPoList = fileSettingDao.search(search);
		paginationResult = PaginationResult.pagination(fileSettingPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public FileSettingPo getFileSetting(Integer id)
	{
		return fileSettingDao.find(id);
	}
    public void saveFileSetting(FileSettingPo fileSetting)
	{
		fileSettingDao.saveForLog(fileSetting);
	}
    public void deletes(Integer[] ids)
	{
		fileSettingDao.removeByIdsForLog(ids);
	}

	@Override
	public List<FileSettingPo> getFileSettingList(Search search) {
		return fileSettingDao.search(search);
	}

	public FileSettingPo getFileSettingsByRequestPath(String path)
	{
		ModuleBo moduleBo=moduleService.getModuleBoByPath(path);
		Search search=new Search();
		search.addFilterEqual("moduleId",moduleBo.getId());
		FileSettingPo fileSettingPo=fileSettingDao.searchUnique(search);
		return fileSettingPo;
	}
}
