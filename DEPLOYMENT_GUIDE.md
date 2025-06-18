# Hướng dẫn Deploy Full-Stack Application

## 1. Deploy Backend lên Glitch

### Bước 1: Chuẩn bị Repository
1. Đảm bảo code đã được push lên GitHub
2. Kiểm tra các file đã được tạo:
   - `glitch.json`
   - `package.json`
   - `.gitignore`
   - `pom.xml` (đã cập nhật với Java 11 và maven-compiler-plugin 3.8.1)
   - `src/main/resources/application.properties` (đã cập nhật)

### Bước 2: Tạo Glitch Project
1. Truy cập [glitch.com](https://glitch.com) và đăng nhập
2. Click "New Project" → "Import from GitHub"
3. Nhập URL repository GitHub của bạn
4. Đợi Glitch import project

### Bước 3: Cấu hình Environment Variables
Trong Glitch dashboard:
1. Click vào file `.env` (tạo mới nếu chưa có)
2. Thêm các biến môi trường:
```
PORT=3000
ALLOWED_ORIGINS=https://your-frontend-domain.vercel.app,http://localhost:3000
```

### Bước 4: Deploy và lấy URL
1. Glitch sẽ tự động build và deploy
2. Đợi quá trình hoàn tất (có thể mất 2-3 phút)
3. Lưu lại URL backend: `https://your-project-name.glitch.me`

## 2. Deploy Frontend lên Vercel

### Bước 1: Chuẩn bị Frontend
1. Đảm bảo đang ở thư mục frontend: `src/main/frontend/`
2. Kiểm tra `package.json` có script build:
   ```json
   {
     "scripts": {
       "build": "next build",
       "dev": "next dev"
     }
   }
   ```

### Bước 2: Tạo Vercel Project
1. Truy cập [vercel.com](https://vercel.com) và đăng nhập
2. Click "New Project"
3. Import GitHub repository
4. Cấu hình:
   - **Framework Preset**: `Next.js`
   - **Root Directory**: `src/main/frontend`
   - **Build Command**: `npm run build`
   - **Output Directory**: `.next`
   - **Install Command**: `npm install`

### Bước 3: Cấu hình Environment Variables
Trong Vercel dashboard, thêm:
```
NEXT_PUBLIC_API_BASE_URL=https://your-project-name.glitch.me
```

### Bước 4: Deploy
1. Click "Deploy"
2. Vercel sẽ build và deploy frontend
3. Lưu lại URL frontend: `https://your-frontend-name.vercel.app`

## 3. Cập nhật CORS và Environment Variables

### A. Cập nhật Backend CORS
Sau khi có URL frontend, cập nhật `.env` trong Glitch:
```
ALLOWED_ORIGINS=https://your-frontend-name.vercel.app,http://localhost:3000
```

### B. Restart Backend
1. Vào Glitch dashboard
2. Click "Tools" → "Terminal"
3. Chạy lệnh: `refresh` hoặc restart project

## 4. Kiểm tra và Test

### A. Test Backend
```bash
# Test health check
curl https://your-project-name.glitch.me/api/health

# Test models endpoint
curl https://your-project-name.glitch.me/api/models
```

### B. Test Frontend
1. Truy cập URL frontend
2. Nhập API key Gemini
3. Test chat functionality
4. Kiểm tra console có lỗi CORS không

## 5. Troubleshooting

### Lỗi thường gặp:

1. **Maven Compiler Error**:
   - Lỗi: "invalid flag: --release"
   - Giải pháp: Đã cập nhật `pom.xml` với Java 11 và maven-compiler-plugin 3.8.1
   - Đã chuyển đổi Java records thành classes thông thường

2. **Maven Version Error**:
   - Lỗi: "requires Maven version 3.6.3"
   - Giải pháp: Đã cập nhật `pom.xml` với maven-compiler-plugin 3.8.1
   - Sử dụng Maven wrapper: `./mvnw` thay vì `mvn`

3. **CORS Error**:
   - Kiểm tra `ALLOWED_ORIGINS` trong Glitch `.env`
   - Đảm bảo URL frontend chính xác
   - Restart Glitch project sau khi thay đổi

4. **Build Error**:
   - Kiểm tra `pom.xml` dependencies
   - Kiểm tra Java version compatibility (Java 11)
   - Xem logs trong Glitch console

5. **API Connection Error**:
   - Kiểm tra `NEXT_PUBLIC_API_BASE_URL` trong Vercel
   - Đảm bảo backend URL đúng
   - Test backend endpoints trực tiếp

6. **Glitch Sleep**:
   - Glitch có thể sleep sau 5 phút không hoạt động
   - Có thể cần wake up bằng cách truy cập URL

7. **Maven Wrapper Permission**:
   - Nếu gặp lỗi permission với `./mvnw`
   - Chạy: `chmod +x mvnw` trong Glitch terminal

## 6. Glitch Features

### A. Auto-reload
- Glitch tự động reload khi có thay đổi code
- Real-time collaboration
- Built-in editor

### B. Environment Variables
- File `.env` cho environment variables
- Secure storage
- Easy management

### C. Logs và Monitoring
- Real-time logs
- Error tracking
- Performance monitoring

## 7. Glitch Limitations

### Free Tier:
- 1000 requests/hour
- Sleep after 5 minutes inactivity
- 200MB storage
- Public projects

### Pro Features:
- Private projects
- More requests
- No sleep
- Custom domains

## 8. Security Considerations

1. **API Keys**: Không commit API keys vào code
2. **Environment Variables**: Sử dụng `.env` file trong Glitch
3. **CORS**: Chỉ allow specific origins
4. **HTTPS**: Glitch hỗ trợ HTTPS tự động

## 9. Cost Optimization

### Glitch Free Tier:
- 1000 requests/hour
- Sleep after 5 minutes
- Public projects only

### Vercel Free Tier:
- 100GB bandwidth/month
- 100GB storage
- Unlimited deployments

## 10. Backup và Recovery

1. **Code**: GitHub repository là backup chính
2. **Environment Variables**: Lưu backup các config
3. **Glitch**: Auto-save và version control
4. **Documentation**: Lưu lại các cấu hình quan trọng 