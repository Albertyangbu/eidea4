package indi.liudalei.eidea.core.dto;

import lombok.Getter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/6 19:38.
 */
@Getter
public class PaginationResult<T> implements Serializable {
    /**
     * 当前页码
     */
    private int pageNumber = 1;
    /**
     * 总页数
     */
    private int totalPages = 1;
    /**
     * 总记录数
     */
    private int totalRecords = 0;
    /**
     * 必须是list
     */
    private List<T> data;

    private PaginationResult() {

    }

    public static <T> PaginationResult<T> pagination(List<T> data) {
        return pagination(data, 1);
    }

    public static <T> PaginationResult<T> pagination(List<T> data, int totalRecord) {
        return pagination(data, totalRecord, 1);
    }

    public static <T> PaginationResult<T> pagination(List<T> data, int totalRecord, int pageNumber) {
        return pagination(data, totalRecord, pageNumber, 10);
    }

    public static <T> PaginationResult<T> pagination(List<T> data, int totalRecord, int pageNumber, int pageSize) {
        PaginationResult<T> paginationResult = new PaginationResult<>();
        if (pageNumber < 1) {
            pageNumber = 1;
        }
        paginationResult.pageNumber = pageNumber;
        paginationResult.totalRecords = totalRecord;
        if (totalRecord == 0) {
            paginationResult.totalPages = 1;
        } else {
            if (totalRecord % pageSize == 0) {
                paginationResult.totalPages = totalRecord / pageSize;
            } else {
                paginationResult.totalPages = totalRecord / pageSize + 1;
            }
        }
        paginationResult.data = data;
        return paginationResult;
    }
}
