# 🎬 Movie Explorer App

## 📌 Overview
Movie Explorer is an immersive and dynamic Android application built with **Jetpack Compose** and powered by **The Movie Database (TMDb) API** and **Firebase**. It provides users with an engaging experience to explore trending and popular movies, mark favorites, and receive notifications of Upcoming Movies. The app includes dedicated screens to inform users about network issues, ensuring clarity when movies are not loading due to no internet or slow connection.

---
## ✨ Features
- **User Authentication** 🔐
  - Firebase Authentication with email verification
  - "Remember Me" feature for a smooth login experience

- **Home Screen** 🏠
  - Intuitive navigation with a drawer menu

- **Movie Details Section** 🎥
  - Multi-page movie information including reviews, trailers, and cast details
  - Engaging UI with high-quality visuals

- **Favorites Section** ❤️
  - Save and manage your favorite movies effortlessly

- **Notifications** 🔔
  - Stay updated with Upcoming movie notifications

- **Network Handling** 🌐
  - Dedicated screens for **no internet** and **slow connection** scenarios
  - Clearly informs users about connectivity issues

---
## 📂 App Structure
```
📂 NSAAI
│
│-- 📂 Authentication
│
│-- 📂 AuthScreens
|
│-- 📂 CastByApi
|
│-- 📂 data
|
│-- 📂 datafromapi
│
│-- 📂 DetailOfMovieApi
│
│-- 📂 HomeScreenContent
│
│-- 📂 Navigation
│
│-- 📂 Screens
│
│-- 📂 TopBar
│
│-- 📂 TrailerFromApi
│
│-- 📂 DetailOfMovieApi
│
│-- 📂 ViewModels
```
---
## 📸 Screenshots
| App Start Screen | Sign Up Screen | Sign In Screen |
|------------------|---------------|---------------|
| ![Start](https://github.com/user-attachments/assets/05b62133-854a-4d1f-b3f8-4d4cc3b176ba) | ![Sign Up](https://github.com/user-attachments/assets/e08b6005-cdde-4738-a24f-5bff61f35c31) | ![Sign In](https://github.com/user-attachments/assets/10a2b346-b9f3-4abb-9bf1-a839d531a3fc) |

| Home Screen | Drawer |
|-------------|--------|
| ![Home](https://github.com/user-attachments/assets/dbb85a0b-f2a3-42cc-93c1-b2d0ea651bd4) | ![Drawer](https://github.com/user-attachments/assets/80a3dbc1-4c1b-414c-9d6e-b2a2b5195f40) |

| Movie Screen | Popular Movies | Trending Movies |
|-------------|---------------|-----------------|
| ![Movie](https://github.com/user-attachments/assets/945ee117-5817-4a9c-a99a-78d5b6fe0535) | ![Popular](https://github.com/user-attachments/assets/79548cf1-820c-4e55-a623-9d8df47241a4) | ![Trending](https://github.com/user-attachments/assets/4dfb796d-9441-4c63-aafe-5003a4d4c32d) |

| Favorites | Upcoming |
|----------|---------|
| ![Favorites](https://github.com/user-attachments/assets/d7c020e5-c14a-4f00-8e02-74d1491c0c10) | ![Upcoming](https://github.com/user-attachments/assets/4fd05f5c-89d7-4906-815a-0a3e5f0a77d1) |

| About Movie | More Details | Cast & Crew | Trailer |
|-------------|-------------|-------------|---------|
| ![About Movie](https://github.com/user-attachments/assets/5008c100-e36e-465f-8f03-4abbd3bc94f8) | ![More Details](https://github.com/user-attachments/assets/615ea81c-3628-4695-8832-8ca6676eccc4) | ![Cast & Crew](https://github.com/user-attachments/assets/a70bf58d-8de6-4455-81e1-acf433a4c2a3) | ![Reviews](https://github.com/user-attachments/assets/9ae391db-6147-4d30-8cf2-bc60edbc58e8) |

| No Internet | Slow Connection |
|-------------|----------------|
| ![No Internet](https://github.com/user-attachments/assets/dc2d86c0-ec64-46a6-bcfe-09bee01a4282) | ![Slow Connection](https://github.com/user-attachments/assets/b135f9bf-75e4-474e-b41f-0af086b9026b) |

---
## 🚀 Getting Started
### Prerequisites
- Android Studio (Latest Version)
- Firebase Account (For Authentication)
- TMDb API Key

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/Satyam12singh/NSAAI_.git
   ```
2. Open in **Android Studio**
3. Configure **Firebase Authentication**
4. Add your **TMDb API Key** to `local.properties`:
   ```properties
     googleclientid= your Google Client ID
     api_key_normal= 99e336afe......ad7ba8c2b706a18
     API_KEY=eyJhbGciOiJIUzI1NiJ9.eyJhdWQi....mUzOTUwNmU1YWFkN2JhOGMyYjcwNm................iI2NzcyNDBjNjYzZjkwZjhmNjY5MjcwM..........BpX3JlYWQiXSwidmVyc2lvbiI6MX0._A46VjdrNeKLkVBTvf4aDuJ8gjNIu9oTQEKc2BX_KQc
   ```
5. Run the app on an emulator or a physical device

---
## 🛠 Tech Stack
- **Kotlin** 🟡 (Primary Language)
- **Jetpack Compose** 🏗️ (UI Framework)
- **Firebase Authentication** 🔥 (User Management)
- **Retrofit** 🌍 (API Integration)
- **FireBase Database** 🗄️ (Favourite Movie Storage) 
- **Coroutines & Flow** ⏳ (Asynchronous Programming)

---
## 🤝 Contribution
Contributions are welcome! Feel free to fork this repository and submit a pull request.

---
## 📄 License
This project is licensed under the **APACHE 2.0 License**.

---
## 📧 Contact
For any queries, reach out at **satyamsingh190235@gmail.com** or open an issue on GitHub.

🚀 **Explore the world of movies like never before!** 🎥🍿

