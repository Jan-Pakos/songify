## Songify

A robust REST API engineered as a **Modular Monolith** using **Java 21**. It utilizes **Virtual Threads** to handle high-throughput operations efficiently while enforcing strict domain isolation. The application is secured via **Spring Security** and **OAuth2**, backed by **Hibernate** and a **Dockerized PostgreSQL** instance. **The entire testing and build lifecycle is automated using GitHub Actions.**

---

### Key Highlights

* **Architecture:** Designed as a **Modular Monolith** to combine the deployment simplicity of a monolith with the maintainability of microservices (future-proofed for splitting). 
* **Performance:** Leverages **Java 21 Virtual Threads (Project Loom)** for scalable, high-throughput I/O operations without resource contention.
* **Security:** Implements industry-standard security via **Spring Security** and the **OAuth2** framework.
* **Data Persistence:** Uses **Hibernate** for robust ORM, backed by a persistent **Dockerized PostgreSQL** database instance.
* **Continuous Integration (CI):** Build, dependencies, and testing are **automated via GitHub Actions**, ensuring immediate quality feedback on every commit. 
* **Quality Assurance:** Validated through rigorous **Unit** and **Integration testing** to ensure high code quality and functional reliability.
* **Documentation:** Fully documented via **Swagger**, alongside detailed technical, user, and security requirement specifications.
