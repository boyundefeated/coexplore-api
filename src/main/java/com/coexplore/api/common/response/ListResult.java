package com.coexplore.api.common.response;

import java.util.List;

/**
 * Note: StandardResponse's value can be an instance of this class
 * @param <T>
 */

public class ListResult<T> {

    private Long totalCount;

    private List<T> list;
    
    
    

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public ListResult() {
        super();
    }

    public ListResult(Long totalCount, List<T> list) {
        super();
        this.totalCount = totalCount;
        this.list = list;
    }

}
