package com.bz.hospitalsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bz.hospitalsystem.connection.ConnectDB;
import com.bz.hospitalsystem.exceptions.PatientAlreadyExistException;
import com.bz.hospitalsystem.exceptions.PatientNotFoundException;
import com.bz.hospitalsystem.model.HospitalPojo;

public class AddPatient {
	
	public static boolean patientExistOrNot(String firstName) throws SQLException {
		Connection connection = ConnectDB.getInstance().getConnection();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("select * from patientDetails where firstName ='"+firstName+"' ;");
		
		if(resultSet.getRow() == 0) {
			return false;
		}
		return true;
	}
	
	public void addPatint(HospitalPojo pojo) {
		Connection connection = ConnectDB.getInstance().getConnection();
		
		try {
			if(patientExistOrNot(pojo.getFirstName())) {
				throw new PatientAlreadyExistException(" Entry Already Exist ");
			}
			else {
			PreparedStatement statement = connection.prepareStatement("insert into patientDetails(firstName,lastName,age)"
					+ " values(? , ? , ? );");
			statement.setString(1, pojo.getFirstName());
			statement.setString(2, pojo.getLastName());
			statement.setInt(3, pojo.getAge());
			
			int confirm = statement.executeUpdate();
			System.out.println(confirm==1? "Patient Added ":" Error while Adding Patient");
			statement.close();
			connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	}
	
