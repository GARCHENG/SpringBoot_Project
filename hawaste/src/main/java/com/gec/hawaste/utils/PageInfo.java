package com.gec.hawaste.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class PageInfo<T> extends Page<T> {

    //导航页码数
    private int navigatePages;
    //所有导航页号
    private int[] navigatepageNums;
    //导航条上的第一页
    private int navigateFirstPage;
    //导航条上的最后一页
    private int navigateLastPage;
    //前一页
    private int prePage;
    //下一页
    private int nextPage;

    //是否为第一页
    private boolean isFirstPage = false;
    //是否为最后一页
    private boolean isLastPage = false;
    //是否有前一页
    private boolean hasPreviousPage = false;
    //是否有下一页
    private boolean hasNextPage = false;


    public PageInfo() {
        this(1,5);
    }

    public PageInfo(long current, long size) {
        super(current, size);
    }

    /*设置页码导航栏 数据*/
    public void setNavigatePage(){
        setNavigatePage(8);//默认显示8个页码
    }

    /*根据指定显示页码个数，动态计算出导航栏 页码 */
    public void setNavigatePage(int navigatePages){
        if (records instanceof Collection) {
            this.navigatePages = navigatePages;
            this.calcNavigatepageNums();
            this.calcPage();
            this.judgePageBoudary();
        }
    }


    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (this.getPages() <= navigatePages) {
            navigatepageNums = new int[(int) this.getPages()];
            for (int i = 0; i < this.getPages(); i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new int[navigatePages];
            int startNum = (int) (current - navigatePages / 2);
            int endNum = (int) (current + navigatePages / 2);

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > this.getPages()) {
                endNum = (int) this.getPages();
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
            if (current > 1) {
                prePage = (int) (current - 1);
            }
            if (current < this.getPages()) {
                nextPage = (int) (current + 1);
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = current == 1;
        isLastPage = current == this.getPages() || this.getPages() == 0;
        hasPreviousPage = current > 1;
        hasNextPage = current < this.getPages();
    }

}
