import api from './api';

const authService = {

  // Login
  login: async (email, password) => {
    const response = await api.post(
      '/auth/login', { email, password });

    // Token aur user info save karo
    localStorage.setItem(
      'token', response.data.token);
    localStorage.setItem(
      'role', response.data.role);
    localStorage.setItem(
      'user', JSON.stringify(response.data));

    return response.data;
  },

  // Logout
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('user');
    window.location.href = '/login';
  },

  // Current user lo
  getCurrentUser: () => {
    return JSON.parse(
      localStorage.getItem('user'));
  },

  // Role lo
  getRole: () => {
    return localStorage.getItem('role');
  },

  // Login hai ya nahi check karo
  isLoggedIn: () => {
    return !!localStorage.getItem('token');
  },
};

export default authService;