# 🎓 Exam Prep Lab

> A polished, practical-first workspace for **Maven → Java → JUnit/TestNG → Docker → GitHub Actions → Docker Hub** on Windows.

[![Practical 1 CI](https://github.com/Madhukesh2005/exam-prep/actions/workflows/practical-1.yml/badge.svg)](https://github.com/Madhukesh2005/exam-prep/actions/workflows/practical-1.yml)
[![Practical 2 CI](https://github.com/Madhukesh2005/exam-prep/actions/workflows/practical-2.yml/badge.svg)](https://github.com/Madhukesh2005/exam-prep/actions/workflows/practical-2.yml)

## ✨ What is included

| Lab | Stack | Outcome |
|---|---|---|
| **Practical 1** | Java 17, Maven, JUnit 5, Docker, Fabric8, GitHub Actions | Grade calculator packaged as a JAR and published to Docker Hub |
| **Practical 2** | Java 17, Selenium, TestNG, Chrome, Docker Buildx, GitHub Actions | Headless local HTML login tests and a published Selenium test image |

## 🗂️ Repository map

```text
exam-prep/
├── practical-1-grade-calculator/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── src/main/java/com/example/Mahathi/GradeCalculator.java
│   ├── src/test/java/com/example/Mahathi/GradeCalculatorTest.java
│   └── .github/workflows/practical-1.yml
├── practical-2-selenium-testng/
│   ├── pom.xml
│   ├── Dockerfile
│   ├── testng.xml
│   ├── webpage/login.html
│   ├── src/test/java/com/example/LocalLoginTest.java
│   └── .github/workflows/practical-2.yml
└── README.md
```

> Each practical is intentionally self-contained. Run Maven from the practical's own directory.

---

# 🧰 PART 0 — Windows prerequisites

Install:

- [Google Chrome](https://www.google.com/chrome/)
- [Eclipse IDE for Java Developers](https://www.eclipse.org/downloads/packages/)
- [JDK 17](https://adoptium.net/temurin/releases/?version=17)
- [Git for Windows](https://git-scm.com/download/win)
- [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)

Open **PowerShell** and verify:

```powershell
java -version
git --version
docker --version
```

Start Docker Desktop and wait for **Engine running**. Docker Desktop should use Linux containers.

Configure Git once:

```powershell
git config --global user.name "Your Name"
git config --global user.email "your@email.com"
```

## GitHub authentication

Create a GitHub classic personal access token:

1. GitHub → profile photo → **Settings**.
2. **Developer settings** → **Personal access tokens** → **Tokens classic**.
3. Generate a token with `repo` and `workflow` selected.
4. Copy it immediately and store it safely.

When Git asks for a password, paste the token. Nothing appears while pasting; press Enter.

## Docker Hub authentication

Create a Docker Hub personal access token with **Read & Write** permissions. It will be used as the `DOCKERHUB_TOKEN` GitHub secret.

---

# 🚀 Practical 1 — Maven project → GitHub → Docker Hub

## 1. Create the project in Eclipse

Create a Maven project using:

```text
Group Id:    com.example
Artifact Id: student-grade-calculator
Version:     1.0-SNAPSHOT
Packaging:   jar
```

The ready-to-run implementation is already available in [`practical-1-grade-calculator`](practical-1-grade-calculator).

## 2. Run the Java program

```powershell
cd practical-1-grade-calculator
mvn clean package
java -cp target/classes com.example.Mahathi.GradeCalculator
```

Expected output:

```text
Marks : 95
Grade : A
```

## 3. Run JUnit tests

```powershell
mvn test
```

Expected result:

```text
Tests run: 3, Failures: 0
BUILD SUCCESS
```

## 4. Build and run the Docker image locally

```powershell
docker build -t student-grade-calculator .
docker run --rm student-grade-calculator
```

Expected output:

```text
Marks : 95
Grade : A
```

## 5. Publish with Maven and Docker Hub

Create a public Docker Hub repository named `student-grade-calculator`. Then set environment variables in PowerShell:

```powershell
$env:DOCKERHUB_USERNAME="your-dockerhub-username"
$env:DOCKERHUB_TOKEN="your-dockerhub-token"
mvn docker:build docker:push
```

The Fabric8 Docker Maven Plugin configuration is in [`pom.xml`](practical-1-grade-calculator/pom.xml).

## 6. GitHub Actions setup

Add these repository secrets under **Settings → Secrets and variables → Actions**:

```text
DOCKERHUB_USERNAME = your Docker Hub username
DOCKERHUB_TOKEN    = your Docker Hub token
```

The workflow at [`.github/workflows/practical-1.yml`](practical-1-grade-calculator/.github/workflows/practical-1.yml) automatically:

1. Checks out the code.
2. Installs JDK 17.
3. Runs `mvn clean package`.
4. Builds and pushes the Docker image on pushes to `main`.

---

# 🧪 Practical 2 — Selenium + TestNG → GitHub → Docker Hub

## 1. Project details

```text
Group Id:    com.example
Artifact Id: SeleniumLocalWebTest
Version:     1.0-SNAPSHOT
Packaging:   jar
```

The ready-to-run implementation is in [`practical-2-selenium-testng`](practical-2-selenium-testng).

## 2. Run Selenium tests on Windows

```powershell
cd practical-2-selenium-testng
mvn clean test
```

Selenium Manager downloads a compatible ChromeDriver automatically. The tests use headless Chrome, so no browser window is expected.

Expected result:

```text
Tests run: 3
Failures: 0
Skipped: 0
BUILD SUCCESS
```

The three tests verify:

- Valid login returns `Login Successful`.
- Invalid credentials return `Invalid Username or Password`.
- The login panel background is `rgba(173, 216, 230, 1)`.

## 3. Run with Eclipse TestNG

Install **TestNG for Eclipse** from **Help → Eclipse Marketplace**, restart Eclipse, then right-click `LocalLoginTest.java` → **Run As → TestNG Test**.

If Marketplace installation fails, use:

```text
https://testng.org/testng-eclipse-update-site
```

## 4. Build the Selenium Docker image

The Dockerfile installs Google Chrome in an AMD64 Linux container and runs the Maven suite:

```powershell
docker build -t selenium-testng-automation .
docker run --rm selenium-testng-automation
```

On Windows, Docker Desktop must be running with Linux containers enabled. On ARM machines, use GitHub Actions for the AMD64 image rather than relying on a local build.

## 5. GitHub Actions and Docker Hub

Create a public Docker Hub repository named `selenium-testng-automation` and add the same two GitHub secrets:

```text
DOCKERHUB_USERNAME = your Docker Hub username
DOCKERHUB_TOKEN    = your Docker Hub token
```

The workflow at [`.github/workflows/practical-2.yml`](practical-2-selenium-testng/.github/workflows/practical-2.yml) automatically:

1. Installs JDK 17 and Chrome.
2. Runs all Selenium/TestNG tests.
3. Logs in to Docker Hub.
4. Builds the AMD64 image with Buildx.
5. Publishes both `latest` and the commit SHA tag on pushes to `main`.

---

# 🔐 GitHub repository workflow

This repository is already public and uses the default `main` branch. For a separate copy of either practical:

```powershell
git clone https://github.com/Madhukesh2005/exam-prep.git
cd exam-prep
git status
```

Commit changes normally:

```powershell
git add .
git commit -m "Update practical exercises"
git push origin main
```

Do not commit:

- Docker Hub tokens.
- GitHub tokens.
- `.env` files.
- `target/` output.
- Eclipse metadata when it is not required.

---

# 🧭 Eclipse visibility tips

If `.gitignore` or `.github` is hidden:

**Package Explorer → ⋮ → Filters… → untick `.* resources` → OK**

To open a project in PowerShell:

1. Right-click the project → **Properties → Resource**.
2. Copy the location.
3. Run:

```powershell
cd "C:\path\to\project"
dir
```

You should see `pom.xml`.

---

# 🧯 Troubleshooting

| Problem | Fix |
|---|---|
| `mvn` is not recognized | Use Eclipse Maven, or install Maven and add its `bin` folder to Windows PATH. |
| Docker commands fail | Start Docker Desktop and wait for **Engine running**. |
| Docker job says client version is too old | Use Fabric8 Docker Maven Plugin `0.48.0`. |
| Docker says unauthorized or denied | Check exact secret names, Docker username, and Read & Write token permission. |
| Git rejects workflow changes | Use a GitHub token with both `repo` and `workflow` permissions. |
| `remote origin already exists` | Run `git remote set-url origin https://github.com/YOUR_USERNAME/REPO.git`. |
| Selenium cannot find `login.html` | Keep `webpage` directly beside `pom.xml`. |
| Chrome tests fail locally | Confirm Chrome is installed, keep Docker Desktop available, and retry after Selenium Manager downloads the driver. |
| Maven errors after editing `pom.xml` | Eclipse: right-click project → Maven → Update Project → enable Force Update. |
| `.gitignore` or `.github` is invisible | Disable `.* resources` in Package Explorer filters. |

---

# ✅ Completion checklist

- [ ] Java 17, Git, Chrome, Eclipse, and Docker Desktop installed.
- [ ] Practical 1 runs with `mvn clean package`.
- [ ] Practical 1 JUnit tests pass.
- [ ] Practical 1 Docker image runs locally.
- [ ] Practical 1 image is published to Docker Hub.
- [ ] Practical 2 Selenium/TestNG tests pass.
- [ ] Practical 2 Docker image builds in GitHub Actions.
- [ ] Both GitHub Actions workflows are green.
- [ ] Docker Hub contains the expected image tags.
- [ ] No credentials are committed.

> ⭐ If this workspace helps your preparation, star the repository and keep improving the practicals with small, verifiable commits.
