import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function SearchForm({ onSearch }) {
  const navigate = useNavigate();
  const [searchParams, setSearchParams] = useState({
    serialNumber: '',
    title: '',
    status: '',
    department: '',
    createdAt: '',
    updatedAt: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setSearchParams({
      ...searchParams,
      [name]: value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.get('http://localhost:8080/v1/documents/search', {
        params: searchParams,
        withCredentials: true
      });
      onSearch(response.data);
    } catch (error) {
      console.error('Ошибка при поиске документов:', error);
      if (error.response && error.response.status === 401) {
        navigate('/login');
      }
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>Серийный номер:</label>
        <input type="text" name="serialNumber" value={searchParams.serialNumber} onChange={handleChange} />
      </div>
      <div>
        <label>Название:</label>
        <input type="text" name="title" value={searchParams.title} onChange={handleChange} />
      </div>
      <div>
        <label>Статус:</label>
        <input type="text" name="status" value={searchParams.status} onChange={handleChange} />
      </div>
      <div>
        <label>Отдел:</label>
        <select name="department" value={searchParams.department} onChange={handleChange}>
          <option value="">Выберите отдел</option>
          <option value="HR_DEP">HR_DEP</option>
          <option value="ECONOMIST_DEP">ECONOMIST_DEP</option>
          <option value="DEVELOPMENT_DEP">DEVELOPMENT_DEP</option>
          <option value="MANAGEMENT">MANAGEMENT</option>
        </select>
      </div>
      <div>
        <label>Дата создания:</label>
        <input type="datetime-local" name="createdAt" value={searchParams.createdAt} onChange={handleChange} />
      </div>
      <div>
        <label>Дата обновления:</label>
        <input type="datetime-local" name="updatedAt" value={searchParams.updatedAt} onChange={handleChange} />
      </div>
      <button type="submit">Поиск</button>
    </form>
  );
}

export default SearchForm;