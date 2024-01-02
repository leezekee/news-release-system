package com.leezekee.mapper;

import com.leezekee.pojo.ChiefEditor;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChiefEditorMapper {
    int addChiefEditor(ChiefEditor chiefEditor);

    ChiefEditor findChiefEditorByUsername(String username);

    ChiefEditor findChiefEditorById(Integer id);

    void updateChiefEditor(ChiefEditor chiefEditor);

    void deleteChiefEditor(ChiefEditor chiefEditor);
}
