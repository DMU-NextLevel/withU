import React from 'react'
import { BrowserRouter as Router, Route, Routes, useLocation } from 'react-router-dom'
import MainPage from './pages/MainPage'
import { HeaderMain, HeaderSub } from './components/layout/Header'
import Footer from './components/layout/Footer'
import IDFindPage from './pages/IDFindPage';
import Signup from './pages/Signup';
import Login from './pages/Login';
import A from './pages/a';
import MyPage from './pages/MyPage';

function App() {
	return (
		<Router>
			<AppWrapper />
		</Router>
	)
}

const AppWrapper = () => {
	const location = useLocation()
	const hideLayout = ['/login', '/signup']
	const mainPage = ['/']
	return (
		<>
			{!hideLayout.includes(location.pathname) ? mainPage.includes(location.pathname) ? <HeaderMain /> : <HeaderSub /> : null}
			<Routes>
				<Route path='/' element={<MainPage />} />
				<Route path="/login" element={<Login />} />
        		<Route path="/signup" element={<Signup />} />
        		<Route path="/idfind" element={<IDFindPage />} />
				<Route path="/mypage" element={<MyPage />} />
			</Routes>
			{!hideLayout.includes(location.pathname) && <Footer />}
		</>
	)
}

export default App;
