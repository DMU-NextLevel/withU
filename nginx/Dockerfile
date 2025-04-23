# Step 1: Nginx 기본 이미지 사용
FROM nginx:latest

# # Step 2: Nginx 설정 파일 복사
# COPY nginx/springboot.conf /etc/nginx/sites-available/springboot.conf

# # Step 3: Nginx 설정 활성화 (사이트 활성화)
# RUN mkdir -p /etc/nginx/sites-enabled && \
#     ln -s /etc/nginx/sites-available/springboot.conf /etc/nginx/sites-enabled/springboot.conf

# Step 6: 80, 443 포트 노출
EXPOSE 80 443

# Step 7: Nginx 실행
CMD ["nginx", "-g", "daemon off;"]
