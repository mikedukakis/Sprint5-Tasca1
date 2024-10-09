📄 Description - Project Statement

This project consists of creating and managing a Blackjack game, incorporating game logic for a player versus a dealer. It includes functionality for dealing cards, player actions, and tracking wins and losses, along with persisting game data in a MongoDB database and player statistics in a MySQL database.

💻 Technologies Used

    - Java
    - Spring Boot
    - MongoDB
    - MySQL
    - Reactive programming with Project Reactor
    - Maven


📋 Requirements

    - Java 11 or higher
    - Maven 3.6 or higher
    - MongoDB v4.4+
    - MySQL v8.0+
    - Spring Boot v2.6+
    - Internet connection for dependency downloading


🛠️ Installation

    1. Clone this repository:

    `git clone https://github.com/mikedukakis/Sprint5-Tasca1.git`

    2. Navigate to the project directory:

    `cd blackjack-game`

    3. Install dependencies using Maven:

    `mvn clean install`

    4. Configure the application:
        - Update your MongoDB connection details in `application.properties` under the `src/main/resources` directory.
        - Set up your MySQL connection details in the same file.

▶️ Execution

    1. Make sure MongoDB and MySQL are running on your local machine or on a configured server.
    
    2. Start the application:

    `mvn spring-boot:run`

    3.  The application will be available at `http://localhost:8080`

🌐 Deployment

    1.  Prepare the production environment.
    2.  Upload the project files to the production server.
    3.  Configure the server to point to the production databases for MongoDB and MySQL.
    4.  Ensure environment variables are properly set for MongoDB and MySQL configurations.

🤝 Contributions

Contributions are welcome! Please follow the steps below to contribute:

    1.  Fork the repository.
    
    2.  Create a new branch:

      `git checkout -b feature/NewFeature`

    3.  Make your changes and commit them:

      `git commit -m 'Add New Feature'`

    4.  Push the changes to your branch:

      `git push origin feature/NewFeature`

    5.  Create a pull request.