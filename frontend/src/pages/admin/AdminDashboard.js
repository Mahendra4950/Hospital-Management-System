import React, { useState, useEffect } from 'react';
import authService from '../../services/authService';
import api from '../../services/api';

function AdminDashboard() {
  const [doctors, setDoctors] = useState([]);
  const [patients, setPatients] = useState([]);
  const [appointments, setAppointments] = useState([]);
  const user = authService.getCurrentUser();

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const doctorsRes = await api.get(
        '/admin/doctors/all');
      const patientsRes = await api.get(
        '/admin/patients/all');
      const appointmentsRes = await api.get(
        '/appointments/all');

      setDoctors(doctorsRes.data);
      setPatients(patientsRes.data);
      setAppointments(appointmentsRes.data);
    } catch (err) {
      console.error('Error fetching data:', err);
    }
  };

  const handleLogout = () => {
    authService.logout();
  };

  return (
    <div>
      {/* Navbar */}
      <nav className="navbar navbar-dark 
                      bg-primary px-4">
        <span className="navbar-brand fw-bold">
          🏥 HMS — Admin Panel
        </span>
        <div className="d-flex align-items-center">
          <span className="text-white me-3">
            👤 {user?.firstName} {user?.lastName}
          </span>
          <button
            className="btn btn-outline-light"
            onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>

      <div className="container mt-4">

        {/* Welcome */}
        <h4 className="mb-4">
          Welcome, {user?.firstName}! 👋
        </h4>

        {/* Stats Cards */}
        <div className="row mb-4">

          {/* Doctors Card */}
          <div className="col-md-4 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-4"
                   style={{ 
                     backgroundColor: '#e3f2fd' }}>
                <h1 className="text-primary fw-bold">
                  {doctors.length}
                </h1>
                <h5 className="text-muted">
                  👨‍⚕️ Total Doctors
                </h5>
              </div>
            </div>
          </div>

          {/* Patients Card */}
          <div className="col-md-4 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-4"
                   style={{ 
                     backgroundColor: '#e8f5e9' }}>
                <h1 className="text-success fw-bold">
                  {patients.length}
                </h1>
                <h5 className="text-muted">
                  🧑‍🤝‍🧑 Total Patients
                </h5>
              </div>
            </div>
          </div>

          {/* Appointments Card */}
          <div className="col-md-4 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-4"
                   style={{ 
                     backgroundColor: '#fff3e0' }}>
                <h1 className="text-warning fw-bold">
                  {appointments.length}
                </h1>
                <h5 className="text-muted">
                  📅 Total Appointments
                </h5>
              </div>
            </div>
          </div>

        </div>

        {/* Doctors Table */}
        <div className="card shadow border-0 mb-4"
             style={{ borderRadius: '15px' }}>
          <div className="card-header bg-primary 
                          text-white fw-bold"
               style={{ borderRadius: 
                        '15px 15px 0 0' }}>
            👨‍⚕️ Doctors List
          </div>
          <div className="card-body">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th>Name</th>
                  <th>Specialization</th>
                  <th>Department</th>
                  <th>Experience</th>
                  <th>Fee</th>
                  <th>Status</th>
                </tr>
              </thead>
              <tbody>
                {doctors.map((doctor) => (
                  <tr key={doctor.id}>
                    <td>
                      {doctor.user?.firstName}{' '}
                      {doctor.user?.lastName}
                    </td>
                    <td>{doctor.specialization}</td>
                    <td>{doctor.department}</td>
                    <td>
                      {doctor.experienceYears} yrs
                    </td>
                    <td>₹{doctor.consultationFee}</td>
                    <td>
                      <span className={`badge ${
                        doctor.isAvailable
                          ? 'bg-success'
                          : 'bg-danger'}`}>
                        {doctor.isAvailable
                          ? 'Available'
                          : 'Unavailable'}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

        {/* Appointments Table */}
        <div className="card shadow border-0 mb-4"
             style={{ borderRadius: '15px' }}>
          <div className="card-header bg-warning
                          text-white fw-bold"
               style={{ borderRadius:
                        '15px 15px 0 0' }}>
            📅 Appointments List
          </div>
          <div className="card-body">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th>Patient</th>
                  <th>Doctor</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Status</th>
                  <th>Reason</th>
                </tr>
              </thead>
              <tbody>
                {appointments.map((apt) => (
                  <tr key={apt.id}>
                    <td>
                      {apt.patient?.user?.firstName}{' '}
                      {apt.patient?.user?.lastName}
                    </td>
                    <td>
                      {apt.doctor?.user?.firstName}{' '}
                      {apt.doctor?.user?.lastName}
                    </td>
                    <td>{apt.appointmentDate}</td>
                    <td>{apt.appointmentTime}</td>
                    <td>
                      <span className={`badge ${
                        apt.status === 'CONFIRMED'
                          ? 'bg-success'
                          : apt.status === 'PENDING'
                          ? 'bg-warning'
                          : apt.status === 'CANCELLED'
                          ? 'bg-danger'
                          : 'bg-info'}`}>
                        {apt.status}
                      </span>
                    </td>
                    <td>{apt.reason}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  );
}

export default AdminDashboard;