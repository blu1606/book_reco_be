# Hello Flash: Hệ thống Gợi Ý Sách Thông Minh

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen)
![Next.js](https://img.shields.io/badge/Next.js-14-black)
![React](https://img.shields.io/badge/React-18-blue)
![TypeScript](https://img.shields.io/badge/TypeScript-5-blue)
![Java](https://img.shields.io/badge/Java-23-orange)

## Tổng Quan Dự Án

**Hello Flash** là một hệ thống gợi ý sách thông minh được xây dựng với kiến trúc full-stack hiện đại. Dự án kết hợp sức mạnh của Google Gemini AI để phân tích sở thích đọc sách và đưa ra những gợi ý phù hợp cho người dùng.

### Tính Năng Chính

- 🤖 **AI Chat Thông Minh**: Tương tác với AI để nhận gợi ý sách cá nhân hóa
- 📚 **Gợi Ý Sách**: Phân tích sở thích và đưa ra danh sách sách phù hợp
- 💬 **Chat Session Management**: Quản lý nhiều phiên chat với localStorage
- 🔑 **User API Key Management**: Người dùng tự nhập và quản lý API key
- 🎨 **Giao Diện Hiện Đại**: UI/UX đẹp mắt với Next.js và Tailwind CSS
- 📱 **Responsive Design**: Tương thích với mọi thiết bị
- 🔄 **Real-time**: Cập nhật gợi ý theo thời gian thực

## Kiến Trúc Hệ Thống

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.4.3
- **AI Integration**: Google Gemini API với OpenAI-compatible endpoints
- **Session Management**: Quản lý phiên chat với in-memory storage
- **RESTful API**: Endpoints cho chat và gợi ý sách
- **API Key Validation**: Xác thực API key từ request header
- **Package Structure**: Tổ chức code theo mô hình MVC

### Frontend (Next.js)
- **Framework**: Next.js 14 với App Router
- **UI Library**: React 18 + TypeScript
- **Styling**: Tailwind CSS + shadcn/ui components
- **Markdown Rendering**: Hiển thị phản hồi AI dưới dạng markdown
- **Session Management**: Quản lý nhiều phiên chat với localStorage
- **API Key Management**: Giao diện nhập và lưu trữ API key
- **Responsive**: Tối ưu cho mobile và desktop

## Yêu Cầu Hệ Thống

- **Java**: 23+
- **Node.js**: 18+
- **Maven**: 3.8+
- **Gemini API Key**: Từ Google AI Studio
- **Kết nối Internet**: Để truy cập Gemini API

## Cài Đặt và Chạy Dự Án

### 1. Lấy Gemini API Key

Trước tiên, bạn cần lấy Gemini API key:

1. Truy cập [Google AI Studio](https://ai.google.dev/)
2. Tạo tài khoản nếu chưa có
3. Vào phần API keys
4. Tạo API key mới
5. Sao chép API key (bắt đầu bằng "AIza")

### 2. Chạy Backend

```bash
# Di chuyển vào thư mục gốc
cd "E:\NetBeansProjects\hello-flash - Copy"

# Chạy Spring Boot application
mvn spring-boot:run
```

Backend sẽ chạy tại: `http://localhost:8080`

### 3. Chạy Frontend

Mở terminal mới và chạy:

```bash
# Di chuyển vào thư mục frontend
cd src/main/frontend

# Cài đặt dependencies
npm install

# Chạy development server
npm run dev
```

Frontend sẽ chạy tại: `http://localhost:3000`

### 4. Cấu Hình API Key

1. Mở ứng dụng tại `http://localhost:3000`
2. Click vào sidebar (biểu tượng menu)
3. Trong phần "API Configuration", nhập API key của bạn
4. Click "Lưu API Key"
5. Bắt đầu sử dụng ứng dụng!

## Cấu Trúc Dự Án

```
hello-flash/
├── src/
│   ├── main/
│   │   ├── java/backend/
│   │   │   ├── Application.java              # Main class
│   │   │   ├── config/
│   │   │   │   ├── WebConfig.java            # CORS & RestTemplate config
│   │   │   │   └── GlobalExceptionHandler.java # Global error handling
│   │   │   ├── controller/
│   │   │   │   ├── BookRecommendationController.java
│   │   │   │   └── GeminiModelController.java
│   │   │   ├── service/
│   │   │   │   └── BookRecommendationService.java
│   │   │   ├── model/
│   │   │   │   ├── ChatRequest.java
│   │   │   │   ├── ChatResponse.java
│   │   │   │   ├── GeminiModel.java
│   │   │   │   └── ModelListResponse.java
│   │   │   └── session/
│   │   │       └── ChatSessionManager.java
│   │   ├── frontend/                         # Next.js app
│   │   │   ├── app/
│   │   │   │   ├── api/recommend/route.ts    # API proxy
│   │   │   │   ├── layout.tsx
│   │   │   │   └── page.tsx                  # Main chat interface
│   │   │   ├── components/
│   │   │   │   ├── app-sidebar.tsx           # Session & API key management
│   │   │   │   ├── MarkdownRenderer.tsx
│   │   │   │   └── ui/                       # shadcn/ui components
│   │   │   └── ...
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml                                   # Maven configuration
└── README.md
```

## API Endpoints

### Backend Endpoints

- `GET /health` - Health check endpoint
- `POST /api/books/recommend` - Gửi tin nhắn chat và nhận phản hồi từ AI
- `GET /models` - Lấy danh sách các model Gemini có sẵn

### Request/Response Format

#### POST /api/books/recommend
```json
// Request Headers
Authorization: Bearer YOUR_GEMINI_API_KEY

// Request Body
{
  "prompt": "Gợi ý sách trinh thám hay",
  "sessionChatId": "uuid-session-id"
}

// Response
{
  "recommendation": "Markdown formatted book recommendations..."
}
```

### Frontend API Routes

- `POST /api/recommend` - Proxy để gọi backend API

## Tính Năng Nổi Bật

### 1. AI Chat Thông Minh
- Tương tác tự nhiên với AI bằng tiếng Việt
- Phản hồi được format dưới dạng markdown
- Lưu trữ lịch sử trò chuyện theo session

### 2. Session Management
- Tạo nhiều phiên chat khác nhau
- Lưu trữ lịch sử trò chuyện trong localStorage
- Chuyển đổi giữa các phiên chat
- Xóa phiên chat không cần thiết

### 3. API Key Management
- Người dùng tự nhập API key
- Lưu trữ an toàn trong localStorage
- Validation API key format
- Giao diện ẩn/hiện API key

### 4. Gợi Ý Sách Cá Nhân Hóa
- Phân tích sở thích đọc sách của người dùng
- Đưa ra danh sách sách phù hợp với từng cá nhân
- Giải thích lý do gợi ý

### 5. Giao Diện Người Dùng
- Thiết kế hiện đại với sidebar navigation
- Responsive design cho mọi thiết bị
- Loading states và error handling
- Markdown rendering cho phản hồi AI

## Công Nghệ Sử Dụng

### Backend
- **Spring Boot 3.4.3**: Framework chính
- **Spring Web**: RESTful web services
- **RestTemplate**: HTTP client cho Gemini API
- **Maven**: Dependency management

### Frontend
- **Next.js 14**: React framework
- **TypeScript**: Type safety
- **Tailwind CSS**: Utility-first CSS framework
- **shadcn/ui**: Component library
- **react-markdown**: Markdown rendering
- **uuid**: Session ID generation

### AI & APIs
- **Google Gemini**: AI model cho gợi ý sách
- **Gemini API**: OpenAI-compatible endpoints

## Phát Triển

### Thêm Tính Năng Mới

1. **Backend**: Thêm controller/service trong package tương ứng
2. **Frontend**: Tạo component mới trong `src/main/frontend/components/`
3. **API**: Cập nhật endpoints và types

### Cấu Hình Development

```bash
# Backend development
mvn spring-boot:run

# Frontend development
cd src/main/frontend
npm run dev

# Build production
mvn clean package
cd src/main/frontend
npm run build
```

## Troubleshooting

### Lỗi API Key
- Đảm bảo API key bắt đầu bằng "AIza"
- Kiểm tra kết nối internet
- Xác nhận API key có quyền truy cập Gemini API

### Lỗi Kết Nối
- Kiểm tra backend đang chạy tại port 8080
- Kiểm tra frontend đang chạy tại port 3000
- Xem logs trong console để debug

### Lỗi Session
- Xóa localStorage và thử lại
- Tạo session mới
- Kiểm tra browser compatibility

## Đóng Góp

Dự án này được phát triển để demo khả năng tích hợp AI vào ứng dụng web. Mọi đóng góp đều được chào đón!

## License

MIT License - Xem file LICENSE để biết thêm chi tiết.