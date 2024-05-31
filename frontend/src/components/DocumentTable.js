import React from 'react';

const DocumentTable = ({ documents }) => {
  return (
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Serial Number</th>
          <th>Title</th>
          <th>Status</th>
          <th>Department</th>
          <th>User ID</th>
          <th>Created At</th>
          <th>Updated At</th>
        </tr>
      </thead>
      <tbody>
        {documents.map((doc) => (
          <tr key={doc.id}>
            <td>{doc.id}</td>
            <td>{doc.serialNumber}</td>
            <td>{doc.title}</td>
            <td>{doc.status}</td>
            <td>{doc.department}</td>
            <td>{doc.userId}</td>
            <td>{doc.createdAt}</td>
            <td>{doc.updatedAt}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
};

export default DocumentTable;