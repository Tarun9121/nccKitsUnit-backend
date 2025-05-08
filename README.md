Here's the complete `README.md` file in one code snippet:

````markdown
# NCC Cadet Management System - Backend

The **NCC Cadet Management System** is a web application developed by 4th-year students of **KITs College**, under the guidance of **Tarun Swaroop**. This platform helps cadets manage their stocks, register for camps, and receive notifications. The ANOs can view stock statuses, accept/reject registrations, and send announcements to cadets.

## Features

- **Cadet Stock Management**: 
  - Cadets can view and manage their assigned stocks.
  - Stocks include both returnable and pending items.
  
- **Camp Registration**:
  - Cadets can register for upcoming camps.
  - The ANO can accept or reject cadet registrations for camps.

- **Email Notifications**:
  - Temporary registrations automatically receive their **Regimental ID** via email.
  - The ANO can send **announcements** to temporary registrants for updates.

## Technologies Used

- **Spring Boot**: Backend framework for building RESTful APIs.
- **JPA (Java Persistence API)**: For database operations.
- **MySQL**: Database to store cadet, camp, and stock information.
- **JavaMailSender**: For sending email notifications.
- **Dotenv**: For loading environment variables like database credentials and email configurations.

## Prerequisites

Before you run the application, make sure you have the following installed:

- **Java 17+** (for Spring Boot application)
- **MySQL** (or any relational database of your choice)
- **Maven** (for building the project)

## Setup Instructions

### 1. Clone the repository
```bash
git clone https://github.com/yourusername/nccKitsUnit-backend.git
cd nccKitsUnit-backend
````

### 2. Set up the MySQL database

* Create a database named `ncc` in your MySQL instance.
* Update the `application.properties` file with your database credentials.

### 3. Set up environment variables

* Create a `.env` file in the root directory and add the following variables:

```env
DB_URL=jdbc:mysql://localhost:3306/ncc
DB_USERNAME=root
DB_PASSWORD=root
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_email_password
```

### 4. Build the application

You can use Maven to build the project:

```bash
mvn clean install
```

### 5. Run the application

Once the build is complete, run the application using:

```bash
mvn spring-boot:run
```

The backend will be available at `http://localhost:8080`.

## API Endpoints

### Cadet Management

* `POST /api/cadets/login`: Login for cadets.
* `GET /api/cadets/{cadetId}/stocks`: Get all stocks assigned to a cadet.

### ANO Management

* `POST /ano/login`: Login for ANOs.
* `POST /api/camps`: Add a new camp.
* `GET /api/camps`: Get all upcoming camps.
* `GET /api/issued-stocks/pending-cadets`: Get a list of cadets with pending stock returns.

### Email Notifications

* **Temporary Registrations**: Emails are sent to cadets with their Regimental ID upon registration.
* **Announcements**: The ANO can send email notifications to cadets.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

* Developed by 4th-year students of **KITs College** under the guidance of **Tarun Swaroop**.
* Special thanks to all the contributors who helped in building the project.

