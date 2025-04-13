import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Header from './components/layout/Header';
import IDFindPage from './pages/IDFindPage';
import Signup from './pages/Signup';
import Login from './pages/Login';
import A from './pages/a';

function App() {
  return (
    <Router>
      <Header />

      {/* 라우터 설정 */}
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<Signup />} />
        <Route path="/idfind" element={<IDFindPage />} />
        <Route path="/IDFindPage" element={<IDFindPage />} />
        <Route path="/a" element={<A />} />
      </Routes>
    </Router>
  );
}

export default App;
