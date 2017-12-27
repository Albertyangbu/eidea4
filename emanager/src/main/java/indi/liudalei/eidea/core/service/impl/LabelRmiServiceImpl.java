package indi.liudalei.eidea.core.service.impl;

import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.LabelRmiService;
import indi.liudalei.eidea.core.service.LabelService;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2017/1/17 8:49.
 */
@Service
public class LabelRmiServiceImpl implements LabelRmiService {
    @Autowired
    private LabelService labelService;
    @Override
    public List<String> getAllLabelList() {
        List<String> list=labelService.getLabelList(new Search(),new QueryParams()).getData().stream().map(e->e.getMsgtext()).collect(Collectors.toList());
        return list;
    }
}
