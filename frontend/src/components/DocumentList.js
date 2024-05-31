import React, { useState, useEffect } from 'react';
import axios from 'axios';
import DocumentTable from './DocumentTable';
import SearchForm from './SearchForm';

function DocumentList() {
  const [documents, setDocuments] = useState([]);

  useEffect(() => {
    const fetchDocuments = async () => {
      try {
        const response = await axios.get('http://localhost:8080/v1/documents', { withCredentials: true });
        setDocuments(response.data);
      } catch (error) {
        console.error('Ошибка при получении списка документов:', error);
      }
    };

    fetchDocuments();
  }, []);

  const handleSearch = async (searchParams) => {
    try {
      const response = await axios.get('http://localhost:8080/v1/documents/search', { params: searchParams });
      setDocuments(response.data);
    } catch (error) {
      console.error('Ошибка при поиске документов:', error);
    }
  };

  return (
    <div>
      <h2>Список документов</h2>
      <SearchForm onSearch={handleSearch} />
      <DocumentTable documents={documents} />
    </div>
  );
}

export default DocumentList;