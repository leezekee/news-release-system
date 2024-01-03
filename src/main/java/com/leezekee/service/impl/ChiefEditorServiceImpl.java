package com.leezekee.service.impl;

import com.leezekee.mapper.ChiefEditorMapper;
import com.leezekee.pojo.ChiefEditor;
import com.leezekee.pojo.Role;
import com.leezekee.service.ChiefEditorService;
import com.leezekee.utils.CommonUtil;
import com.leezekee.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ChiefEditorServiceImpl implements ChiefEditorService {
    @Autowired
    ChiefEditorMapper chiefEditorMapper;

    @Override
    public ChiefEditor findChiefEditorByUsername(String username) {
        ChiefEditor chiefEditor = chiefEditorMapper.findChiefEditorByUsername(username);
        if (chiefEditor == null) {
            return null;
        }
        if (chiefEditor.getTelephoneNumber() != null) {
            chiefEditor.setTelephoneNumber(CommonUtil.hideTelephoneNumber(chiefEditor.getTelephoneNumber()));
        }
        if (chiefEditor.getIdCardNumber() != null) {
            chiefEditor.setIdCardNumber(CommonUtil.hideIdCardNumber(chiefEditor.getIdCardNumber()));
        }
        return chiefEditor;
    }

    @Override
    public void addChiefEditor(ChiefEditor chiefEditor) {
        if (chiefEditor.getUsername() == null) {
            chiefEditor.setUsername(CommonUtil.randomUsername(Role.CHIEF_EDITOR));
        }
        if (chiefEditor.getPassword() == null) {
            chiefEditor.setPassword(CommonUtil.randomPassword());
        }
        chiefEditor.setUuid(String.valueOf(UUID.randomUUID()));
        chiefEditorMapper.addChiefEditor(chiefEditor);
        if (chiefEditor.getTelephoneNumber() != null) {
            chiefEditor.setTelephoneNumber(CommonUtil.hideTelephoneNumber(chiefEditor.getTelephoneNumber()));
        }
        if (chiefEditor.getIdCardNumber() != null) {
            chiefEditor.setIdCardNumber(CommonUtil.hideIdCardNumber(chiefEditor.getIdCardNumber()));
        }
        chiefEditor.setPassword(null);
    }

    @Override
    public ChiefEditor findChiefEditorById(Integer id) {
        ChiefEditor chiefEditor= chiefEditorMapper.findChiefEditorById(id);
        if (chiefEditor == null) {
            return null;
        }
        if (chiefEditor.getTelephoneNumber() != null) {
            chiefEditor.setTelephoneNumber(CommonUtil.hideTelephoneNumber(chiefEditor.getTelephoneNumber()));
        }
        if (chiefEditor.getIdCardNumber() != null) {
            chiefEditor.setIdCardNumber(CommonUtil.hideIdCardNumber(chiefEditor.getIdCardNumber()));
        }
        chiefEditor.setPassword(null);
        return chiefEditor;
    }

    @Override
    public void updateChiefEditor(ChiefEditor chiefEditor) {
        chiefEditorMapper.updateChiefEditor(chiefEditor);
    }

    @Override
    public void deleteChiefEditor(Integer id) {
        chiefEditorMapper.deleteChiefEditor(id);
    }

    @Override
    public void updatePassword(String newPasswordMd5, Integer id) {
        chiefEditorMapper.updatePassword(newPasswordMd5, id);
    }

    @Override
    public List<ChiefEditor> findChiefEditorList() {
        List<ChiefEditor> chiefEditorList = chiefEditorMapper.findChiefEditorList();
        for (ChiefEditor chiefEditor : chiefEditorList) {
            if (chiefEditor.getTelephoneNumber() != null) {
                chiefEditor.setTelephoneNumber(CommonUtil.hideTelephoneNumber(chiefEditor.getTelephoneNumber()));
            }
            if (chiefEditor.getIdCardNumber() != null) {
                chiefEditor.setIdCardNumber(CommonUtil.hideIdCardNumber(chiefEditor.getIdCardNumber()));
            }
            chiefEditor.setPassword(null);
        }
        return chiefEditorList;
    }

    @Override
    public ChiefEditor findChiefEditorByIdWithoutHidingInformation(Integer id) {
        return chiefEditorMapper.findChiefEditorById(id);
    }

    @Override
    public ChiefEditor findChiefEditorByUsernameWithoutHidingInformation(String username) {
        return chiefEditorMapper.findChiefEditorByUsername(username);
    }
}
