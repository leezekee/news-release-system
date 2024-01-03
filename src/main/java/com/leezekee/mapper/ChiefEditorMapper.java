package com.leezekee.mapper;

import com.leezekee.pojo.ChiefEditor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChiefEditorMapper {
    int addChiefEditor(ChiefEditor chiefEditor);

    ChiefEditor findChiefEditorByUsername(String username);

    ChiefEditor findChiefEditorById(Integer id);

    void updateChiefEditor(ChiefEditor chiefEditor);

    void deleteChiefEditor(Integer id);

    void updatePassword(String password, Integer id);

    List<ChiefEditor> findChiefEditorList();
}
