package com.zzk.fastanswer.model.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * 对MybatisPlus Page对象进行包装
 *
 * @author zzk
 * @create 2020-11-06 22:15
 */
@Data
@NoArgsConstructor
public class PageInfo<T> {
    /**
     * 结果集
     */
    private List<T> list;

    /**
     * 总记录数
     */
    private int total;

    /**
     * 当前页
     */
    private int pageNum;

    /**
     * 每页的数量
     */
    private int pageSize;

    /**
     * 当前页的数量
     */
    private int size;

    /**
     * 总页数
     */
    private int pages;

    /**
     * 前一页
     */
    private int prePage;

    /**
     * 下一页
     */
    private int nextPage;

    /**
     * 是否为第一页
     */
    private boolean isFirstPage = false;

    /**
     * 是否为最后一页
     */
    private boolean isLastPage = false;

    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage = false;

    /**
     * 是否有下一页
     */
    private boolean hasNextPage = false;

    /**
     * 导航页码数
     */
    private int navigatePages;

    /**
     * 所有导航页号
     */
    private int[] navigatepageNums;

    /**
     * 导航条上的第一页
     */
    private int navigateFirstPage;

    /**
     * 导航条上的最后一页
     */
    private int navigateLastPage;

    /**
     * 包装Page对象
     *
     * @param page page结果
     */
    public PageInfo(IPage<T> page) {
        this(page, 5);
    }

    /**
     * 包装Page对象
     *
     * @param page          page结果
     * @param navigatePages 页码数量
     */
    public PageInfo(IPage<T> page, int navigatePages) {
        this.list = page.getRecords();

        total = (int) page.getTotal();
        pageNum = (int) page.getCurrent();
        pageSize = (int) page.getSize();
        size = (int) page.getSize();
        pages = (int) page.getPages();

        this.navigatePages = navigatePages;

        //计算导航页
        calcNavigatepageNums();
        //计算前后页，第一页，最后一页
        calcPage();
        //判断页面边界
        judgePageBoudary();
    }

    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new int[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }

    /**
     * 替换当前PageInfo的list为其他类型的list
     *
     * @param list
     * @param <S>
     * @return
     */
    public <S> PageInfo<S> covertTo(List<S> list) {
        PageInfo<S> sPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(this, sPageInfo);
        sPageInfo.list = list;
        return sPageInfo;
    }
}
