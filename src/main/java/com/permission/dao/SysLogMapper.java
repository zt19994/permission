package com.permission.dao;

import com.permission.beans.PageQuery;
import com.permission.dto.SearchLogDto;
import com.permission.model.SysLog;
import com.permission.model.SysLogWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLogWithBLOBs record);

    int insertSelective(SysLogWithBLOBs record);

    SysLogWithBLOBs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLogWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysLogWithBLOBs record);

    int updateByPrimaryKey(SysLog record);

    int countBySearchDto(SearchLogDto dto);

    List<SysLog> getPageListBySearchDto(@Param("dto") SearchLogDto dto, @Param("page") PageQuery page);
}