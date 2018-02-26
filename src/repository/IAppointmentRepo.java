package repository;

import java.util.List;

import model.Appointment;

public interface IAppointmentRepo {

	public void insertAppointment(Appointment appointment);
	public List<Appointment> getAppointments(String searchText); 
}
