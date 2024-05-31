import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

function DocumentEditForm() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [document, setDocument] = useState({
    serialNumber: '',
    title: '',
    department: '',
    text: ''
  });

  useEffect(() => {
    const fetchDocument = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/v1/documents/${id}`, { withCredentials: true });
        setDocument(response.data);
      } catch (error) {
        console.error('Ошибка при получении документа:', error);
        if (error.response && error.response.status === 401) {
          navigate('/login');
        }
      }
    };

    fetchDocument();
  }, [id, navigate]);

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
      const response = await axios.put(`http://localhost:8080/v1/documents/${id}`, document, { withCredentials: true });
      if (response.status === 200) {
        console.log('Документ успешно обновлен');
      } else {
        console.error('Не удалось обновить документ');
      }
    } catch (error) {
      console.error('Ошибка при обновлении документа:', error);
      if (error.response && error.response.status === 401) {
        navigate('/login');
      }
    }
  };

  return (
    <div>
      <h2>Редактировать документ</h2>
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
        <button type="submit">Сохранить документ</button>
      </form>
    </div>
  );
}

export default DocumentEditForm;