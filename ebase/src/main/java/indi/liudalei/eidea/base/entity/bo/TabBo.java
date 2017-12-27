package indi.liudalei.eidea.base.entity.bo;

import indi.liudalei.eidea.base.entity.po.FieldPo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/3 16:22.
 * Tab页 Bo类
 */
@Getter
@Setter
public class TabBo {
    /**
     * tab id
     */
    private Integer id;
    /**
     * tab 名字
     */
    private String tabName;
    /**
     * tabs帮助信息
     */
    private String help;
    /**
     * 排序号
     */
    private Integer seqNo;
    /**
     * 主键id
     */
    private Integer pkFieldId;
    /**
     * 字段列表
     */
    private List<FieldPo> fieldList;
    /**
     * select管理自定义函数
     */
    private List<String> functions;
}
