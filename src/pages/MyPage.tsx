import React from 'react';
import styled from 'styled-components';

const MyPage = () => {
  return (
    <Container>
      <Sidebar>
        <Logo>withU</Logo>
        <NavMenu>
          <MenuButton>오픈예정</MenuButton>
          <MenuButton>펀딩 +</MenuButton>
          <MenuButton>프리오더</MenuButton>
          <MenuButton>스토어</MenuButton>
          <MenuButton>더보기</MenuButton>
        </NavMenu>

        <ProfileBox>
          <Emoji>😕</Emoji>
          <Name>김주련</Name>
          <SettingsBtn>내 정보 설정</SettingsBtn>
        </ProfileBox>

        <ActivityMenu>
          <MenuButton>최근 조회</MenuButton>
          <MenuButton>즐겨찾기</MenuButton>
          <MenuButton>
            초대 <span className="highlight">50,000P</span>
          </MenuButton>
          <MenuButton>팔로잉</MenuButton>
          <MenuButton>간편결제 설정</MenuButton>
          <MenuButton>스타터 문의하기</MenuButton>
          <MenuButton>프로젝트 만들기</MenuButton>
          <MenuButton>스타터 추천</MenuButton>
        </ActivityMenu>
      </Sidebar>

      <Main>
        <ProfileSection>
          <h2>김주련님 안녕하세요.</h2>
          <InviteBanner>
            새로운 위터를 초대하고 최대 50,000P 리워드를 수령하세요!
          </InviteBanner>
          <StatsGrid>
            <StatItem>
              <span>펀딩+</span>
              <strong>0</strong>
            </StatItem>
            <StatItem>
              <span>스토어</span>
              <strong>0</strong>
            </StatItem>
            <StatItem>
              <span>지지서명</span>
              <button>보기</button>
            </StatItem>
            <StatItem>
              <span>알림신청</span>
              <button>보기</button>
            </StatItem>
            <StatItem>
              <span>포인트</span>
              <strong>0P</strong>
            </StatItem>
            <StatItem>
              <span>쿠폰</span>
              <strong>3장</strong>
            </StatItem>
          </StatsGrid>
        </ProfileSection>

        <Banner>
          <img src="https://via.placeholder.com/800x150" alt="배너" />
        </Banner>

        <PurpleCard>
          스타터 추천하기<br />
          스타터 추천하면 최대 30,000P 증정!
        </PurpleCard>

        <ProductList>
          {[1, 2, 3, 4, 5, 6].map((id) => (
            <ProductCard key={id}>
              <img src="https://via.placeholder.com/100x100" alt={`상품${id}`} />
              <div className="tag">멤버십 특가</div>
              <div className="discount">33% 15,000원</div>
            </ProductCard>
          ))}
        </ProductList>
      </Main>
    </Container>
  );
};

export default MyPage;

// styled-components
const Container = styled.div`
  display: flex;
  height: 100vh;
  font-family: 'Pretendard', sans-serif;
`;

const Sidebar = styled.aside`
  width: 260px;
  background: #f4f4f8;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
`;

const Logo = styled.div`
  font-size: 28px;
  font-weight: bold;
  color: #6c00d7;
  margin-bottom: 30px;
`;

const NavMenu = styled.div`
  display: flex;
  flex-direction: column;
  gap: 12px;
`;

const MenuButton = styled.button`
  background: none;
  border: none;
  text-align: left;
  font-size: 16px;
  font-weight: 500;
  color: #333;
  padding: 8px 12px;
  border-radius: 8px;
  transition: background 0.2s;

  .highlight {
    color: #6c00d7;
    font-weight: bold;
  }

  &:hover {
    background: #e8e2f2;
  }

  cursor: pointer;
`;

const ProfileBox = styled.div`
  text-align: center;
  margin: 30px 0;
`;

const Emoji = styled.div`
  font-size: 48px;
`;

const Name = styled.div`
  font-weight: bold;
  margin: 10px 0;
  font-size: 16px;
`;

const SettingsBtn = styled.button`
  padding: 6px 14px;
  border-radius: 20px;
  border: 1px solid #ccc;
  background: white;
  font-size: 13px;
  cursor: pointer;

  &:hover {
    background: #f3f3f3;
  }
`;

const ActivityMenu = styled.div`
  display: flex;
  flex-direction: column;
  gap: 10px;
`;

const Main = styled.main`
  flex: 1;
  padding: 40px 60px;
  background: #fff;
  overflow-y: auto;
`;

const ProfileSection = styled.section`
  margin-bottom: 20px;

  h2 {
    font-size: 22px;
    font-weight: bold;
    margin-bottom: 12px;
  }
`;

const InviteBanner = styled.div`
  background: #d4b6f6;
  padding: 16px 24px;
  border-radius: 12px;
  font-weight: 600;
  font-size: 15px;
  margin-bottom: 25px;
`;

const StatsGrid = styled.div`
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  gap: 16px;
  margin-bottom: 30px;
`;

const StatItem = styled.div`
  background: #fff;
  border: 1px solid #ddd;
  padding: 14px;
  border-radius: 12px;
  text-align: center;
  font-size: 14px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.03);

  span {
    display: block;
    margin-bottom: 6px;
    color: #777;
  }

  button, strong {
    background: none;
    border: none;
    font-weight: bold;
    font-size: 16px;
    color: #333;
    cursor: pointer;
  }
`;

const Banner = styled.div`
  margin-bottom: 30px;

  img {
    width: 100%;
    height: auto;
    border-radius: 12px;
  }
`;

const PurpleCard = styled.div`
  background: #ae84f5;
  color: white;
  padding: 22px;
  border-radius: 15px;
  font-weight: bold;
  text-align: center;
  font-size: 16px;
  line-height: 1.6;
  margin-bottom: 30px;
`;

const ProductList = styled.div`
  display: flex;
  gap: 16px;
  overflow-x: auto;
  padding-bottom: 20px;
`;

const ProductCard = styled.div`
  min-width: 140px;
  background: #fff;
  border: 1px solid #eee;
  border-radius: 12px;
  text-align: center;
  padding: 12px;
  box-shadow: 0 2px 6px rgba(0,0,0,0.04);

  img {
    border-radius: 8px;
    width: 100%;
  }

  .tag {
    background: #6c00d7;
    color: white;
    font-size: 11px;
    border-radius: 12px;
    padding: 4px 8px;
    display: inline-block;
    margin-top: 10px;
  }

  .discount {
    font-weight: bold;
    margin-top: 6px;
    font-size: 15px;
  }
`;
