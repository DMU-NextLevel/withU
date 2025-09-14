import axios from 'axios';
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
  data: {
    projects: ProjectItem[];
    totalCount: number;
    pageCount: number;
    page: number;
  };
}

interface ProjectRequest {
  order?: string;
  tag?: string | number | null;
  page?: number;
  search?: string;
  desc?: boolean;
  pageCount?: number;
}


export const fetchProjectsFromServer = async (input: ProjectRequest): Promise<ProjectItem[]> => {
  const {
    order = 'RECOMMEND',
    tag,
    page = 0,
    search = '',
    desc = true,
    pageCount = 100000,
  } = input;

  const requestData = {
    order,
    tag: tag !== null && tag !== undefined && !isNaN(Number(tag))
      ? [Number(tag)]
      : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
    page,
    search: search.trim() !== '' ? search.trim() : null,
    desc,
    pageCount,
  };

  console.log('✅ 전달된 input:', input);
  console.log('📦 요청 보낼 데이터:', JSON.stringify(requestData, null, 2));

  const response = await api.post<ProjectResponse>('/public/project/all', requestData); // ✅ 고정된 경로 사용
  return response.data.data.projects;
};


// RECOMMEND : 추천수 기준
// COMPLETION : 참여 금액 / 목표 금액 * 100
// USER : 펀딩에 참여한 인원 기준
// CREATED : 생성일 기준
// EXPIRED : 만료일 기준