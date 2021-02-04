package demo.common.util;

import com.github.smallcham.plugin.page.support.Page;
import com.github.smallcham.plugin.page.support.RowBounds;

import java.util.List;

/**
 * 类名称：PageUtil
 * 类描述：分页辅助类
 * 创建人：limiao
 * 创建时间：2017/11/20 11:46
 */
public class PageUtil<Entity> {

    public static <Entity> PageUtil getInstance(){
        return new PageUtil<Entity>();
    }

    public Page<Entity> pageByList(long nextPage,long pageSize,long rowCount,List<Entity> list){
        RowBounds rowBounds = new RowBounds(nextPage);
        rowBounds.setPageSize(pageSize);
        rowBounds.setRowCount(rowCount);
        return this.myAsPage(rowBounds, list);
    }

    public Page<Entity> myAsPage(RowBounds rowBounds,List<Entity> list){
        Page<Entity> page = Page.asPage(rowBounds, list);
        //因为插件有bug，故修复nextPage和pageNum不准确的问题
        if(page.isHome()){
            page.setNextPage(page.getHomePage()+1);
            page.setPageNum(page.getHomePage());
        }else if(page.isLast()){
            page.setNextPage(page.getMaxPage());
            page.setPageNum(page.getMaxPage());
        }else{
            page.setPageNum(page.getNextPage());
            page.setNextPage(page.getNextPage()+1);
        }
        return page;
    }

}
