package com.leezekee.service;

import com.leezekee.pojo.ChiefEditor;

import java.util.List;

public interface ChiefEditorService {
    ChiefEditor findChiefEditorByUsername(String username);

    void addChiefEditor(ChiefEditor chiefEditor);

    ChiefEditor findChiefEditorById(Integer id);

    void updateChiefEditor(ChiefEditor chiefEditor);

    void deleteChiefEditor(Integer id);

    void updatePassword(String newPasswordMd5, Integer id);

    List<ChiefEditor> findChiefEditorList();

    ChiefEditor findChiefEditorByIdWithoutHidingInformation(Integer id);

    ChiefEditor findChiefEditorByUsernameWithoutHidingInformation(String username);
}
