# Password Manager in Java

I made this project in 2019 during my studies.

I needed to create a password manager in Java with a GUI.

A file is created who contains all the informations (application password + accounts).

This project isn't terminated beacause we don't crypt the file who contains all passwords.

## Installation

You need to have installed a jdk to build and lauch this project

### Windows

To launch the project, I use [chocolatey](https://chocolatey.org/)

To install you need to launch this command on your PowerShell in admin mode :

```cmd
chocolatey install openjdk8
```

To create binaries, your need to launch this command :

```powershell
javac Compte.java DeleteButtonEditor.java Fenetre_accueil.java Fenetre_ajouter.java Fenetre_connexion.java Fenetre_creation.java Fenetre_rechercher.java Model.java Principal.java
```

### Linux

To install you need to launch this command on your terminal :

```bash
sudo apt install openjdk8
```

To create binaries, your need to launch this command :

```bash
javac *.java
```

## Usage

You need to open a terminal and go to project folder :

```
java Principal
```
