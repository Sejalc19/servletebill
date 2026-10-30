# ⚡ Electricity Bill Calculator – Java Servlet

A responsive web-based **Electricity Bill Calculator** developed using Java Servlet, HTML, and CSS. The application calculates electricity bills based on the number of units consumed using a slab-wise tariff system.

## 📌 Assignment Details

| Field | Details |
|---|---|
| Subject | Web Technology |
| Assignment | Assignment – 3 |
| Name | Sejal Suni Chougule |
| Roll No. | 46 |
| PRN | 1252070056 |
| Batch | B2 |
| Technology | Java Servlet, HTML, CSS |

## 🎯 Project Objective

The objective of this project is to design and develop a responsive electricity bill calculator using Java Servlet with the assistance of an AI code generation tool.

The application accepts consumer details and electricity consumption units as input and calculates the total electricity bill according to predefined tariff slabs.

## ✨ Features

- Responsive and user-friendly interface.
- Accepts consumer details and electricity units.
- Calculates electricity bills using slab-wise rates.
- Displays the calculated bill amount.
- Server-side processing using Java Servlet.
- HTML and CSS for frontend design.

## 💰 Electricity Tariff Structure

The electricity bill is calculated using the following tariff rates:

| Units Consumed | Rate per Unit |
|---|---:|
| First 50 units | ₹3.50 |
| Next 100 units (51–150) | ₹4.00 |
| Next 100 units (151–250) | ₹5.20 |
| Above 250 units | ₹6.50 |

### Calculation Logic

The bill is calculated progressively based on the electricity consumption slabs.

For example, if a consumer uses 200 units:

- First 50 units = 50 × 3.50 = ₹175
- Next 100 units = 100 × 4.00 = ₹400
- Remaining 50 units = 50 × 5.20 = ₹260

**Total Electricity Bill = ₹835**

## 🛠️ Technologies Used

- **Java:** Backend programming.
- **Java Servlet:** Server-side processing and bill calculation.
- **HTML5:** Structure of the web application.
- **CSS3:** Styling and responsive design.
- **Apache Tomcat:** Servlet deployment and execution.
- **AI Code Generation Tool:** Assistance in application development.

## ⚙️ How to Run the Project

1. Clone the repository:

   ```bash
   git clone https://github.com/Sejalc19/servletebill.git
   ```

2. Open the project in an IDE such as Eclipse or IntelliJ IDEA.

3. Configure Apache Tomcat Server.

4. Add the project to the Tomcat server.

5. Build and deploy the application.

6. Start the Tomcat server.

7. Open the application in your browser using the deployed project URL.

## 📸 Output

The application provides an interface where users can enter consumer details and electricity units consumed. After submitting the details, the Servlet processes the input and displays the calculated electricity bill.

<!-- Add your project output screenshot below -->
<!-- ![Electricity Bill Calculator Output](screenshots/output.png) -->

## 🔗 GitHub Repository

[Click here to view the Electricity Bill Calculator Project](https://github.com/Sejalc19/servletebill)

## 👩‍💻 Developed By

**Sejal Suni Chougule**

- Roll No: 46
- PRN: 1252070056
- Batch: B2
- Subject: Web Technology

---

⭐ If you find this project useful, feel free to explore the repository!
