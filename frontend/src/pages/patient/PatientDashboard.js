import React, { useState, useEffect } from 'react';
import authService from '../../services/authService';
import api from '../../services/api';

function PatientDashboard() {
  const [appointments, setAppointments] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [showBooking, setShowBooking] = useState(false);
  const [bookingData, setBookingData] = useState({
    doctorId: '',
    appointmentDate: '',
    appointmentTime: '',
    reason: ''
  });
  const user = authService.getCurrentUser();

  useEffect(() => {
    fetchAppointments();
    fetchDoctors();
  }, []);

  // ─── Patient ID dhundo User ID se ─────────────
  const getPatientId = async () => {
    const patientRes = await api.get(
      `/admin/patients/all`);
    const currentPatient = patientRes.data
      .find(p => p.user?.id === user?.id);
    return currentPatient?.id;
  };

  // ─── Appointments fetch karo ───────────────────
  const fetchAppointments = async () => {
    try {
      const patientId = await getPatientId();
      if (patientId) {
        const res = await api.get(
          `/appointments/patient/${patientId}`);
        setAppointments(res.data);
      }
    } catch (err) {
      console.error('Error:', err);
    }
  };

  // ─── Available Doctors fetch karo ─────────────
  const fetchDoctors = async () => {
    try {
      const res = await api.get(
        '/admin/doctors/available');
      setDoctors(res.data);
    } catch (err) {
      console.error('Error:', err);
    }
  };

  // ─── Appointment Book karo ─────────────────────
  const handleBooking = async () => {
    try {
      const patientId = await getPatientId();

      await api.post('/appointments/book', {
        patientId: patientId,
        doctorId: parseInt(bookingData.doctorId),
        appointmentDate: bookingData.appointmentDate,
        appointmentTime: bookingData.appointmentTime,
        reason: bookingData.reason
      });

      alert('Appointment booked successfully!');
      setShowBooking(false);
      setBookingData({
        doctorId: '',
        appointmentDate: '',
        appointmentTime: '',
        reason: ''
      });
      fetchAppointments();

    } catch (err) {
      alert('Error booking appointment!');
    }
  };

  // ─── Appointment Cancel karo ───────────────────
  const handleCancel = async (id) => {
    try {
      await api.put(`/appointments/cancel/${id}`);
      fetchAppointments();
    } catch (err) {
      console.error('Error:', err);
    }
  };

  // ─── Logout ────────────────────────────────────
  const handleLogout = () => {
    authService.logout();
  };

  return (
    <div>
      {/* Navbar */}
      <nav className="navbar navbar-dark
                      bg-info px-4">
        <span className="navbar-brand fw-bold">
          🏥 HMS — Patient Panel
        </span>
        <div className="d-flex align-items-center">
          <span className="text-white me-3">
            🧑 {user?.firstName} {user?.lastName}
          </span>
          <button
            className="btn btn-outline-light"
            onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>

      <div className="container mt-4">

        {/* Welcome + Book Button */}
        <div className="d-flex justify-content-between
                        align-items-center mb-4">
          <h4>Welcome, {user?.firstName}! 👋</h4>
          <button
            className="btn btn-info text-white fw-bold"
            onClick={() =>
              setShowBooking(!showBooking)}>
            {showBooking
              ? '✕ Close'
              : '+ Book Appointment'}
          </button>
        </div>

        {/* Booking Form */}
        {showBooking && (
          <div className="card shadow border-0 mb-4"
               style={{ borderRadius: '15px' }}>
            <div className="card-header bg-info
                            text-white fw-bold"
                 style={{ borderRadius:
                          '15px 15px 0 0' }}>
              📅 Book New Appointment
            </div>
            <div className="card-body">
              <div className="row">

                {/* Doctor Select */}
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Select Doctor
                  </label>
                  <select
                    className="form-select"
                    value={bookingData.doctorId}
                    onChange={(e) =>
                      setBookingData({
                        ...bookingData,
                        doctorId: e.target.value
                      })}>
                    <option value="">
                      -- Select Doctor --
                    </option>
                    {doctors.map((doc) => (
                      <option
                        key={doc.id}
                        value={doc.id}>
                        Dr. {doc.user?.firstName}{' '}
                        {doc.user?.lastName} —{' '}
                        {doc.specialization}
                      </option>
                    ))}
                  </select>
                </div>

                {/* Date */}
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Appointment Date
                  </label>
                  <input
                    type="date"
                    className="form-control"
                    value={bookingData.appointmentDate}
                    onChange={(e) =>
                      setBookingData({
                        ...bookingData,
                        appointmentDate: e.target.value
                      })}
                  />
                </div>

                {/* Time */}
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Appointment Time
                  </label>
                  <input
                    type="time"
                    className="form-control"
                    value={bookingData.appointmentTime}
                    onChange={(e) =>
                      setBookingData({
                        ...bookingData,
                        appointmentTime: e.target.value
                      })}
                  />
                </div>

                {/* Reason */}
                <div className="col-md-6 mb-3">
                  <label className="form-label fw-bold">
                    Reason for Visit
                  </label>
                  <input
                    type="text"
                    className="form-control"
                    placeholder="Enter reason..."
                    value={bookingData.reason}
                    onChange={(e) =>
                      setBookingData({
                        ...bookingData,
                        reason: e.target.value
                      })}
                  />
                </div>

              </div>

              <button
                className="btn btn-info
                           text-white fw-bold px-4"
                onClick={handleBooking}>
                Book Appointment
              </button>
            </div>
          </div>
        )}

        {/* Stats */}
        <div className="row mb-4">

          <div className="col-md-3 mb-3">
            <div className="card shadow text-center
                            border-0"
                 style={{ borderRadius: '15px' }}>
              <div className="card-body py-3"
                   style={{
                     backgroundColor: '#e3f2fd' }}>
                <h2 className="text-info fw-bold">
                  {appointments.length}
                </h2>
                <p className="text-muted mb-0">
                  Total
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
                     backgroundColor: '#e8f5e9' }}>
                <h2 className="text-success fw-bold">
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
                <h2 className="fw-bold"
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
          <div className="card-header bg-info
                          text-white fw-bold"
               style={{ borderRadius:
                        '15px 15px 0 0' }}>
            📋 My Appointments
          </div>
          <div className="card-body">
            <table className="table table-hover">
              <thead>
                <tr>
                  <th>Doctor</th>
                  <th>Specialization</th>
                  <th>Date</th>
                  <th>Time</th>
                  <th>Reason</th>
                  <th>Status</th>
                  <th>Action</th>
                </tr>
              </thead>
              <tbody>
                {appointments.length === 0 ? (
                  <tr>
                    <td colSpan="7"
                        className="text-center
                                   text-muted py-4">
                      No appointments yet!
                      Book your first appointment!
                    </td>
                  </tr>
                ) : (
                  appointments.map((apt) => (
                    <tr key={apt.id}>
                      <td>
                        Dr.{' '}
                        {apt.doctor?.user?.firstName}
                        {' '}
                        {apt.doctor?.user?.lastName}
                      </td>
                      <td>
                        {apt.doctor?.specialization}
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
                        {apt.status !== 'CANCELLED' &&
                         apt.status !== 'COMPLETED' && (
                          <button
                            className="btn btn-sm
                                       btn-danger"
                            onClick={() =>
                              handleCancel(apt.id)}>
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

export default PatientDashboard;