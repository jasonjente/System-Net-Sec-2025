## Dev Environment Setup

--- 
### WSL

- https://learn.microsoft.com/en-us/windows/wsl/install
- https://learn.microsoft.com/en-us/windows/wsl/setup/environment

### Add SSH keys to github

- https://docs.github.com/en/authentication/connecting-to-github-with-ssh/generating-a-new-ssh-key-and-adding-it-to-the-ssh-agent

--- 

### WSL(2)
- install sdkman (software that manages JDK versions.
- https://sdkman.io/install/
```
# Install sdk man, if you don't have installed zip/unzip download them via apt prior to theinstallation of sdk man.

# sudo apt install zip unzip 
curl -s "https://get.sdkman.io" | bash
```
---
### Setup of sdk man (see https://sdkman.io/install/)
SDKMan is a tool that manages java versions and can allow the setup of the jvm and jdk of our choice. 
Very user-friendly and easy to set up and run projects.
```
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk version

# example output:
# SDKMAN!
# script: 5.19.0
# native: 0.5.0  
```

```bash
# Check available java distributions
sdk list java
# Install the java choice based on version. Oracle is fine.
sdk install java 21.0.6-oracle
# Install maven 3.9.9 (3.6.x + should suffice)
sdk install maven  3.9.9
```

### IDE - Intellij Community edition
- download & install Intellij from: [https://www.jetbrains.com/idea/download/download-thanks.html?platform=windows&code=IIC](Link)

### Project setup
```bash
git clone git@github.com:jasonjente/System-Net-Sec-2025.git

```

### Docker
The recommended option for docker is to use docker desktop on windows and then enable wsl integration with docker.
- https://www.docker.com/products/docker-desktop/
- https://learn.microsoft.com/en-us/windows/wsl/tutorials/wsl-containers


