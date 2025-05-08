import React, { useEffect, useState } from 'react';
import styled from 'styled-components';
import { api } from '../AxiosInstance';

interface ProjectItem {
  id: number;
  title: string;
  titleImg: string;
  completionRate: number;
  recommendCount: number;
  tags: string[];
  pageCount: number;
  totalCount: number;
  userCount: number;
  createdAt: string;
  isRecommend: boolean;
  expired: string;
  isExpired: boolean;
}

interface ProjectResponse {
  message: string;
  data: ProjectItem[];
}

const categories = [
  { label: '전체', icon: 'bi bi-circle', tag: '' },
  { label: '테크/가전', icon: 'bi bi-cpu', tag: '1' },
  { label: '라이프스타일', icon: 'bi bi-house', tag: '2' },
  { label: '패션/잡화', icon: 'bi bi-bag', tag: '3' },
  { label: '뷰티/헬스', icon: 'bi bi-heart-pulse', tag: '4' },
  { label: '취미/DIY', icon: 'bi bi-brush', tag: '5' },
  { label: '게임', icon: 'bi bi-controller', tag: '6' },
  { label: '교육/키즈', icon: 'bi bi-book', tag: '7' },
  { label: '반려동물', icon: 'bi bi-star', tag: '8' },
  { label: '여행/레저', icon: 'bi bi-airplane', tag: '9' },
  { label: '푸드/음료', icon: 'bi bi-cup-straw', tag: '10' },
];

const orderOptions = [
  { value: 'RECOMMEND', label: '추천순' },
  { value: 'COMPLETION', label: '달성률순' },
  { value: 'USER', label: '유저순' },
  { value: 'CREATED', label: '생성일순' }
];


const Search: React.FC = () => {
  const [projects, setProjects] = useState<ProjectItem[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  const [order, setOrder] = useState('RECOMMEND');
  const [page, setPage] = useState('1');
  const [tag, setTag] = useState('');

  const orderIndex = orderOptions.findIndex(opt => opt.value === order);


  const fetchProjects = async () => {
    setLoading(true);
    setError(null);
    try {
      const query = `/public/project/all?order=${order}&page=${page}&tag=${tag}`;
      const response = await api.get<ProjectResponse>(query);
      setProjects(response.data.data);
    } catch {
      setError('프로젝트 불러오기 실패');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchProjects();
  }, [tag]); // 태그 변경 시 자동 검색

  return (
    <Container>
      <CategoryRow>
        {categories.map((cat) => (
          <CategoryItem
            key={cat.tag}
            onClick={() => setTag(cat.tag)}
            className={tag === cat.tag ? 'active' : ''}
          >
            <i className={cat.icon}></i>
            <span>{cat.label}</span>
          </CategoryItem>
        ))}
      </CategoryRow>
      <OrderTabWrapper>
        <OrderSlider style={{ transform: `translateX(${orderIndex * 100}%)` }} />
        {orderOptions.map((opt, idx) => (
          <OrderButton
            key={opt.value}
            onClick={() => setOrder(opt.value)}
            className={order === opt.value ? 'active' : ''}
          >
            {opt.label}
          </OrderButton>
        ))}
      </OrderTabWrapper>


      {/* <SearchForm>
        <label>
          정렬순서 (order):
          <Select value={order} onChange={(e) => setOrder(e.target.value)}>
            {orderOptions.map((opt) => (
              <option key={opt.value} value={opt.value}>
                {opt.label}
              </option>
            ))}
          </Select>
        </label>
        <label>
          페이지 (page):
          <Input value={page} onChange={(e) => setPage(e.target.value)} />
        </label>
        <SearchButton onClick={fetchProjects}>검색</SearchButton>
      </SearchForm>

      <SelectedTagText>
        {categories.find(c => c.tag === tag)?.label || '전체'} 
        <br />
      </SelectedTagText> */}

      {loading && <p>로딩 중...</p>}
      {projects.length === 0 && !loading && <div>검색 결과가 없습니다.</div>}
      {error && <ErrorText>{error}</ErrorText>}
      
      <CardList>
        {projects.map((item) => (
          <Card key={item.id}>
          <Thumbnail src={`https://api.nextlevel.r-e.kr/img/${item.titleImg}`} alt={item.title} />
          <CardContent>
            <h3>{item.title}</h3>
        
            <ProgressBarWrapper>
              <ProgressBar percent={item.completionRate} />
            </ProgressBarWrapper>
        
            <InfoRow>{item.completionRate}% 달성</InfoRow>
            <InfoRow>추천 수: {item.recommendCount}</InfoRow>
            
            {/* <InfoRow>등록일: {new Date(item.createdAt).toLocaleDateString()}</InfoRow>
            <InfoRow>마감일: {new Date(item.expired).toLocaleDateString()}</InfoRow>
            <Tags>
              {item.tags.map((tag, idx) => (
                <Tag key={idx}>{tag}</Tag>
              ))}
            </Tags> */}
            
          </CardContent>
        </Card>
        
        ))}
      </CardList>
    </Container>
  );
};

export default Search;

// Styled Components

const Container = styled.div`
  
  padding: 0 15%;
`;

const Title = styled.h2`
  font-size: 28px;
  margin-bottom: 24px;
`;


const CategoryRow = styled.div`
  display: flex;
  overflow-x: auto;
  height: 50px;
  padding: 12px 20px;
  margin-bottom: 24px;
  align-items: center;
  justify-content: space-between;
`;

const CategoryItem = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 13px;
  
  color: #999;
  cursor: pointer;
  transition: all 0.2s ease;
  i {
    font-size: 22px;
    margin-bottom: 6px;
    display: flex;
    justify-content: center;
    align-items: center;
    transition: all 0.2s ease;
    
  }
    &:hover {
        color: #A66CFF;
        font-weight: bold;
        transition: all 0.2s ease;
      }

  &.active {
    color: #A66CFF;
    font-weight: bold;
    transition: all 0.2s ease;
    i {
      transition: all 0.2s ease;
      background: #A66CFF;
      color: white;
      border-radius: 50%;
      padding: 6px;
      width: 30px;
      height: 30px;
      display: flex;
      justify-content: center;
      align-items: center;
    }
  }
`;

const SelectedTagText = styled.p`
  font-size: 16px;
  margin-bottom: 12px;
  color: #555;
`;
const SearchForm = styled.div`
  display: flex;
  gap: 12px;
  margin-bottom: 24px;
  align-items: center;
  flex-wrap: wrap;
`;
const Select = styled.select`
  padding: 8px;
  margin-left: 6px;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 14px;
`;
const OrderTabWrapper = styled.div`
  position: relative;
  display: flex;
  background: #f2f2f2;
  border-radius: 20px;
  margin-bottom: 20px;
  overflow: hidden;
`;

const OrderSlider = styled.div`
  position: absolute;
  top: 0;
  left: 0;
  width: 25%; /* 버튼 개수만큼 나눠야 함: 4개면 25% */
  height: 100%;
  background: #A66CFF;
  border: 2px solid #3fa9f5;
  border-radius: 20px;
  transition: transform 0.3s ease;
  z-index: 0;
`;

const OrderButton = styled.button`
  flex: 1;
  padding: 12px;
  background: transparent;
  border: none;
  font-size: 14px;
  color: #999;
  font-weight: bold;
  cursor: pointer;
  white-space: nowrap;
  z-index: 1;
  position: relative;

  &.active {
    color: white;
  }
`;


const Input = styled.input`
  padding: 8px;
  margin-left: 6px;
  border: 1px solid #ccc;
  border-radius: 6px;
`;

const SearchButton = styled.button`
  padding: 8px 16px;
  background: #A66CFF;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;

  &:hover {
    background: #944dff;
  }
`;

const ErrorText = styled.h3`
  color: red;
`;

const CardList = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
`;

const Card = styled.div`
  display: flex;
  flex-direction: column;
  background: #fff;
  overflow: hidden;
`;

const Thumbnail = styled.img`
  height: 180px;
  cursor: pointer;
  border-radius: 12px;
  border : 1px solid #ddd;
  object-fit: cover;
  &:hover {
    box-shadow: 0 0 2px rgba(0, 0, 0, 0.15);
  }

  img {
    height: 180px;
    width: 100%;
    object-fit: cover;
    transition: transform 0.3s ease;
      ${Card}:hover & {
      transform: scale(1.05);
    }}
`;

const CardContent = styled.div`
  padding: 16px;
  display: flex;
  flex-direction: column;

  h3 {
    font-size: 18px;
    margin : 0 0;
    font-weight: bold;
  }
`;

const InfoRow = styled.div`
  font-size: 14px;
  color: #555;
  margin: 4px 0;
`;

const ProgressBarWrapper = styled.div`
  background: #eee;
  border-radius: 10px;
  height: 10px;
  margin: 8px 0;
  overflow: hidden;
`;

const ProgressBar = styled.div<{ percent: number }>`
  height: 100%;
  width: ${({ percent }) => percent}%;
  background: ${({ percent }) =>
    percent === 100 ? '#00C853' :
    percent >= 50 ? '#A66CFF' :
    '#FFAB00'};
  transition: width 0.3s ease;
`;

const Tags = styled.div`
  margin-top: 12px;
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
`;

const Tag = styled.span`
  background: #f2f2f2;
  padding: 4px 10px;
  font-size: 12px;
  border-radius: 6px;
  color: #555;
`;
