
# Pharmacy Guard Management System

### Description

A Spring MVC Thymeleaf project for managing pharmacies on duty. This application allows tracking information about pharmacies on duty, including details about the pharmacies and scheduled duties.

### Technologies Used

- Java
- Spring MVC
- Thymeleaf
- HTML
- CSS
- JavaScript
- MySQL
- Dockerization

### Dockerization

The project supports Dockerization to facilitate deployment. Follow these steps to run the application within a Docker container.

1. **Clone the Repository**
   ```bash
   git clone https://github.com/adnan-khadija/PharmacieGarde.git
   ```

2. **Navigate to the Project Directory**
   ```bash
   cd 'project-directory-name'
   ```

3. **Build the Docker Image**
   ```bash
   docker-compose build
   ```

   ![image](https://github.com/sana7867/Gestion-PharmacieGarde/assets/147515885/d2608096-d76b-4378-91de-3448b6f952e9)

   ## Pharmacy Guard Management System

### Description

A Spring MVC Thymeleaf project for managing pharmacies on duty. This application allows tracking information about pharmacies on duty, including details about the pharmacies and scheduled duties.

### Technologies Used

- Java
- Spring MVC
- Thymeleaf
- HTML
- CSS
- JavaScript
- MySQL
- Dockerization

### Installation - Local Mode

If you prefer to run the application outside a Docker container, follow these steps:

1. **Configure the Database**
   - Create a MySQL database named `pg`.
   - Update the database access information in the `application.properties` file.

2. **Build and Run the Application**
   - The application will be accessible at: [http://localhost:8089](http://localhost:8089).

### Features

1. **List of Pharmacies with Details**
   - View a list of pharmacies along with their details.

2. **Scheduled Duty Management**
   - Manage scheduled duties for each pharmacy.

3. **User-Friendly Interface with Thymeleaf**
   - Utilize Thymeleaf for a user-friendly interface.

4. **Interactive Data Display with DataTables**
   - Use DataTables to display data interactively.

### Test with SonarQube

Run tests and analyze the code using SonarQube for quality assurance.




5. **Run the Docker Container**
   ```bash
   docker-compose up
   ```

The application will be accessible at: [http://localhost:8089](http://localhost:8089).
