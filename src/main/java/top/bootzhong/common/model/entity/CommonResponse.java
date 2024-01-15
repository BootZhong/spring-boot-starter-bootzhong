package top.bootzhong.common.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 统一响应类
 * @param <T>
 */
@Getter
public class CommonResponse<T> {
    private final T data;

    private String msg;

    private Integer code;

    private boolean success;

    private PageInfo pageInfo;

    /**
     * 分页信息
     */
    @Setter
    @Getter
    static class PageInfo{
        private int page;

        private int pageSize;

        private long total;
        
        private int totalPages;

        public PageInfo(int page, int pageSize, long total){
            this.page = page;
            this.pageSize = pageSize;
            this.total = total;
            this.totalPages = pageSize / page + pageSize % page > 0 ? 1 : 0;
        }
    }

    CommonResponse(T data, Integer code, boolean success){
        this.data = data;
        this.code = code;
        this.success = success;
    }

    CommonResponse(T data, PageInfo pageInfo, Integer code, boolean success){
        this.data = data;
        this.code = code;
        this.pageInfo = pageInfo;
        this.success = success;
    }


    public static <R> CommonResponse<R> success(R data){
        return new CommonResponse<>(data, 200, true);
    }

    public static <T> CommonResponse<T> success(T data, int page, int pageSize, long total){
        return new CommonResponse<>(data, new PageInfo(page, pageSize, total), 200, true);
    }
}
