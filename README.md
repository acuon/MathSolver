# MathSolver App

<!-- ![MathSolver App by Wolfram Alpha](https://products.wolframalpha.com/_next/static/images/cloud-icon_2GWj-tLX.png) -->

MathSolver is a mobile application that allows users to input mathematical expressions and get instant solutions using the Wolfram Alpha API. It supports both single expressions and multiple expressions entered on separate lines.

## APK File

You can download the APK file of the MathSolver app from the following link:

[Download MathSolver APK](https://github.com/acuon/MathSolver/blob/main/apk/app-debug.apk)


## Tech Stack

MathSolver is built using the following technologies and libraries:

- [Kotlin](https://kotlinlang.org/): The project is written in Kotlin, a modern and expressive programming language for Android development.

- [Jetpack Compose](https://developer.android.com/jetpack/compose): Jetpack Compose is used for building the user interface (UI) of the application, enabling a more declarative and efficient way to create Android UIs.

- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel): ViewModel is used to manage UI-related data and handle the communication between the UI and the underlying data sources.

- [Hilt](https://dagger.dev/hilt/): Hilt is used for dependency injection, making it easier to manage and provide dependencies throughout the app.

- [Room Database](https://developer.android.com/topic/libraries/architecture/room): Room is used for local data storage and managing the app's database.

- [Retrofit](https://square.github.io/retrofit/): Retrofit is used for making network requests to the Wolfram Alpha API.

- [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html): Coroutines are used to handle asynchronous operations, such as making network requests and database interactions, in a structured and efficient manner.

- [Material Components for Android](https://material.io/develop/android/docs/getting-started): Material Components are used for designing a visually appealing and consistent user interface.

This tech stack empowers MathSolver to provide a smooth and intuitive experience for users while efficiently handling math expression evaluations and history management.


## Features

- Evaluate single mathematical expressions.
- Evaluate multiple mathematical expressions simultaneously.
- View a history of evaluated expressions.
- Copy results to the clipboard for easy sharing.

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You need to log in to the [Wolfram Alpha website](https://www.wolframalpha.com/) and obtain the Base URL and App ID from the Wolfram Alpha Developers section.

## Installation

To install and run MathSolver, follow these steps:

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/your-username/MathSolver.git

2. Open the project in Android Studio.

3. Create a local.properties file in the root directory of your project if it doesn't already exist.

4. Add your Base URL and App ID obtained from the Wolfram Alpha Developers section to local.properties:
    ```bash
    BASE_URL=#Your Base Url
    APP_ID=#Your app id

5. Build and run the application on your Android device or emulator.


## Usage
1. Launch the MathSolver app.

2. Choose between evaluating a single expression or multiple expressions using the provided radio buttons.

3. Enter your mathematical expression(s) in the input field. If evaluating multiple expressions, enter each expression on a new line.

4. Click the "Evaluate" button.

5. Wait for the app to fetch and display the results.

6. View the results on the screen, and the history of evaluated expressions.

7. Copy the results to the clipboard by clicking on the result.

## Example
Here's an example of how to use the MathSolver app:

1. Choose "Single Expression."

2. Enter the expression "2+2."

3. Click "Evaluate."

4. The result "4" will be displayed on the screen.

5. To evaluate multiple expressions, choose "Multiple Expressions," and enter expressions like:
    ```bash
    2*4*4
    5/(7-5)
    sqrt(5^2-4^2)
    sqrt(-3^2-4^2)

6. Click "Evaluate," and the results for each expression will be displayed.

## Acknowledgments

Wolfram Alpha for providing the powerful mathematical computation engine.

## Contact
If you have any questions or suggestions, please feel free to contact me at rohitshar8600@gmail.com.

Happy Math Solving!



