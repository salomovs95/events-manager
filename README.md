<div align="center">
  <br />

  <a href="#" target="_blank">
    <img src="https://github.com/user-attachments/assets/406b1464-b976-49a3-a2f7-e1c483af9f5c" alt="Project Banner"/>
  </a>

  <br />
  <br />
  <br />

  <div>
    <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=fff&style=for-the-badge" alt="Java" />
    <img src="https://img.shields.io/badge/MySQL-316192?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" />
    <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=fff&style=for-the-badge" alt="Docker" />
  </div>
<br/><br/>
 
  <h1 align="center">Events Manager</h1>

   <div align="center">
     An app designed to manage Events and Subscriptions.
   </div>
</div>

## 📋 <a name="table">Sumary</a>

1. 🚀 [Introduction](#introduction)
2. ⚙️ [Tech Stack](#tech-stack)
3. 🔋 [Features](#features)
4. 💻 [Quick Start](#quick-start)
5. 💾 [Environment Variables](#envs)
6. 📅 [Releases](#versions)
7. 🤝 [Contributing](#contributing)
8. 👥 [Authors](#authors)




## <a name="introduction">🚀 Introduction</a>

&nbsp;Developed to bring joy of management Events, though to be the ultimate management system.


## <a name="features">🔋 Features</a>

- Easy Event creation and subscription.
- Easy deployment, with just a few touchs.
- Friendly UI. **(soon)**


## <a name="tech-stack">⚙️ Tech Stack</a>

- [Java 17](https://www.java.com/en/)
- [Maven](https://maven.apache.org)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [JUnit5](https://junit.org/junit5/)
- [Spring JPA](https://spring.io/projects/spring-data-jpa)
- [MySQL](https://mysql.com)
- [Docker](https://www.docker.com/)
- [SpringDoc (Swagger + OpenAPI)](https://springdoc.org/)


## <a name="quick-start">💻 Quick Start</a>

Follow these steps to set up the project locally on your machine.

**00 - Prerequisites**

To use this project you must have previously installed the following packages:

- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/)
- MySQL Server (if not confident with docker)


**01 - Cloning the Repository**

```bash
git clone https://github.com/salomovs95/events-manager && cd my-events-manager
```

**02 - Running the Project**

```bash
# Deploys datanase locally. Only if not using mysql-server yourself
docker compose up -d
```
```bash
./mvnw clean -Dmaven.skip.tests spring-boot:run
```

Now one can start making http request to [http://localhost:8080](http://localhost:8080), accordingly to _Controllers_ enpoints, using whatever Rest Client one may choose.

## <a name="envs">💾 Environment Variables</a>

<details>
<summary><code>Environment Variable To Be Exposed (inside ${})</code></summary>

```yml
# Database Connection - PRODUCTION ONLY
spring:
  datasource:
    url: jdbc:mysql://${DB_URL}
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```
</details>

## <a name="versions">📅 Release History</a>

* 0.0.1  - The initial release, NLW Day 01.<br />
  FEAT:
    * Project creation
    * Added Event creation support
    * Initial data validation
    * Initial testing coverage
* 0.0.2  - Events subscription, NLW Day 02.<br />
  FEAT:
    * Added Event subscription with (non-)existing user support
    * Exception handling support
    * Improves data validation
    * Improves testing coverage
* 0.0.3 - Rankings, NLW Day 03.<br/>
  FEAT:
    * Added Event ranking support
    * Improves data validation
    * Improves exception handling
    * Improves testing coverage
* 0.0.4 - Ranking between Events.
  FEAT:
    * Adds support for Ranking between Events.
    * Improves API documentation with SpringDoc/OpenAPI (Available at [/swagger-ui.html route](.))
    * Improves exception handling
    * Improves data validation
    * Other minor bugs fixes


## <a name="contributing">🤝 Contributing</a>

Contributions, issues, and feature requests are welcome!

1. Fork it ([https://github.com/salomovs95/events-manager](https://github.com/salomovs95/events-manager))
2. Create your feature branch (`git checkout -b feature/fooBar`)
3. Commit your changes (`git commit -am 'Add some fooBar'`)
4. Push to the branch (`git push origin feature/fooBar`)
5. Create a new Pull Request


## <a name="authors">👥 Authors</a>

<table style="border-collapse: collapse; table-layout: auto text-align: left;">

  <tbody>
    <tr>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <img src="https://avatars.githubusercontent.com/u/170432574?v=4" width="60" style="border-radius: 50%; display: block; margin: 0 auto;">
      </td>
      <td style="padding: 10px; border: 1px solid #ddd;">Salomao Souza</td>
      <td style="padding: 10px; border: 1px solid #ddd;">
        <a href="https://linkedin.com/in/salomovs95" target="_blank">LinkedIn</a> |
        <a href="https://github.com/salomovs95" target="_blank">GitHub</a>
      </td>
    </tr>
  </tbody>
</table>

#NLWConnect2025 💜
