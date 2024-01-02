package com.leezekee.service.impl;

import com.leezekee.mapper.ChiefEditorMapper;
import com.leezekee.pojo.ChiefEditor;
import com.leezekee.pojo.Role;
import com.leezekee.service.ChiefEditorService;
import com.leezekee.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChiefEditorServiceImpl implements ChiefEditorService {
    @Autowired
    ChiefEditorMapper chiefEditorMapper;

    @Override
    public ChiefEditor findChiefEditorByUsername(String username) {
        return chiefEditorMapper.findChiefEditorByUsername(username);
    }

    @Override
    public int addChiefEditor(ChiefEditor chiefEditor) {
        if (chiefEditor.getUsername() == null) {
            chiefEditor.setUsername(CommonUtil.randomUsername(Role.CHIEF_EDITOR));
        }
        if (chiefEditor.getPassword() == null) {
            chiefEditor.setPassword(CommonUtil.randomPassword());
        }
        return chiefEditorMapper.addChiefEditor(chiefEditor);
    }

    @Override
    public ChiefEditor findChiefEditorById(Integer id) {
        return chiefEditorMapper.findChiefEditorById(id);
    }

    @Override
    public void updateChiefEditor(ChiefEditor chiefEditor) {
        chiefEditorMapper.updateChiefEditor(chiefEditor);
    }

    @Override
    public void deleteChiefEditor(ChiefEditor chiefEditor) {
        chiefEditorMapper.deleteChiefEditor(chiefEditor);
    }
}
