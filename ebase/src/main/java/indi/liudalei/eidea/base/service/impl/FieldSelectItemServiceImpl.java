/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.FieldSelectItemPo;
import indi.liudalei.eidea.base.service.FieldSelectItemService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-03 17:51:03
 */
@Service("fieldSelectItemService")
public class FieldSelectItemServiceImpl  implements FieldSelectItemService {
	@DataAccess(entity =FieldSelectItemPo.class)
	private CommonDao<FieldSelectItemPo,Integer> fieldSelectItemDao;
	public PaginationResult<FieldSelectItemPo> getFieldSelectItemListByPaging(Search search, QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FieldSelectItemPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FieldSelectItemPo> searchResult = fieldSelectItemDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FieldSelectItemPo> fieldSelectItemPoList = fieldSelectItemDao.search(search);
		paginationResult = PaginationResult.pagination(fieldSelectItemPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public FieldSelectItemPo getFieldSelectItem(Integer id)
	{
		return fieldSelectItemDao.find(id);
	}
    public void saveFieldSelectItem(FieldSelectItemPo fieldSelectItem)
	{
		fieldSelectItemDao.save(fieldSelectItem);
	}
    public void deletes(Integer[] ids)
	{
		fieldSelectItemDao.removeByIds(ids);
	}
}
