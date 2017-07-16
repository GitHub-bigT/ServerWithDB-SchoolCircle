package com.oracle.service;

import java.util.ArrayList;

import com.oracle.dao.SCOTypeDao;
import com.oracle.entity.SCOTypeEntity;

public class SCOTypeService {

	public int addSCOType(String sco_type) {
		SCOTypeDao scoTypeDao = new SCOTypeDao();
		return scoTypeDao.addSCOType(sco_type);
	}

	public ArrayList<SCOTypeEntity> selectSCOTypes() {
		SCOTypeDao scoTypeDao = new SCOTypeDao();
		return scoTypeDao.selectSCOTypes();
	}

}
