
# Photo Album App! 



## Screens
### 1. Profile Screen
- The profile screen displays the user's name and address at the top.
- Below the user information, a list of all user albums is presented.
- User albums are retrieved by making a request to the albums endpoint, passing the user id as a parameter.
### 2. Album Details Screen

- This screen appears when you tap on a specific album from the profile screen.
- It displays an Instagram-like grid of images from the selected album.
- To retrieve images, the app makes a request to the photos endpoint, passing the album id as a parameter.
- The screen includes a search bar to filter images within the album by title. As you type, the grid dynamically updates to show only relevant images.
### 3. Zooming and Sharing Screen
- Tap on any image in the Album Details Screen to open it in a separate image viewer.
- The image viewer supports zooming in and out for a closer look.
- Share the image with friends or on social media directly from the image viewer.
## Software Stack
### Kotlin: 
The programming language used for developing the Android app.
### MVVM:
 The app follows the Model-View-ViewModel architectural pattern for a clean and modular codebase.
### Repository Pattern: 
Utilized to manage data access and abstract the origin of the data.
### Navigation Component:
 Responsible for handling navigation between different screens.
### Retrofit 2:
A powerful HTTP client for making API requests in a type-safe manner.
### Coroutines: 
Used for managing asynchronous programming tasks, ensuring smooth and responsive user interactions.
### Flows:
 Leveraged for reactive programming, enhancing data flow within the app.
#### View Binding:
  Employed for efficient way of interacting with views.

