import React from 'react';

const A = () => {
  return (
    <div style={{ padding: '40px', fontFamily: 'Noto Sans KR, sans-serif' }}>
      <h1 style={{ fontSize: '32px', marginBottom: '24px' }}>개인정보 수집 및 이용 동의</h1>

      <h2 style={{ fontSize: '20px', marginBottom: '16px' }}>
        [필수] 개인정보 수집 및 이용 동의
      </h2>

      <table style={{ width: '100%', borderCollapse: 'collapse', marginBottom: '32px' }}>
        <thead>
          <tr>
            {['목적', '수집항목', '보유 및 이용기간'].map((item, idx) => (
              <th
                key={idx}
                style={{
                  borderBottom: '1px solid #ccc',
                  padding: '12px 16px',
                  backgroundColor: '#f9f9f9',
                  textAlign: 'center' as const,
                }}
              >
                {item}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          <tr>
            <td style={{ padding: '16px', verticalAlign: 'top' }}>가입(전체회원)</td>
            <td style={{ padding: '16px', verticalAlign: 'top' }}>
              [이메일 회원가입] 이름, 이메일, 비밀번호<br />
              [카카오계정 연동 가입] 이메일, 닉네임, 프로필 사진, 전화번호(선택), 계정 고유식별자<br />
              [네이버 연동 가입] 이메일, 별명, 이름(선택), 프로필 사진(선택), 성별(선택), 생일(선택), 연령대(선택), 계정 고유식별자[페이스북 연동 가입]<br />
              이메일, 이름, 프로필사진, 계정 고유식별자<br />
              [애플 연동 회원가입] 이메일, 이름, 계정 고유식별자<br />
              *안드로이드앱에서는 불가
            </td>
            <td style={{ padding: '16px', verticalAlign: 'top', color: 'blue', fontWeight: 'bold' }}>
              회원 탈퇴 시<br />또는 법령에<br />따른 보존기간
            </td>
          </tr>
          <tr>
            <td style={{ padding: '16px', verticalAlign: 'top' }}>서비스 품질향상 및 부정이용 방지</td>
            <td style={{ padding: '16px', verticalAlign: 'top' }}>
              서비스 이용기록, 접속로그, 쿠키, 접속IP 정보, MAC주소, 기기정보, OS(Android/iOS) 및 버전, 브라우저 종류
            </td>
            <td style={{ padding: '16px', verticalAlign: 'top', color: 'blue', fontWeight: 'bold' }}>
              회원 탈퇴 시<br />지체없이 삭제<br />단, 접속IP의 경우 3개월
            </td>
          </tr>
        </tbody>
      </table>

      <ul style={{ fontSize: '14px', lineHeight: '1.8' }}>
        <li>필수 정보 수집에 거부하실 경우 서비스 이용에 제한이 있을 수 있습니다.</li>
        <li>
          자세한 개인정보 처리에 관한 사항은{' '}
          <a href="#" style={{ color: '#7952f5', textDecoration: 'underline' }}>
            위드유 개인정보처리방침
          </a>
          을 참고하시기 바랍니다.
        </li>
      </ul>
    </div>
  );
};

export default A;