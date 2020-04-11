
## Domain Challenge - ViewModel + LiveData + Coroutines + Navigation Component

### MVVM

The app was built using the MVVM (Model-View-View Model) design pattern.

The Model layer represents the data and state of the App, it is represented by classes using the Repository pattern. The repository is responsible for fetching the data from the API and convert it to domain models.

The View layer is represented by Fragments that observe and react to states and actions exposed by the ViewModel.

The ViewModel layer is responsible for interacting with the model and preparing observable data needed by the view. It also provides hooks for the view to pass events to the model. An important thing is that the ViewModel is not tied to the view.
This layer was implemented using Jetpack ViewModel and LiveData to expose the observers to the View layer.

### Single Activity App

The app is very simple in terms of navigation and has only one screen, it was built using he Navigation Architecture Component to simplified the implementation of the Single Activity concept. Basically, the App has the MainActivty responsible for holding the NavHostFragment and one Fragment that displays the Property list.

### Courotines

Coroutines were introduced to handle asynchronous calls. They are mainly used to handle the Network calls and they work really well with the JetPack ViewModel and Retrofit.

### Network layer

Retrofit was used to build the network layer because it is a mature library and simple to implement, also is the most used network library across the Android community.
To de/serialize JSON objects was used Moshi. Moshi has adapters and converters that work well with Refrofit and Courotines and also has good performance.

### Unit Tests

Unit tests are covering only the ViewModel and Repository

The unit tests are using two additional test rules to provide the proper executors and dispatchers to the ViewModel (InstantTaskExecutorRule) and Coroutines (CoroutinesTestRule).

## Next-Steps

- Include Dagger or Koin library to do dependency injection and handle dependencies scopes
- Create UI tests using espresso.
- Integrate lint and code style tools to improve the code quality and style.
- Integrate some CI tool like Circle CI to automatically run Unit tests and code quality steps every time the code is pushed to the repository.
