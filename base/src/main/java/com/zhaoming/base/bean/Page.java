package com.zhaoming.base.bean;

import com.zhaoming.base.annotation.Dict;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collections;
import java.util.List;

/**
 * 分页
 *
 * @author hhx
 */
@Data
@Accessors(chain = true)
public class Page<T> {

    public static final Page EMPTY_PAGE = new Page();

    @Dict(desc = "列表数据")
    private List<T> list;
    @Dict(desc = "总记录数", value = "100")
    private long total;
    @Dict(desc = "每页的数量", value = "10")
    private long pageSize;
    @Dict(desc = "当前页", value = "1")
    private long pageNumber;

    public Page() {
        this.list = Collections.emptyList();
        this.total = 0L;
        this.pageSize = 10L;
        this.pageNumber = 1L;
    }

    public Page(long pageNumber, long pageSize, long total, List<T> list) {
        this.list = list;
        this.total = 0L;
        this.pageSize = 10L;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.total = total;
    }
}
