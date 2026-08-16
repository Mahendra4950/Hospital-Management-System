import React from 'react';
import { BrowserRouter as Router,
         Routes, Route, Navigate }
         from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './pages/Login';
import AdminDashboard from
  './pages/admin/AdminDashboard';
import DoctorDashboard from
  './pages/doctor/DoctorDashboard';
import PatientDashboard from
  './pages/patient/PatientDashboard';

function App() {
  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/"
                 element={
                   <Navigate to="/login" />} />
          <Route path="/login"
                 element={<Login />} />
          <Route path="/admin/dashboard"
                 element={<AdminDashboard />} />
          <Route path="/doctor/dashboard"
                 element={<DoctorDashboard />} />
          <Route path="/patient/dashboard"
                 element={<PatientDashboard />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;