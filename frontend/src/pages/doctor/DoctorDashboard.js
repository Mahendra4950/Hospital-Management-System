import React, { useState, useEffect } from 'react';
import authService from '../../services/authService';
import api from '../../services/api';

function DoctorDashboard() {
  const [appointments, setAppointments] = useState([]);
  const user = authService.getCurrentUser();

  useEffect(() => {
    fetchAppointments();
  }, []);

  const fetchAppointments = async () => {
    try {
      const res = await api.get(
        `/appointments/doctor/${user?.id}`);
      setAppointments(res.data);
    } catch (err) {
      console.error('Error:', err);
    }
  };

  const handleStatusUpdate = async (id, status) => {
    try {
      await api.put(`/appointments/status/${id}`,
        { status });
      fetchAppointments();
    } catch (err) {
      console.error('Error:', err);
    }
  };

  const handleAddNotes = async (id) => {
    const notes = prompt('Enter notes:');
    if (notes) {
      try {
        await api.put(
          `/appointments/notes/${id}`,
          { notes });
        fetchAppointments();
      } catch (err) {
        console.error('Error:', err);
      }
    }
  };

  const handleLogout = () => {
    authService.logout();
  };

  return (
    <div>
      {/* Navbar */}
      <nav className="navbar navbar-dark
                      bg-success px-4">
        <span className="navbar-brand fw-bold">
          🏥 HMS — Doctor Panel
        </span>
        <div className="d-flex align-items-center">
          <span className="text-white me-3">
            👨‍⚕️ Dr. {user?.firstName} {user?.lastName}
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
          Welcome, Dr. {user?.firstName}! 👋
        </h4>

        {/* Stats */}
        <div className="row mb-4">

          <div className="col-md-3 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-3"
                   style={{
                     backgroundColor: '#e8f5e9' }}>
                <h2 className="text-success fw-bold">
                  {appointments.length}
                </h2>
                <p className="text-muted mb-0">
                  Total Appointments
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-3"
                   style={{
                     backgroundColor: '#fff3e0' }}>
                <h2 className="text-warning fw-bold">
                  {appointments.filter(
                    a => a.status === 'PENDING')
                    .length}
                </h2>
                <p className="text-muted mb-0">
                  Pending
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-3"
                   style={{
                     backgroundColor: '#e3f2fd' }}>
                <h2 className="text-primary fw-bold">
                  {appointments.filter(
                    a => a.status === 'CONFIRMED')
                    .length}
                </h2>
                <p className="text-muted mb-0">
                  Confirmed
                </p>
              </div>
            </div>
          </div>

          <div className="col-md-3 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-3"
                   style={{
                     backgroundColor: '#f3e5f5' }}>
                <h2 fw-bold
                    style={{ color: '#7b1fa2' }}>
                  {appointments.filter(
                    a => a.status === 'COMPLETED')
                    .length}
                </h2>
                <p className="text-muted mb-0">
                  Completed
                </p>
              </div>
            </div>
          </div>

        </div>

        {/* Appointments Table */}
        <div className="card shadow border-0"
             style={{ borderRadius: '15px' }}>
          <div className="card-header bg-success
                          text-white fw-bold"
               style={{ borderRadius:
                        '15px 15px 0 0' }}>
            📅 My Appointments
          </div>
          <div className="card-body">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th>Patient</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Reason</th>
                  <th>Status</th>
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {appointments.length === 0 ? (
                  <tr>
                    <td colSpan="6"
                        className="text-center
                                   text-muted py-4">
                      No appointments yet!
                    </td>
                  </tr>
                ) : (
                  appointments.map((apt) => (
                    <tr key={apt.id}>
                      <td>
                        {apt.patient?.user?.firstName}
                        {' '}
                        {apt.patient?.user?.lastName}
                      </td>
                      <td>{apt.appointmentDate}</td>
                      <td>{apt.appointmentTime}</td>
                      <td>{apt.reason}</td>
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
                      <td>
                        {apt.status === 'PENDING' && (
                          <button
                            className="btn btn-sm
                                       btn-success me-1"
                            onClick={() =>
                              handleStatusUpdate(
                                apt.id, 'CONFIRMED')}>
                            Confirm
                          </button>
                        )}
                        {apt.status === 'CONFIRMED' && (
                          <button
                            className="btn btn-sm
                                       btn-info me-1"
                            onClick={() =>
                              handleAddNotes(apt.id)}>
                            Complete
                          </button>
                        )}
                        {apt.status !== 'CANCELLED' &&
                         apt.status !== 'COMPLETED' && (
                          <button
                            className="btn btn-sm
                                       btn-danger"
                            onClick={() =>
                              handleStatusUpdate(
                                apt.id, 'CANCELLED')}>
                            Cancel
                          </button>
                        )}
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </div>
  );
}

export default DoctorDashboard;