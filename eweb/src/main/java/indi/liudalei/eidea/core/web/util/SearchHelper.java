package indi.liudalei.eidea.core.web.util;

import indi.liudalei.eidea.base.service.SpringContextHolder;
import indi.liudalei.eidea.common.web.vo.SearchColumnVo;
import indi.liudalei.eidea.core.entity.dto.SearchColumnDto;
import indi.liudalei.eidea.core.service.SearchService;
import indi.liudalei.eidea.core.web.def.WebConst;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/20 17:46.
 */
public class SearchHelper {
    private static ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
    private static SearchService searchService = applicationContext.getBean(SearchService.class);

    public static Search getSearchParam(String uri, HttpSession session) {
        List<SearchColumnVo> searchColumnVoList = (List<SearchColumnVo>) session.getAttribute(uri + WebConst.SESSION_SEARCH_PARAM);
        if (searchColumnVoList == null) {
            return new Search();
        }
        ModelMapper modelMapper = new ModelMapper();
        List<SearchColumnDto> searchColumnDtoList = modelMapper.map(searchColumnVoList, new TypeToken<List<SearchColumnDto>>() {
        }.getType());
        return searchService.getSearchParam(searchColumnDtoList);
    }
}
