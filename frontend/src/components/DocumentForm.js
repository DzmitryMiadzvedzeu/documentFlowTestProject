import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function DocumentForm() {
  const navigate = useNavigate();
  const [document, setDocument] = useState({
    serialNumber: '',
    title: '',
    department: '',
    text: '',
    user_id: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setDocument({
      ...document,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/v1/documents/create', document, { withCredentials: true });
      if (response.status === 200) {
        console.log('Документ успешно создан');
        navigate('/');
      } else {
        console.error('Не удалось создать документ');
      }
    } catch (error) {
      console.error('Ошибка при создании документа:', error);
      if (error.response && error.response.status === 401) {
        navigate('/login');
      }
    }
  };

  return (
    <div>
      <h2>Создать новый документ</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Серийный номер:</label>
          <input
            type="text"
            name="serialNumber"
            value={document.serialNumber}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Название:</label>
          <input
            type="text"
            name="title"
            value={document.title}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>Отдел:</label>
          <select name="department" value={document.department} onChange={handleChange}>
            <option value="HR_DEP">HR_DEP</option>
            <option value="ECONOMIST_DEP">ECONOMIST_DEP</option>
            <option value="DEVELOPMENT_DEP">DEVELOPMENT_DEP</option>
            <option value="MANAGEMENT">MANAGEMENT</option>
          </select>
        </div>
        <div>
          <label>Текст:</label>
          <textarea
            name="text"
            value={document.text}
            onChange={handleChange}
          />
        </div>
        <div>
          <label>User ID:</label>
          <input
            type="text"
            name="user_id"
            value={document.user_id}
            onChange={handleChange}
          />
        </div>
        <button type="submit">Создать документ</button>
      </form>
    </div>
  );
}

export default DocumentForm;