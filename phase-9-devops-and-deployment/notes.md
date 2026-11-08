# Docker Notes

### Table of Contents

**Part A: Core Docker**

1. [What is Docker? Why do we need it?](#1-what-is-docker-why-do-we-need-it)
2. [Docker Images and Containers](#2-docker-images-and-containers)
3. [Installation of Docker CLI and Desktop](#3-installation-of-docker-cli-and-desktop)
4. [Important Docker Commands](#4-important-docker-commands)
5. [Docker vs Virtual Machine](#5-docker-vs-virtual-machine)
6. [Port Mapping and Environment Variables](#6-port-mapping-and-environment-variables)
7. [Troubleshooting Containers](#7-troubleshooting-containers)
8. [Using Containers to Build an Application](#8-using-containers-to-build-an-application)
9. [Dockerization of a Spring Boot App (Dockerfile)](#9-dockerization-of-a-spring-boot-app-dockerfile)
10. [Docker Compose](#10-docker-compose)
11. [Publishing to Docker Hub](#11-publishing-to-docker-hub)
12. [Layering in Docker Images](#12-layering-in-docker-images)
13. [Volume Mounting](#13-volume-mounting)
14. [Docker Networking](#14-docker-networking)

**Part B: Java and Production Topics**

15. [Multi-Stage Builds](#15-multi-stage-builds)
16. [.dockerignore](#16-dockerignore)
17. [Health Checks and depends_on](#17-health-checks-and-depends_on)
18. [Docker Security Basics](#18-docker-security-basics)
19. [Image Tagging and Versioning](#19-image-tagging-and-versioning)
20. [Logs, Resource Limits and Restart Policies](#20-logs-resource-limits-and-restart-policies)

**Part C**

- [Practice Plan with Your Own Project](#practice-plan-with-your-own-project)
- [Command Cheat Sheet](#command-cheat-sheet)

---

# PART A: CORE DOCKER

---

## 1. What is Docker? Why do we need it?

**Overview:** This topic has 3 parts: the problem, the solution, and how Docker is built inside.

### 1.1 The problem

Your app works on your laptop, but fails on the server. Common reasons:

- Different Java version (you use 21, server has 11)
- Different OS or OS libraries
- Missing config or environment variables
- Different MySQL version
- Two apps on the same server need different versions of the same tool

Also, a new team member may spend a full day just setting up the project (install JDK, MySQL, Node, set paths, and so on).

### 1.2 The solution

**Docker** is a platform that packages your application **together with everything it needs to run** (runtime, libraries, config, files) into one unit called an **image**. When you run that image, it becomes a **container**: an isolated process on the host machine.

The same image runs the same way on your laptop, your friend's laptop, a test server, and production.

```
Without Docker:                    With Docker:

 Laptop    Server                   Laptop     Server
 JDK 21    JDK 11                   [ Image ]  [ Image ]
 MySQL 8   MySQL 5.7                 same       same
   |         |                       result     result
 "works"   "broken"                 "works"    "works"
```

### 1.3 How Docker works inside (important for engineers)

Docker is **not magic**. It uses features that already exist in the Linux kernel:

| Linux feature                  | What it does                                                                                                                                                                                          |
| ------------------------------ | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Namespaces**                 | Gives each container its own view of the system: its own process IDs (pid), network (net), filesystem mounts (mnt), hostname (uts), users (user). A container cannot see other containers' processes. |
| **cgroups (control groups)**   | Limits how much CPU, memory, and I/O a container can use.                                                                                                                                             |
| **Union / overlay filesystem** | Stacks read-only layers into one filesystem view (explained in topic 12).                                                                                                                             |

So a container is basically: **a normal Linux process + namespaces (isolation) + cgroups (limits) + a layered filesystem.**

### 1.4 Docker architecture

```
 +-------------+      REST API       +----------------------+
 | Docker CLI  | ------------------> |  Docker Daemon       |
 | (docker ... |                     |  (dockerd)           |
 |  commands)  |                     |   - builds images    |
 +-------------+                     |   - runs containers  |
                                     |   - manages volumes  |
                                     |     and networks     |
                                     +----------+-----------+
                                                |
                          pull / push           |  uses containerd + runc
                                                v  to start containers
                                     +----------------------+
                                     |  Registry            |
                                     |  (Docker Hub, GHCR,  |
                                     |   AWS ECR, ...)      |
                                     +----------------------+
```

- **Docker CLI**: the `docker` command you type.
- **Docker Daemon (`dockerd`)**: the background service that does the real work.
- **Registry**: a server that stores images. Docker Hub is the default public one.

> On Windows and Mac, Docker Desktop runs a small hidden Linux VM, because containers need a Linux kernel.

---

## 2. Docker Images and Containers

**Overview:** 2 main ideas (image, container) plus the container lifecycle.

### 2.1 Image

An **image** is a **read-only template** that contains:

- A filesystem (OS files, JRE, your JAR, config files)
- Metadata (default command to run, environment variables, exposed port, working directory, user)

An image is built from **layers** (topic 12). Images never change after they are built. Every image has a name and tag, like `mysql:8.4` or `eclipse-temurin:21-jre`.

### 2.2 Container

A **container** is a **running instance of an image**. When you start a container, Docker:

1. Takes the image's read-only layers.
2. Adds a thin **writable layer** on top.
3. Starts the main process inside new namespaces with cgroup limits.

You can start many containers from one image. Each has its own writable layer, so they do not affect each other.

For a Java developer, this is like **class and object**: the image is the class (the definition), the container is an object (a live instance).

```
              Image: myapp:1.0  (read-only layers)
             /          |           \
     Container A   Container B   Container C
     (own write    (own write    (own write
      layer)        layer)        layer)
```

### 2.3 Container lifecycle

```
   docker create          docker start
 (none) ---------> Created ----------> Running <------+
                                         |  |         |
                          docker pause   |  | docker  | docker
                                         v  | stop/   | unpause
                                      Paused| kill    |
                                            v         |
                                         Exited ------+ (docker start)
                                            |
                                            | docker rm
                                            v
                                         Removed
```

Important points:

- `docker run` = `create` + `start` in one command.
- When the **main process exits, the container exits**. A container is only alive as long as its main process (PID 1) is alive.
- Data in the writable layer is **lost when the container is removed**. Use volumes (topic 13) for data that must stay.

---

## 3. Installation of Docker CLI and Desktop

**Overview:** 2 ways to install: Docker Desktop (easy, with GUI) or Docker Engine (Linux servers).

### 3.1 Docker Desktop (Windows / Mac / Linux desktop)

- Includes Docker Engine, CLI, Docker Compose, and a GUI.
- **Windows:** uses **WSL 2** (Windows Subsystem for Linux) in the background. Enable WSL 2 and virtualization in BIOS if asked.
- **Mac:** uses a lightweight VM. Pick the Apple Silicon or Intel build to match your chip.
- Download from the official Docker website.

### 3.2 Docker Engine (Linux servers)

On Ubuntu or other Linux servers you install **Docker Engine** (no GUI) using the official apt repository. Follow the official guide at `docs.docker.com/engine/install`, because exact steps change over time.

After installing on Linux, you usually add your user to the `docker` group so you do not need `sudo`:

```bash
sudo usermod -aG docker $USER
# log out and log in again
```

> Security note: users in the `docker` group effectively have root power on that machine.

### 3.3 Verify the installation

```bash
docker --version          # shows CLI version
docker version            # shows client AND daemon (daemon must be running)
docker compose version    # Compose is built in
docker run hello-world    # downloads a tiny image and runs it
```

If `docker version` shows an error about the daemon, Docker Desktop is not running (or the Linux service is stopped: `sudo systemctl start docker`).

---

## 4. Important Docker Commands

**Overview:** 5 groups: images, running containers, inspecting, cleaning, and building/publishing.

### 4.1 Images

```bash
docker pull nginx:1.27          # download image from registry
docker images                   # list local images (also: docker image ls)
docker rmi nginx:1.27           # remove an image
docker history nginx:1.27       # show layers of an image
docker image prune              # remove unused (dangling) images
```

### 4.2 Run containers

```bash
docker run nginx                       # run in foreground
docker run -d nginx                    # -d = detached (background)
docker run -d --name web nginx         # give the container a name
docker run -d -p 8080:80 nginx         # map host port 8080 -> container port 80
docker run -d -e MYSQL_ROOT_PASSWORD=secret mysql:8.4   # set env variable
docker run --rm alpine echo "hi"       # --rm = delete container when it exits
docker run -it ubuntu bash             # -i interactive, -t terminal: get a shell
docker run -d -v mydata:/var/lib/mysql mysql:8.4        # attach a volume
docker run -d --network mynet nginx    # join a custom network
```

Common flags:

| Flag                | Meaning                        |
| ------------------- | ------------------------------ |
| `-d`                | run in background              |
| `-it`               | interactive terminal           |
| `--name`            | container name                 |
| `-p host:container` | publish a port                 |
| `-e KEY=value`      | environment variable           |
| `--env-file file`   | many env variables from a file |
| `-v`                | volume or bind mount           |
| `--network`         | network to join                |
| `--rm`              | auto-remove after exit         |
| `--restart`         | restart policy (topic 20)      |

### 4.3 Manage and inspect containers

```bash
docker ps                  # running containers
docker ps -a               # all containers (including stopped)
docker stop web            # graceful stop (SIGTERM, then SIGKILL after 10s)
docker kill web            # immediate stop (SIGKILL)
docker start web           # start a stopped container
docker restart web
docker rm web              # remove a stopped container
docker rm -f web           # force remove (stops it first)
docker logs web            # see output
docker logs -f --tail 100 web   # follow last 100 lines live
docker exec -it web bash   # open a shell INSIDE a running container
docker inspect web         # full details in JSON
docker stats               # live CPU / memory usage
docker cp web:/app/log.txt .   # copy a file out of the container
```

### 4.4 Cleaning up

```bash
docker container prune     # remove all stopped containers
docker image prune -a      # remove all unused images
docker volume prune        # remove unused volumes (careful: data!)
docker system prune        # stopped containers, unused networks, dangling images
docker system df           # see how much disk Docker is using
```

### 4.5 Build and publish

```bash
docker build -t myapp:1.0 .       # build image from Dockerfile in current folder
docker tag myapp:1.0 user/myapp:1.0
docker login
docker push user/myapp:1.0
```

> **Tip:** `docker stop` sends SIGTERM first so your Spring Boot app can shut down gracefully. `docker kill` does not give that chance.

---

## 5. Docker vs Virtual Machine

**Overview:** Both give isolation, but at different levels. A VM virtualizes **hardware**, a container virtualizes the **operating system**.

```
        Virtual Machines                        Containers

 +-------+ +-------+ +-------+          +-------+ +-------+ +-------+
 | App A | | App B | | App C |          | App A | | App B | | App C |
 +-------+ +-------+ +-------+          +-------+ +-------+ +-------+
 | Bins/ | | Bins/ | | Bins/ |          | Bins/ | | Bins/ | | Bins/ |
 | Libs  | | Libs  | | Libs  |          | Libs  | | Libs  | | Libs  |
 +-------+ +-------+ +-------+          +-------+-+-------+-+-------+
 |Guest  | |Guest  | |Guest  |          |        Docker Engine      |
 |  OS   | |  OS   | |  OS   |          +---------------------------+
 +-------+-+-------+-+-------+          |   Host OS (shared kernel) |
 |        Hypervisor         |          +---------------------------+
 +---------------------------+          |      Hardware             |
 |        Host OS            |          +---------------------------+
 +---------------------------+
 |        Hardware           |
 +---------------------------+
```

| Point                  | Virtual Machine                          | Container                                                                        |
| ---------------------- | ---------------------------------------- | -------------------------------------------------------------------------------- |
| Has its own OS kernel? | Yes (full guest OS)                      | No, shares the host kernel                                                       |
| Size                   | GBs                                      | MBs (often 50 to 300 MB for Java apps)                                           |
| Start time             | Minutes                                  | Seconds or less                                                                  |
| Resource use           | Heavy (OS overhead)                      | Light                                                                            |
| Isolation              | Strong (hardware level)                  | Process level (weaker, but good enough for most apps)                            |
| Different OS?          | Yes (Windows VM on Linux host)           | Container must match the host kernel type (Linux containers need a Linux kernel) |
| Best for               | Running different OSes, strong isolation | Packaging and scaling apps                                                       |

**Key takeaway:** Containers are lighter and faster because they **share the host kernel**. The trade-off is weaker isolation: a kernel bug could affect all containers. In real systems, containers often run **inside** VMs (for example on cloud servers).

---

## 6. Port Mapping and Environment Variables

**Overview:** 2 parts. Ports let outside traffic reach a container. Env variables pass configuration into it.

### 6.1 Port mapping

A container has its **own network namespace**. A server listening on port 8080 inside the container is **not** reachable from your machine by default. You must **publish** the port:

```bash
docker run -d -p 8080:8080 myapp:1.0
#               HOST:CONTAINER
```

```
 Browser -> localhost:9090 -> [ Host ] --maps to--> [ Container :8080 ]
                                  docker run -p 9090:8080
```

Useful variations:

```bash
-p 9090:8080              # host 9090 -> container 8080
-p 127.0.0.1:8080:8080    # only reachable from this machine (safer)
-P                        # publish all EXPOSEd ports on random host ports
```

Rules:

- **Host port must be free.** If another process uses it, you get "port is already allocated".
- `EXPOSE 8080` in a Dockerfile is **only documentation**. It does **not** publish the port. Only `-p` (or `ports:` in Compose) does.
- Containers on the same Docker network talk to each other **without** publishing ports (topic 14).

### 6.2 Environment variables

Config should not be hard-coded in the image. Pass it when running:

```bash
docker run -d \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/appdb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  myapp:1.0
```

Or from a file:

```bash
# app.env
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=secret

docker run -d --env-file app.env myapp:1.0
```

**Spring Boot relaxed binding:** an env variable `SPRING_DATASOURCE_URL` automatically maps to the property `spring.datasource.url` (uppercase, underscores instead of dots). So you do not need to change `application.properties` for each environment.

Ways to set env variables:

| Where                  | Syntax                                | Use for                           |
| ---------------------- | ------------------------------------- | --------------------------------- |
| `docker run -e`        | `-e KEY=val`                          | quick runs                        |
| `--env-file`           | file with `KEY=val` lines             | many variables                    |
| Dockerfile `ENV`       | `ENV KEY=val`                         | non-secret defaults               |
| Compose `environment:` | map or list                           | multi-container setups            |
| Compose `.env` file    | auto-loaded for `${VAR}` substitution | variables inside the compose file |

> Never put passwords in a Dockerfile `ENV`. They stay inside the image and anyone with the image can read them (topic 18).

---

## 7. Troubleshooting Containers

**Overview:** A step-by-step way to debug, then the most common problems.

### 7.1 Debug steps (use in this order)

```
1. docker ps -a              -> Is it running? Did it exit? What exit code?
2. docker logs <name>        -> What did the app print before it died?
3. docker inspect <name>     -> Check env, ports, mounts, network, exit code
4. docker exec -it <name> sh -> Look inside (files, curl other services)
5. docker stats / events     -> Resource problems, restarts
```

### 7.2 Exit codes

| Code      | Meaning                                                          |
| --------- | ---------------------------------------------------------------- |
| 0         | Normal exit                                                      |
| 1         | App error (check logs)                                           |
| 125       | Docker command itself failed                                     |
| 126 / 127 | Command cannot run / not found (wrong ENTRYPOINT or path)        |
| 137       | Killed (SIGKILL). Often **out of memory (OOM)** or `docker kill` |
| 143       | Stopped with SIGTERM (normal `docker stop`)                      |

Check OOM: `docker inspect <name> --format '{{.State.OOMKilled}}'`

### 7.3 Common problems and fixes

| Problem                                          | Likely reason                                                  | Fix                                                                       |
| ------------------------------------------------ | -------------------------------------------------------------- | ------------------------------------------------------------------------- |
| Container exits immediately                      | Main process finished or crashed                               | `docker logs`. A container must have a long-running main process.         |
| `port is already allocated`                      | Host port in use                                               | Change the host port, or stop the other process.                          |
| Cannot reach app from browser                    | Port not published, or app listens only on 127.0.0.1 inside    | Use `-p`. Make the app listen on `0.0.0.0` (Spring Boot does by default). |
| Spring app cannot connect to DB with `localhost` | Inside a container, `localhost` means **the container itself** | Use the DB **service or container name** (like `db`) on a shared network. |
| App starts before DB is ready                    | No wait logic                                                  | Health check + `depends_on` (topic 17), or retry in app.                  |
| `no such image` / `pull access denied`           | Typo, wrong tag, private repo                                  | Check name and tag, run `docker login`.                                   |
| Changes in code not visible                      | Old image is used                                              | Rebuild: `docker build` or `docker compose up --build`.                   |
| `permission denied` on mounted folder            | File owner/user mismatch                                       | Check the container user and folder permissions.                          |
| Disk full                                        | Old images, volumes, build cache                               | `docker system df`, then `docker system prune`.                           |
| Container keeps restarting                       | Restart policy + app crash loop                                | `docker logs`, fix the crash.                                             |

### 7.4 Run a debug shell

```bash
docker exec -it myapp sh          # use "sh" if "bash" is missing (slim/alpine images)
docker run -it --rm --entrypoint sh myapp:1.0   # start a new container with a shell instead of the app
```

---

## 8. Using Containers to Build an Application

**Overview:** You can use a container as a **clean build machine**. You do not need Maven or the right JDK installed on your computer. This is the idea your course shows with Node.js. Here it is with Java.

### 8.1 Build a JAR with a Maven container

From your project folder (where `pom.xml` is):

```bash
docker run --rm \
  -v "$(pwd)":/app \
  -w /app \
  maven:3.9-eclipse-temurin-21 \
  mvn clean package -DskipTests
```

What each part does:

| Part                           | Meaning                                                                |
| ------------------------------ | ---------------------------------------------------------------------- |
| `--rm`                         | remove container after build                                           |
| `-v "$(pwd)":/app`             | bind mount: your project folder appears at `/app` inside the container |
| `-w /app`                      | working directory inside the container                                 |
| `maven:3.9-eclipse-temurin-21` | image that has Maven and JDK 21                                        |
| `mvn clean package ...`        | the command that runs                                                  |

The JAR appears in your local `target/` folder, because of the bind mount.

### 8.2 Speed up with a Maven cache volume

By default Maven downloads all dependencies on every run. Keep them in a named volume:

```bash
docker run --rm \
  -v "$(pwd)":/app \
  -v maven-repo:/root/.m2 \
  -w /app \
  maven:3.9-eclipse-temurin-21 \
  mvn clean package -DskipTests
```

### 8.3 Run the JAR in another container

```bash
docker run --rm -p 8080:8080 \
  -v "$(pwd)/target":/app \
  -w /app \
  eclipse-temurin:21-jre \
  java -jar my-app.jar
```

This is useful for learning. For real projects, you put these steps in a **Dockerfile** (next topic) and later in a **multi-stage build** (topic 15).

---

## 9. Dockerization of a Spring Boot App (Dockerfile)

**Overview:** 3 parts: Dockerfile instructions, a first working Dockerfile, and how to build and run.

### 9.1 What is a Dockerfile?

A **Dockerfile** is a text file with step-by-step instructions to build an image. Each instruction creates a layer.

### 9.2 Important instructions

| Instruction     | What it does                                                                      |
| --------------- | --------------------------------------------------------------------------------- |
| `FROM`          | Base image to start from. Must be first.                                          |
| `WORKDIR`       | Sets the working folder for next instructions (creates it if missing).            |
| `COPY src dest` | Copies files from your machine (build context) into the image.                    |
| `ADD`           | Like COPY, but can also extract tar files and download URLs. **Prefer COPY.**     |
| `RUN`           | Runs a command **at build time** (install packages, build code). Creates a layer. |
| `ENV`           | Sets environment variable (available at build and run time).                      |
| `ARG`           | Build-time variable only (`docker build --build-arg`). Not available at run time. |
| `EXPOSE`        | Documents the port the app uses. Does not publish it.                             |
| `USER`          | Sets the user for next instructions and for running the container.                |
| `ENTRYPOINT`    | The main command that always runs.                                                |
| `CMD`           | Default arguments or command. Can be overridden at `docker run`.                  |
| `HEALTHCHECK`   | Command Docker runs to check app health.                                          |
| `VOLUME`        | Declares a mount point for data.                                                  |
| `LABEL`         | Adds metadata.                                                                    |

**RUN vs CMD vs ENTRYPOINT:**

- `RUN` happens **when building** the image.
- `CMD` / `ENTRYPOINT` happen **when starting** a container.
- `ENTRYPOINT` is the fixed program, `CMD` gives default arguments to it. If you pass arguments to `docker run image args`, they replace `CMD` but not `ENTRYPOINT`.

**Exec form vs shell form:**

```dockerfile
ENTRYPOINT ["java", "-jar", "app.jar"]    # exec form (recommended)
ENTRYPOINT java -jar app.jar              # shell form
```

Use the **exec form**. In shell form, the process runs under `/bin/sh -c`, so Java is not PID 1 and may not receive the SIGTERM signal from `docker stop`. Then your app cannot shut down gracefully.

### 9.3 First Dockerfile (simple version)

Build the JAR first (`mvn clean package`), then:

```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/my-app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Line by line:

1. `FROM eclipse-temurin:21-jre`: base image with only the Java **runtime** (JRE), smaller than a full JDK.
2. `WORKDIR /app`: all next commands run in `/app`.
3. `COPY ...`: puts your JAR into the image as `app.jar`.
4. `EXPOSE 8080`: documentation that the app listens on 8080.
5. `ENTRYPOINT`: runs the app when the container starts.

### 9.4 Build and run

```bash
docker build -t my-app:1.0 .        # "." = build context (current folder)
docker run -d --name my-app -p 8080:8080 my-app:1.0
docker logs -f my-app
```

> The simple version needs you to build the JAR on your machine first. Topic 15 (multi-stage build) removes that need.

### 9.5 JVM and containers

- Modern JVMs (Java 10+) detect container memory limits automatically.
- Control heap size by percentage so it follows the container limit:

```dockerfile
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
```

---

## 10. Docker Compose

**Overview:** Compose runs **multi-container apps** from one YAML file. 4 parts from your course: services, ports, env variables, volumes. We add networks and commands.

### 10.1 Why Compose?

A real app is not one container. Example: Spring Boot backend + MySQL + React frontend. Without Compose you type three long `docker run` commands in the right order. With Compose you write `compose.yaml` once and run:

```bash
docker compose up -d
```

### 10.2 Full example

Project layout:

```
my-project/
├── compose.yaml
├── .env
├── backend/
│   ├── Dockerfile
│   └── (Spring Boot code)
└── frontend/
    ├── Dockerfile
    └── (React code)
```

`.env` (Compose reads this automatically for `${VARIABLES}`; do **not** commit it to Git):

```
DB_ROOT_PASSWORD=change_me
DB_NAME=appdb
```

`compose.yaml`:

```yaml
services:
  db:
    image: mysql:8.4
    environment:
      MYSQL_ROOT_PASSWORD: ${DB_ROOT_PASSWORD}
      MYSQL_DATABASE: ${DB_NAME}
    volumes:
      - db_data:/var/lib/mysql
    healthcheck:
      test:
        [
          "CMD",
          "mysqladmin",
          "ping",
          "-h",
          "localhost",
          "-uroot",
          "-p${DB_ROOT_PASSWORD}",
        ]
      interval: 10s
      timeout: 5s
      retries: 10

  backend:
    build: ./backend
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/${DB_NAME}
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: ${DB_ROOT_PASSWORD}
    depends_on:
      db:
        condition: service_healthy

  frontend:
    build: ./frontend
    ports:
      - "3000:80"
    depends_on:
      - backend

volumes:
  db_data:
```

### 10.3 Explanation of each part

**a. Services**

Each entry under `services:` is one container definition. Use either:

- `image:` to use a ready image (like `mysql:8.4`), or
- `build:` to build from a Dockerfile in a folder.

**b. Port mapping**

`ports: ["host:container"]`, same as `-p`. Always write ports in quotes to avoid YAML parsing surprises.

Only publish ports that you really need from the host. In the example, the database has **no** `ports:`, so only the backend can reach it (through the internal network). That is safer.

**c. Environment variables**

- `environment:` sets variables for the container.
- `${DB_ROOT_PASSWORD}` is replaced by Compose from the `.env` file or your shell.
- `env_file: [app.env]` loads a whole file into a container.

**d. Volumes**

- `db_data:/var/lib/mysql` uses a **named volume** so MySQL data survives `docker compose down`.
- Named volumes must be declared in the top-level `volumes:` block.
- Bind mount example: `- ./backend/config:/app/config:ro`.

**e. Networks (automatic)**

Compose creates **one default network** for the project. Every service joins it, and can reach others **by service name**. This is why the backend uses `jdbc:mysql://db:3306/...`: `db` is the service name.

### 10.4 Compose commands

```bash
docker compose up                # create and start (foreground)
docker compose up -d             # background
docker compose up -d --build     # rebuild images first
docker compose ps                # status of services
docker compose logs -f backend   # follow logs of one service
docker compose exec db mysql -uroot -p     # run a command in a running service
docker compose stop              # stop containers
docker compose down              # stop and REMOVE containers and networks
docker compose down -v           # also REMOVE volumes (deletes DB data!)
docker compose build             # only build images
docker compose config            # validate and show final merged config
```

> Older tutorials use `docker-compose` (with a hyphen, Compose v1) and a `version:` line at the top. Modern Docker uses `docker compose` (space) and the `version:` line is no longer needed.

---

## 11. Publishing to Docker Hub

**Overview:** A registry stores images so other machines (servers, teammates) can pull them. 4 steps: account, login, tag, push.

### 11.1 Steps

1. Create an account on hub.docker.com. Your **username** becomes the namespace.
2. Log in from the terminal:

```bash
docker login
```

3. **Tag** your image with the format `username/repository:tag`:

```bash
docker tag my-app:1.0 yourusername/my-app:1.0
```

Or build with the full name directly:

```bash
docker build -t yourusername/my-app:1.0 .
```

4. **Push**:

```bash
docker push yourusername/my-app:1.0
```

### 11.2 Use the image anywhere

```bash
docker pull yourusername/my-app:1.0
docker run -d -p 8080:8080 yourusername/my-app:1.0
```

Or in Compose: `image: yourusername/my-app:1.0`

### 11.3 Good to know

- **Public repos** are free and visible to everyone. **Private repos** need a paid plan or limits apply (check current Docker Hub plans).
- Use an **access token** (Account settings, Security) instead of your real password for `docker login`, especially in CI/CD.
- Pushing needs the image name to start with your namespace. `my-app:1.0` alone cannot be pushed to your account.
- Other registries work the same way, only the name prefix changes: GitHub Container Registry (`ghcr.io/user/app`), AWS ECR, Google Artifact Registry, Azure ACR.
- **Never push images that contain secrets** (passwords, API keys, `.env` files).

---

## 12. Layering in Docker Images

**Overview:** An image is a stack of layers. Understanding layers lets you build **faster** and make **smaller** images.

### 12.1 What is a layer?

Each of these Dockerfile instructions creates a **read-only layer**: `FROM`, `RUN`, `COPY`, `ADD`. (Other instructions like `ENV`, `WORKDIR`, `EXPOSE` only change metadata and add no real file content.)

```
Dockerfile                          Image layers (bottom to top)

FROM eclipse-temurin:21-jre   -->   [ Base OS + JRE layers ]
WORKDIR /app                  -->   [ metadata ]
COPY app.jar app.jar          -->   [ layer: app.jar ]
ENTRYPOINT [...]              -->   [ metadata ]
                                          |
                          container adds: [ thin WRITABLE layer ]
```

See layers of any image: `docker history my-app:1.0`

### 12.2 Why layers matter

1. **Sharing:** if two images use the same base layers, Docker stores them **once** on disk and downloads them **once**.
2. **Build cache:** Docker reuses a layer if the instruction and its inputs did not change. **But if one layer changes, all layers after it are rebuilt.**
3. **Copy-on-write:** when a running container modifies a file from a lower layer, the file is first copied to the writable layer, then changed. Original layers stay untouched.

### 12.3 Cache-friendly ordering (very important)

**Bad order**: any code change invalidates the dependency download:

```dockerfile
COPY . .                       # code changes often -> cache broken here
RUN mvn dependency:go-offline  # re-downloads ALL dependencies every time
RUN mvn package
```

**Good order**: put things that change **rarely** first, and things that change **often** last:

```dockerfile
COPY pom.xml .                           # changes rarely
RUN mvn -B dependency:go-offline         # cached unless pom.xml changes
COPY src ./src                           # changes often
RUN mvn -B package -DskipTests           # only this part reruns
```

### 12.4 Keep layers small

- Files deleted in a **later** layer still exist in the **earlier** layer (image size does not shrink). So clean up in the **same** `RUN`:

```dockerfile
RUN apt-get update \
 && apt-get install -y --no-install-recommends curl \
 && rm -rf /var/lib/apt/lists/*
```

- Use small base images (`-jre`, `-slim`, `-alpine`) when possible.
- Use multi-stage builds (topic 15) so build tools never reach the final image.

---

## 13. Volume Mounting

**Overview:** Container data is **temporary**. Volumes store data **outside** the container's life. 3 types: named volumes, bind mounts, tmpfs.

### 13.1 The problem

When a container is removed, its writable layer is deleted. If MySQL stored data there, **all data is lost**.

### 13.2 Types of mounts

```
 +----------------------+           +-----------------------------+
 | Container            |           | Host machine                |
 |   /var/lib/mysql  ---+---------> | Docker-managed area         |  Named volume
 |                      |           | (/var/lib/docker/volumes/)  |
 |   /app/config     ---+---------> | /home/me/project/config     |  Bind mount
 |   /tmp/cache      ---+---------> | RAM only                    |  tmpfs
 +----------------------+           +-----------------------------+
```

| Type             | Syntax                          | Managed by | Use for                                                 |
| ---------------- | ------------------------------- | ---------- | ------------------------------------------------------- |
| **Named volume** | `-v db_data:/var/lib/mysql`     | Docker     | **Database data, persistent app data** (best choice)    |
| **Bind mount**   | `-v /host/path:/container/path` | You        | Development (live code or config from your folder)      |
| **tmpfs**        | `--tmpfs /tmp`                  | Memory     | Temporary, secret or fast data that must not touch disk |

### 13.3 Commands

```bash
docker volume create db_data
docker volume ls
docker volume inspect db_data      # shows where it lives on the host
docker volume rm db_data
docker volume prune                # remove ALL unused volumes (be careful)

# Use it
docker run -d --name db -e MYSQL_ROOT_PASSWORD=secret \
  -v db_data:/var/lib/mysql mysql:8.4

# Prove persistence
docker rm -f db                    # container is gone
docker run -d --name db2 -e MYSQL_ROOT_PASSWORD=secret \
  -v db_data:/var/lib/mysql mysql:8.4    # data is still there
```

### 13.4 Bind mount for development

```bash
docker run --rm -p 8080:80 -v "$(pwd)/site":/usr/share/nginx/html:ro nginx
```

- Edit files in `./site` on your machine and the container sees changes at once.
- `:ro` makes it **read-only** inside the container.

### 13.5 The long form (`--mount`)

More readable and gives clearer errors:

```bash
docker run -d --mount type=volume,source=db_data,target=/var/lib/mysql mysql:8.4
docker run -d --mount type=bind,source="$(pwd)/site",target=/usr/share/nginx/html,readonly nginx
```

### 13.6 Important notes

- A bind mount **hides** whatever was at that path in the image.
- A new empty named volume gets **filled with the image's content** at that path the first time.
- Volumes can be shared between containers (for example, a backup container).
- **Backup idea:** run a temporary container that mounts the volume and creates a tar file.
- Data you cannot afford to lose should also have **real backups** (for example `mysqldump`). A volume alone is not a backup.
- If you do not name a volume (just `-v /var/lib/mysql`), Docker makes an **anonymous volume** with a random name. These are hard to track. Prefer named volumes.

---

## 14. Docker Networking

**Overview:** 3 parts from your course: default and custom networks, using a custom network for multi-container apps, and network drivers (bridge, host, none).

### 14.1 How containers get a network

Every container gets its own **network namespace**: its own IP address, ports, and routing table. Docker connects containers to **virtual networks**.

### 14.2 Default network vs custom network

When you install Docker, a network named **`bridge`** (default) exists. Containers without `--network` join it.

| Feature                                       | Default `bridge`    | **Custom bridge** (you create) |
| --------------------------------------------- | ------------------- | ------------------------------ |
| Containers reach each other by IP             | Yes                 | Yes                            |
| Containers reach each other **by name** (DNS) | **No**              | **Yes**                        |
| Isolation from other containers               | Weak (all share it) | Better (only members can talk) |
| Connect/disconnect while running              | No                  | Yes                            |

So for real apps, **always use a custom network** (Compose does this for you).

### 14.3 Using a custom network for multi-container apps

```bash
docker network create app-net

docker run -d --name db --network app-net \
  -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=appdb \
  mysql:8.4

docker run -d --name backend --network app-net -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://db:3306/appdb \
  -e SPRING_DATASOURCE_USERNAME=root \
  -e SPRING_DATASOURCE_PASSWORD=secret \
  my-app:1.0
```

```
              Host machine
   Browser --> localhost:8080
                    |
        +-----------v---------- app-net (custom bridge) ----------+
        |   [ backend ] ---- jdbc:mysql://db:3306 ---> [ db ]     |
        |   (published :8080)        DNS: "db"       (not published)|
        +----------------------------------------------------------+
```

Key points:

- `db` is resolved by Docker's **built-in DNS** to the container's IP.
- **`db` has no published port**, so it is not reachable from outside, only from containers in `app-net`. Good for security.
- Published ports (`-p`) are only needed for traffic **from the host or outside**, not for container-to-container traffic.

Useful commands:

```bash
docker network ls
docker network inspect app-net      # see connected containers and IPs
docker network connect app-net web  # attach a running container
docker network disconnect app-net web
docker network rm app-net
```

### 14.4 Network drivers

| Driver      | What it does                                                                    | When to use                                                                                    |
| ----------- | ------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------- |
| **bridge**  | Private virtual network on one host. Needs port publishing for outside access.  | Default and most common.                                                                       |
| **host**    | Container shares the **host's network** directly. No isolation, no `-p` needed. | Maximum network speed or special tools. Fully works on Linux only (limited on Docker Desktop). |
| **none**    | No network at all, only loopback.                                               | Fully isolated jobs, security-sensitive batch tasks.                                           |
| **overlay** | Connects containers across **multiple hosts** (Docker Swarm).                   | Multi-host clusters.                                                                           |
| **macvlan** | Gives the container its own MAC address on the physical network.                | Special cases (legacy apps).                                                                   |

```bash
docker run -d --network host nginx     # nginx on host port 80 directly
docker run -d --network none alpine sleep 1000
```

### 14.5 Common mistake to remember

> Inside a container, **`localhost` is the container itself**, not your computer and not other containers. To reach another container use its **name**. To reach your host machine from a container on Docker Desktop, use `host.docker.internal`.

---

# PART B: JAVA AND PRODUCTION TOPICS

These topics are **not in your course index**, but you need them for real Java projects and interviews.

---

## 15. Multi-Stage Builds

**Overview:** Use more than one `FROM` in one Dockerfile. Build in one stage, copy only the result into a small final stage.

### 15.1 The problem with the simple Dockerfile

- You must build the JAR on your machine first (not repeatable).
- If you instead install Maven and JDK in the image to build, the final image contains the **JDK, Maven, source code, and the Maven cache** (500 MB+), none of which are needed to **run** the app.

### 15.2 The solution

```dockerfile
# ---------- Stage 1: build ----------
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /build

# Cache dependencies (layer only changes when pom.xml changes)
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Build the app
COPY src ./src
RUN mvn -B clean package -DskipTests

# ---------- Stage 2: run ----------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create a non-root user
RUN addgroup --system app && adduser --system --ingroup app app

COPY --from=build /build/target/*.jar app.jar

USER app
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
```

```
 Stage "build"                          Stage "run" (final image)
 +---------------------------+          +---------------------------+
 | Maven + JDK + source +    |  COPY    | JRE only                  |
 | .m2 cache + target/*.jar  | -------> | + app.jar                 |
 +---------------------------+  --from  +---------------------------+
        (thrown away)                         (this is shipped)
```

### 15.3 Benefits

- **Smaller image** and **smaller attack surface** (no compiler, no Maven, no source code).
- **Repeatable build**: anyone with Docker can build, no local Java or Maven needed.
- **Layer cache** works well (dependencies are cached separately).

### 15.4 Notes

- `COPY --from=build` copies files from the earlier stage.
- If `target/` has more than one `.jar` (for example a `-plain.jar` from Gradle), the wildcard fails or picks the wrong file. Use the exact name, or set `finalName` in Maven (or `bootJar` config in Gradle).
- You can build only one stage: `docker build --target build -t test .`
- Gradle version: use a `gradle:jdk21` build stage and `./gradlew bootJar`.
- Optional extra: Spring Boot supports **layered JARs**, which split dependencies and app code into separate image layers for even better caching.

---

## 16. .dockerignore

**Overview:** A file that tells Docker which files **not** to send in the build context.

### 16.1 Why it matters

When you run `docker build .`, Docker sends the whole folder (the **build context**) to the daemon. Without `.dockerignore` it also sends `.git`, `target/`, `node_modules`, secrets, and IDE files. This makes builds **slow**, can **break the cache** (a changed log file invalidates `COPY . .`), and may **leak secrets** into the image.

### 16.2 Example for a Spring Boot backend

Create `.dockerignore` next to the Dockerfile:

```
target/
.git
.gitignore
.idea/
.vscode/
*.iml
*.log
.env
.env.*
Dockerfile
docker-compose.yml
compose.yaml
README.md
```

### 16.3 Example for a React frontend

```
node_modules
build
dist
.git
.env
*.log
```

Syntax is like `.gitignore`: one pattern per line, `#` for comments, `!pattern` to re-include something.

---

## 17. Health Checks and depends_on

**Overview:** "Container started" does **not** mean "app is ready". Health checks tell Docker when the app is truly ready.

### 17.1 The problem

`depends_on: [db]` alone only waits for the **db container to start**, not for MySQL to **accept connections**. MySQL takes several seconds to initialize. Your Spring Boot app starts, tries to connect, fails, and crashes.

### 17.2 Health check for the database (Compose)

```yaml
db:
  image: mysql:8.4
  healthcheck:
    test:
      [
        "CMD",
        "mysqladmin",
        "ping",
        "-h",
        "localhost",
        "-uroot",
        "-p${DB_ROOT_PASSWORD}",
      ]
    interval: 10s # how often to check
    timeout: 5s # how long one check may take
    retries: 10 # failures before "unhealthy"
    start_period: 30s # grace time at startup
```

Container health states: `starting`, `healthy`, `unhealthy`. Check with `docker ps` or `docker inspect`.

### 17.3 Wait for health

```yaml
backend:
  depends_on:
    db:
      condition: service_healthy
```

| Condition                        | Meaning                                                                |
| -------------------------------- | ---------------------------------------------------------------------- |
| `service_started`                | Container started (default)                                            |
| `service_healthy`                | Health check passes                                                    |
| `service_completed_successfully` | Container ran and exited with code 0 (good for migration or init jobs) |

### 17.4 Health check for your Spring Boot app

Add **Spring Boot Actuator** to your project:

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

It gives `/actuator/health`. Then in Compose:

```yaml
backend:
  healthcheck:
    test: ["CMD", "curl", "-f", "http://localhost:8080/actuator/health"]
    interval: 15s
    timeout: 5s
    retries: 5
    start_period: 40s
```

> `curl` must exist inside the image. Slim JRE images may not include it. Check with `docker exec`. If missing, install it in the Dockerfile or use another tool that exists in the image.

### 17.5 Important in real systems

`depends_on` helps only at **startup**. Databases can restart later. A production-quality app should also **retry** its DB connection (for example, Spring Retry, or connection pool settings in HikariCP) and not crash on the first failure.

---

## 18. Docker Security Basics

**Overview:** 6 simple habits that avoid the most common mistakes.

### 18.1 Do not run as root

By default, processes in a container run as **root** (inside the container). If an attacker escapes, that is more dangerous. Create and use a normal user:

```dockerfile
RUN addgroup --system app && adduser --system --ingroup app app
USER app
```

(For Alpine images the commands are `addgroup -S app && adduser -S app -G app`.)

### 18.2 Never bake secrets into images

Anything in the image can be read by anyone who has the image, even if you delete it in a later layer.

```dockerfile
ENV DB_PASSWORD=secret      # BAD: visible in "docker inspect" and "docker history"
COPY .env /app/.env         # BAD: secret file inside the image
```

Do this instead:

- Pass secrets **at run time**: `-e`, `--env-file`, Compose `environment:`.
- Keep `.env` out of Git (`.gitignore`) and out of the image (`.dockerignore`).
- In production use a **secret manager** or **Docker/Kubernetes secrets**, or cloud tools (AWS Secrets Manager, Vault).

### 18.3 Use small, trusted base images

- Use **official** images (`eclipse-temurin`, `mysql`, `nginx`).
- Prefer `-jre`, `-slim`, or `-alpine` variants (fewer packages = fewer vulnerabilities).
- **Pin versions** (`mysql:8.4`, not `mysql:latest`).
- Rebuild regularly to get security updates for base images.

### 18.4 Scan images

```bash
docker scout cves my-app:1.0       # Docker Scout (built into recent Docker)
trivy image my-app:1.0             # Trivy (popular open-source scanner)
```

### 18.5 Limit what containers can do

- Do **not** use `--privileged` unless you really must.
- Do **not** mount `/var/run/docker.sock` into a container (it gives control of the whole Docker host).
- Mount volumes **read-only** (`:ro`) when writing is not needed.
- Optional: `--read-only` root filesystem, `--cap-drop ALL`.

### 18.6 Limit exposure

- Publish only the ports you need.
- Do **not** publish the database port to the internet. Keep it internal on the Docker network.
- Bind to localhost when only local access is needed: `-p 127.0.0.1:3306:3306`.

---

## 19. Image Tagging and Versioning

**Overview:** A tag is a label on an image version. Good tags make deployments predictable and rollbacks easy.

### 19.1 Name format

```
registry/namespace/repository:tag
ghcr.io/jagir/my-app:1.2.0
yourusername/my-app:1.2.0       (Docker Hub, registry is implied)
my-app:1.0                      (local only)
```

If you do not give a tag, Docker uses **`latest`**.

### 19.2 `latest` is just a name

`latest` does **not** mean "newest". It is a normal tag that is used by default, and can point to anything. Problems when you use it in deployments:

- You cannot tell which version is running.
- Two servers can pull at different times and run different code.
- Rollback is unclear.

### 19.3 Good tagging strategy

| Tag               | Example    | Use                                     |
| ----------------- | ---------- | --------------------------------------- |
| Semantic version  | `1.4.2`    | Official releases                       |
| Minor/major alias | `1.4`, `1` | Convenience (moves with patch releases) |
| Git commit SHA    | `a1b2c3d`  | Exact traceability in CI/CD             |
| Environment label | `staging`  | Optional, moves over time               |

One image can have many tags:

```bash
docker build -t my-app:1.4.2 .
docker tag my-app:1.4.2 my-app:1.4
docker tag my-app:1.4.2 my-app:latest
docker push my-app:1.4.2
```

### 19.4 Digest (strongest pinning)

Every image has an immutable **digest** (`sha256:...`). You can pull exactly one content:

```bash
docker pull nginx@sha256:<digest>
```

Tags can be moved to a different image, but a digest cannot.

---

## 20. Logs, Resource Limits and Restart Policies

**Overview:** 3 parts for running containers reliably.

### 20.1 Logs

Docker captures everything the main process writes to **stdout and stderr**. So in containers, **log to the console**, not to files (Spring Boot does this by default).

```bash
docker logs my-app
docker logs -f my-app                # follow
docker logs --tail 100 my-app
docker logs --since 10m my-app
docker compose logs -f backend
```

The default `json-file` driver can grow forever. Add rotation (in Compose):

```yaml
backend:
  logging:
    driver: json-file
    options:
      max-size: "10m"
      max-file: "3"
```

In larger systems, logs are sent to a central system (ELK, Loki, CloudWatch).

### 20.2 Resource limits

Without limits, one container can use all host memory or CPU.

```bash
docker run -d --memory=512m --cpus=1.5 my-app:1.0
```

In Compose:

```yaml
backend:
  mem_limit: 512m
  cpus: 1.5
```

(Newer Compose also supports `deploy.resources.limits`.)

Points for Java:

- If the container goes over its memory limit, the kernel kills it (**exit code 137, OOMKilled**).
- Set heap as a percentage so it fits the limit: `-XX:MaxRAMPercentage=75.0`. The JVM also needs memory outside the heap (metaspace, threads, native), so do not give 100%.
- Watch usage with `docker stats`.

### 20.3 Restart policies

```bash
docker run -d --restart unless-stopped my-app:1.0
```

| Policy             | Behavior                                          |
| ------------------ | ------------------------------------------------- |
| `no`               | Never restart (default)                           |
| `on-failure[:max]` | Restart only if the exit code is not 0            |
| `always`           | Always restart, also after Docker daemon restarts |
| `unless-stopped`   | Like `always`, but not if you stopped it manually |

In Compose: `restart: unless-stopped`.

Note: a restart policy brings the container back after a crash or server reboot, but it does not fix a crash loop. Always read the logs.

---

# PART C: PRACTICE AND CHEAT SHEET

---

## Practice Plan with Your Own Project

Take one project you already know (for example your `collab_desk` backend) and do these steps in order. Each step uses topics from above.

| Step | Task                                                                                                                               | Topics used |
| ---- | ---------------------------------------------------------------------------------------------------------------------------------- | ----------- |
| 1    | Install Docker, run `hello-world`, run `nginx` with a port mapping                                                                 | 3, 4, 6     |
| 2    | Run MySQL in a container with a named volume, connect using a client, delete the container, start again, check data is still there | 4, 6, 13    |
| 3    | Build your Spring Boot JAR using the Maven container (no local Maven)                                                              | 8           |
| 4    | Write a simple Dockerfile, build and run your backend                                                                              | 9           |
| 5    | Put backend and MySQL on a custom network, connect using the `db` name                                                             | 14          |
| 6    | Convert everything into one `compose.yaml` with `.env`                                                                             | 10          |
| 7    | Rewrite the Dockerfile as a multi-stage build, add `.dockerignore`, compare image sizes (`docker images`)                          | 12, 15, 16  |
| 8    | Add a DB health check and `depends_on: service_healthy`                                                                            | 17          |
| 9    | Add a non-root user and move secrets to `.env`                                                                                     | 18          |
| 10   | Add the React frontend as a third service                                                                                          | 10          |
| 11   | Tag with a version and push to Docker Hub                                                                                          | 11, 19      |
| 12   | Add log rotation, memory limit, and `restart: unless-stopped`                                                                      | 20          |

**Resume line you can earn from this:**
"Containerized a Spring Boot + MySQL + React application using multi-stage Docker builds and Docker Compose with health checks, non-root containers, and versioned images on Docker Hub."

**What comes next after Docker:** Linux and Nginx basics, GitHub Actions (CI/CD to build and push images), deploying to a VPS or AWS EC2 with HTTPS, then Kubernetes basics.

---

## Command Cheat Sheet

```bash
# ---------- Images ----------
docker pull <image:tag>
docker images
docker rmi <image>
docker build -t <name:tag> .
docker tag <src> <dest>
docker push <name:tag>
docker history <image>

# ---------- Containers ----------
docker run -d --name <n> -p H:C -e K=V -v vol:/path --network <net> <image>
docker ps / docker ps -a
docker stop|start|restart <n>
docker rm -f <n>
docker logs -f --tail 100 <n>
docker exec -it <n> sh
docker inspect <n>
docker stats
docker cp <n>:/path ./local

# ---------- Volumes and Networks ----------
docker volume create|ls|inspect|rm <v>
docker network create|ls|inspect|rm <net>
docker network connect|disconnect <net> <container>

# ---------- Compose ----------
docker compose up -d --build
docker compose ps
docker compose logs -f <service>
docker compose exec <service> sh
docker compose down          # keep volumes
docker compose down -v       # delete volumes too

# ---------- Cleanup ----------
docker system df
docker container prune
docker image prune -a
docker volume prune
docker system prune
```

---

## Quick Recap: Things to Never Forget

1. A **container lives as long as its main process**.
2. **Image = read-only template, container = running instance.** Data in a container is **temporary** unless you use a volume.
3. `EXPOSE` does not publish a port. `-p host:container` does.
4. Inside a container, `localhost` means **itself**. Use service or container **names** on a custom network.
5. Put rarely-changing steps **first** in the Dockerfile for good caching.
6. Use **multi-stage builds**, **non-root user**, and **.dockerignore** for every Java project.
7. `depends_on` + **health checks** solve the "DB not ready" crash.
8. No secrets in images. No `latest` in production.
