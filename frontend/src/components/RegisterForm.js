import React, { useState } from 'react';

function RegisterForm() {
  const [user, setUser] = useState({
    name: '',
    surname: '',
    position: '',
    department: '',
    password: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({
      ...user,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch('http://localhost:8080/v1/users/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(user),
      });
      if (response.ok) {
        console.log('User registered successfully');
      } else {
        console.error('Failed to register user');
      }
    } catch (error) {
      console.error('Error registering user:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Name:</label>
        <input type="text" name="name" value={user.name} onChange={handleChange} required />
      </div>
      <div>
        <label>Surname:</label>
        <input type="text" name="surname" value={user.surname} onChange={handleChange} required />
      </div>
      <div>
        <label>Position:</label>
        <input type="text" name="position" value={user.position} onChange={handleChange} required />
      </div>
      <div>
        <label>Department:</label>
        <select name="department" value={user.department} onChange={handleChange} required>
          <option value="" disabled>Select department</option>
          <option value="ECONOMIST_DEP">Economist Department</option>
          <option value="HR_DEP">HR Department</option>
          <option value="DEVELOPMENT_DEP">Development Department</option>
          <option value="MANAGEMENT">Management</option>
        </select>
      </div>
      <div>
        <label>Password:</label>
        <input type="password" name="password" value={user.password} onChange={handleChange} required />
      </div>
      <button type="submit">Register</button>
    </form>
  );
}

export default RegisterForm;