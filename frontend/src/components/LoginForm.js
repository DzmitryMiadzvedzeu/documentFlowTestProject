import React, { useState } from 'react';

function LoginForm() {
  const [credentials, setCredentials] = useState({ userId: '', password: '' });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setCredentials({
      ...credentials,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const { userId, password } = credentials;
    const basicAuth = 'Basic ' + btoa(`${userId}:${password}`);
    
    try {
      const response = await fetch('http://localhost:8080/v1/documents', {
        method: 'GET',
        headers: {
          'Authorization': basicAuth
        }
      });
      if (response.ok) {
        console.log('Logged in successfully');
        // Сохраните токен или выполните перенаправление после успешного входа
      } else {
        console.error('Failed to login');
      }
    } catch (error) {
      console.error('Error logging in:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>User ID:</label>
        <input type="text" name="userId" value={credentials.userId} onChange={handleChange} required />
      </div>
      <div>
        <label>Password:</label>
        <input type="password" name="password" value={credentials.password} onChange={handleChange} required />
      </div>
      <button type="submit">Login</button>
    </form>
  );
}

export default LoginForm;