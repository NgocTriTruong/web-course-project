# VinaFeed - Animal Feed E-Commerce Platform

**VINAFEED** is a web-based platform designed to connect livestock farmers and animal feed distributors through a clean, user-friendly interface. This project was developed as part of the **"Web Programming"** course at school, showcasing a fully functional e-commerce system for animal feed sales.

🌐 **Live Website:** [https://animalsfeeds.online](https://animalsfeeds.online/home)

## ✨ Main Features
📌 Note: Only key e-commerce features are listed below. Explore the live website for additional functionalities!

### 👨‍🌾 User Features:
- Browse and search animal feed products (supports voice and text search)
- Add to cart and checkout with VNPAY payment integration
- Integrated Dialogflow chatbot for customer support
- Auto-calculated shipping fees via GHN API
- Create, cancel, and track orders with GHN
- View order history and track order status
- Login with Google or Facebook accounts
- reCAPTCHA for secure authentication

  ### 🛠️ Admin Features:
- Full CRUD operations for product management
- Order management: confirm, cancel, and track orders
- Shipping order management via GHN API
- View statistics: revenue, orders, and user insights
- User role management (user, admin, super admin)
- Action logging for all significant activities (view, add, edit, delete)

  ### 🔐 Security & System

- **Multi-level role-based access:**
  - 👤 Regular users: Access to shopping features
  - 👮 Admin Level 1: Can manage specific modules (e.g., products only)
  - 👑 Super Admin: Full access + can downgrade other admins
- **Logging system**: Records all significant user and admin actions
- **Error handling**: Friendly user feedback on failures or exceptions

## 🛠️ Technologies Used
- Java (JSP/Servlet)
- HTML, CSS, JavaScript
- Apache Tomcat
- MySQL
- OAuth 2.0 (Google Sign-In)
- IntelliJ IDEA, XAMPP, Navicat
- APIs: VNPAY, GHN, Dialogflow, reCAPTCHA
- Deployment: Docker container hosted on Render
  
## ⚠️ Deployment Notes

The website is currently deployed on a **free hosting service**, which may cause:
- Slower response times
- Occasional lag during peak usage
- Limited server resources (memory, CPU)
