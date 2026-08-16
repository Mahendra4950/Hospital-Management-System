import React, { useState } from 'react';
import authService from '../services/authService';

function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      const data = await authService.login(
        email, password);

      // Role ke hisaab se redirect karo
      if (data.role === 'ADMIN') {
        window.location.href = '/admin/dashboard';
      } else if (data.role === 'DOCTOR') {
        window.location.href = '/doctor/dashboard';
      } else if (data.role === 'PATIENT') {
        window.location.href = '/patient/dashboard';
      }

    } catch (err) {
      setError('Invalid email or password!');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container-fluid min-vh-100 
                    d-flex align-items-center 
                    justify-content-center"
         style={{ backgroundColor: '#f0f4f8' }}>

      <div className="card shadow-lg p-4"
           style={{ width: '400px',
                    borderRadius: '15px' }}>

        {/* Header */}
        <div className="text-center mb-4">
          <h2 className="text-primary fw-bold">
            🏥 HMS
          </h2>
          <p className="text-muted">
            Hospital Management System
          </p>
        </div>

        {/* Error Message */}
        {error && (
          <div className="alert alert-danger">
            {error}
          </div>
        )}

        {/* Login Form */}
        <form onSubmit={handleLogin}>

          {/* Email */}
          <div className="mb-3">
            <label className="form-label fw-bold">
              Email Address
            </label>
            <input
              type="email"
              className="form-control"
              placeholder="Enter your email"
              value={email}
              onChange={(e) => 
                setEmail(e.target.value)}
              required
            />
          </div>

          {/* Password */}
          <div className="mb-3">
            <label className="form-label fw-bold">
              Password
            </label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter your password"
              value={password}
              onChange={(e) => 
                setPassword(e.target.value)}
              required
            />
          </div>

          {/* Login Button */}
          <button
            type="submit"
            className="btn btn-primary w-100 
                       fw-bold py-2"
            disabled={loading}>
            {loading ? 'Logging in...' : 'Login'}
          </button>

        </form>

        {/* Footer */}
        <div className="text-center mt-3">
          <small className="text-muted">
            HMS — Healthcare Made Simple
          </small>
        </div>

      </div>
    </div>
  );
}

export default Login;