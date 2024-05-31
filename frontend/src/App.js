import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate } from 'react-router-dom';
import DocumentList from './components/DocumentList';
import DocumentForm from './components/DocumentForm';
import DocumentEditForm from './components/DocumentEditForm';
import RegisterForm from './components/RegisterForm';
import LoginForm from './components/LoginForm';

function App() {
  const [view, setView] = useState('list');
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const handleSaveDocument = async (document) => {
    try {
      const response = await fetch('http://localhost:8080/v1/documents/create', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(document),
        credentials: 'include',
      });
      if (response.ok) {
        console.log('Document saved successfully');
        setView('list');
      } else {
        console.error('Failed to save document');
      }
    } catch (error) {
      console.error('Error saving document:', error);
    }
  };

  return (
    <Router>
      <div className="App">
        <h1>Document Management</h1>
        <nav>
          <Link to="/">Home</Link>
          <Link to="/add">Add Document</Link>
          <Link to="/register">Register</Link>
          <Link to="/login">Login</Link>
        </nav>
        <Routes>
          <Route path="/" element={<DocumentList />} />
          <Route path="/add" element={isAuthenticated ? <DocumentForm onSave={handleSaveDocument} /> : <Navigate to="/login" />} />
          <Route path="/edit/:id" element={isAuthenticated ? <DocumentEditForm /> : <Navigate to="/login" />} />
          <Route path="/register" element={<RegisterForm />} />
          <Route path="/login" element={<LoginForm setIsAuthenticated={setIsAuthenticated} />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;