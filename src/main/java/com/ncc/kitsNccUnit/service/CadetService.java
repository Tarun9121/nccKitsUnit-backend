package com.ncc.kitsNccUnit.service;

import com.ncc.kitsNccUnit.dto.CadetDTO;
import com.ncc.kitsNccUnit.dto.LoginDTO;

import java.util.List;

public interface CadetService {
    CadetDTO registerCadet(CadetDTO cadetDTO);
    boolean loginCadet(LoginDTO loginDTO);

    CadetDTO getCadetById(int id);
    List<CadetDTO> getAllCadets();

    CadetDTO getCadetByRegimentalNo(String regimentalNo);
    public List<CadetDTO> searchByRegimentalNo(String regimentalNo, int limit);

}
