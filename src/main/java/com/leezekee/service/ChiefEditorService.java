package com.leezekee.service;

import com.leezekee.pojo.ChiefEditor;

public interface ChiefEditorService {
    ChiefEditor findChiefEditorByUsername(String username);

    int addChiefEditor(ChiefEditor chiefEditor);

    ChiefEditor findChiefEditorById(Integer id);

    void updateChiefEditor(ChiefEditor chiefEditor);

    void deleteChiefEditor(ChiefEditor chiefEditor);
}
