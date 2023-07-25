
# Emcryption Login

Welcome to the Android project developed with Kotlin and Android Jetpack, which aims to demonstrate a practical example of using encryption and decryption to ensure the security of login credentials in a scenario application.
## Detailed description

This project has been created with the purpose of showing an __example case of a secure solution to store and manage user login credentials__ in an application. In scenarios where users need to quickly remember and access confidential information such as emails, card numbers or any other sensitive data, this application provides a reliable and protected solution.


## Features

The app allows users to store their email for faster and more convenient login on future occasions. If the user wants to enable this option, they will simply select "Remember mail" in the login interface. Email will be securely encrypted before being stored on the device using Proto DataStore.

When the user launches the application later, they will only need to enter their password, and the application will use the decryption mechanism to retrieve the previously stored email and automatically complete the login process.
## Características Principales

- Encryption and Decryption:     
    The application implements a strong encryption mechanism to protect the user's sensitive data before storing it on the device and decrypts it every time the user needs to access it.

- Use of Preferences DataStore:     
    To manage and store data securely, Preferences DataStore has been used, specifically using Proto DataStore, one of the safest and most recommended Android Jetpack options for this purpose.


# Architecture

The project architecture is based on MVVM (Model-View-ViewModel), with the goal of keeping logic away from presentation layers and ensuring a clear separation of responsibilities. It is prioritized that most of the presentation related tasks are done in the repositories, and other more specific tasks are handled in the use cases. This is achieved by following SOLID principles, where each main class focuses on a specific logic of a certain component.

The application uses coroutines to perform asynchronous tasks efficiently, avoiding blocking the main thread and keeping the user interface fluid.

The project adheres to SOLID principles, ensuring that higher-level implementations do not depend on lower-level ones, thus improving code scalability and maintainability.

For UI and reactivity management, the use of Flows and StateFlow is prioritized, rather than LiveData, leveraging the capabilities of coroutines to deliver a fluid and reactive user experience.
## Importance of Security

Security is a fundamental concern in any application that handles sensitive data. The use of encryption and decryption ensures that user information remains secure even if the device falls into the wrong hands.