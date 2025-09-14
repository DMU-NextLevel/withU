import React, { createContext, useState, useContext, useEffect, ReactNode } from 'react';
import { api } from '../AxiosInstance'; // 실제 API 인스턴스로 바꿔주세요

// 유저 타입 정의
export interface User {
  name?: string;
  nickName?: string;
  point?: number;
  address?: string;
  number?: string;
  areaNumber?: string | null;
  email?: string;
  socialProvider?: string | null;
  img?: string | null;
}

// 컨텍스트 타입 정의
interface AuthContextType {
  isLoggedIn: boolean;
  login: (status: string, userData?: User) => void;
  logout: () => void;
  user: User | null;
  setUser: (user: User) => void;
  refreshAuth: () => void;
}

// 컨텍스트 생성
const AuthContext = createContext<AuthContextType | null>(null);

// Provider 컴포넌트
export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
	const [user, setUser] = useState<User | null>(null)
	const [isLoggedIn, setIsLoggedIn] = useState<boolean>(false)

	/*
  useEffect(() => {
    const status = localStorage.getItem('LoginStatus');
    if (status) {
      setIsLoggedIn(true);

      const saved = localStorage.getItem('UserData');
      if (saved) {
        setUser(JSON.parse(saved));
      } else {
        // 저장된 유저 데이터가 없으면 API 호출
        api.get('/social/user')
          .then(res => {
            if (res.data.message === 'success') {
              setUser(res.data.data);
              localStorage.setItem('UserData', JSON.stringify(res.data.data));
            }
          })
          .catch(err => {
            console.error('유저 정보 불러오기 실패:', err);
          });
      }
    }
  }, []);
  */

	const refreshAuth = () => {
		// 로그인 여부와 무관하게 항상 유저 정보 요청
		api
			.get('/public/login/token')
			.then((res) => {
        console.log('토큰 조회' + res.data.data)
				if (res.data.data !== 'no login') {
					setIsLoggedIn(true)
          console.log('토큰이 존재하므로 유저 정보 조회시작')
          api.get('/social/user', {withCredentials: true}).then(
            (res) => {
              if(res.data.message === 'success') {
                console.log('유저 정보 조회 성공')
                setUser(res.data.data)
              }
            })
				} else {
          console.log('토큰 존재 안함')
					setUser(null)
					setIsLoggedIn(false)
				}
			})
			.catch(() => {
        console.log('토큰 조회 에러 발생')
				setUser(null)
				setIsLoggedIn(false)
			})
	}

  useEffect(() => {
    refreshAuth()
    console.log('토큰을 조회합니다....')
  }, [])

	const login = (status: string, userData?: User) => {
		setIsLoggedIn(true)

		if (userData) {
			setUser(userData)
			localStorage.setItem('UserData', JSON.stringify(userData))
		}
	}

	const logout = () => {
		// 서버에 로그아웃 요청
		api
			.post('/social/user/logout')
			.then((res) => {
				console.log('서버 로그아웃 성공:', res.data)
			})
			.catch((err) => {
				console.error('서버 로그아웃 실패:', err)
			})
			.finally(() => {
				// 로컬 상태 및 저장값 정리
				localStorage.removeItem('LoginStatus')
				localStorage.removeItem('UserData')
				localStorage.removeItem('accessToken')
				document.cookie = 'accessToken=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;'
				setIsLoggedIn(false)
				setUser(null)
			})
	}

	return <AuthContext.Provider value={{ isLoggedIn, login, logout, user, setUser, refreshAuth }}>{children}</AuthContext.Provider>
};

// 커스텀 훅
export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) throw new Error('useAuth must be used within an AuthProvider');
  return context;
};
