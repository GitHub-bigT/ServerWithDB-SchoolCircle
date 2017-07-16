package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.oracle.entity.NewsEntity;
import com.oracle.entity.SCOTypeEntity;

public class SCOTypeDao extends BaseDao {

	public int addSCOType(String sco_type) {
		openCon();
		int row = 0;
		try {
			ps = con.prepareStatement("insert into sco_types values(sco_id.nextval,?)");
			ps.setString(1, sco_type);
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

	public ArrayList<SCOTypeEntity> selectSCOTypes() {
		openCon();
		ArrayList<SCOTypeEntity> list = null;
		try {
			ps = con.prepareStatement("select type_id,type_name from sco_types");
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				list = new ArrayList<>();
				while(rs.next()){
					int type_id = rs.getInt(1);
					String type_name = rs.getString(2);
					SCOTypeEntity scoTypeEntity = new SCOTypeEntity();
					scoTypeEntity.setType_id(type_id);
					scoTypeEntity.setType_name(type_name);
					list.add(scoTypeEntity);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
