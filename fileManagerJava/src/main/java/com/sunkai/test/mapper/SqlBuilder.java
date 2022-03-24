package com.sunkai.test.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public class SqlBuilder {
    public String fileListSql(@Param("startNo") Integer startNo, @Param("startCount") Integer startCount, Map map){
        String keyword = map.get("keyWord").toString();
        List<Integer> li = (List<Integer>) map.get("tags");
        StringBuffer sql = new StringBuffer();
        sql.append("select *,(select count(fileid) from ffile where fileid=t.id) cnt from filelist AS t");
        if(!keyword.isEmpty()||li.size()>0){
            sql.append(" where 1=1 and (");
            if(!keyword.isEmpty()){
                sql.append("t.title like "+"'%"+keyword+"%' ");
                sql.append("or t.descri like "+"'%"+keyword+"%' ");
                sql.append("or t.tagsName like "+"'%"+keyword+"%' ");
            }
            if(li.size()>0){
                if(!keyword.isEmpty()){
                    sql.append("or ");
                }
               for (int i=0;i<li.size();i++){
                   String tem = Integer.toString(li.get(i));
                   if(i==0){
                       sql.append("CONCAT(',',t.tags,',') LIKE '%,"+tem+",%' ");
                   }else{
                       sql.append("or CONCAT(',',t.tags,',') LIKE '%,"+tem+",%' ");
                   }
               }
            }
            sql.append(")");
        }
        sql.append(" order by date desc LIMIT "+startNo+","+startCount);
        return sql.toString();
    }
    public String fileListCountSql(Map map){
        String keyword = map.get("keyWord").toString();
        List<Integer> li = (List<Integer>) map.get("tags");
        StringBuffer sql = new StringBuffer();
        sql.append("select count(t.id) from filelist AS t");
        if(!keyword.isEmpty()||li.size()>0){
            sql.append(" where 1=1 and (");
            if(!keyword.isEmpty()){
                sql.append("t.title like "+"'%"+keyword+"%' ");
                sql.append("or t.descri like "+"'%"+keyword+"%' ");
                sql.append("or t.tagsName like "+"'%"+keyword+"%' ");
            }
            if(li.size()>0){
                if(!keyword.isEmpty()){
                    sql.append("or ");
                }
                for (int i=0;i<li.size();i++){
                    String tem = Integer.toString(li.get(i));
                    if(i==0){
                        sql.append("CONCAT(',',t.tags,',') LIKE '%,"+tem+",%' ");
                    }else{
                        sql.append("or CONCAT(',',t.tags,',') LIKE '%,"+tem+",%' ");
                    }
                }
            }
            sql.append(")");
        }
        return sql.toString();
    }
}
