# Weather test app (Boris Rayskiy)

### This coding challenge demonstrates:
* Multi-module Android application 
    - ::app (startup, config, etc. module)
    - ::weather (feature module) 
    - ::core (common code module)
* Flexible and easy scalable application using:
    - mvvm architecture
    - flow architecture  
    - kotlin coroutines
    - convertible to mvp+coroutines, mvvm+rxJava, mvp+coroutines paradigm
    - dagger dependencies injection
    - 100% kotlin
* Covered by unit tests
    - espresso
    - mockk
    - coroutine test
* Uses location API
    - firebase

### Satisfied requirements:
* Uses [OpenWeather's REST API](https://api.openweathermap.org) to build an Android application that displays the current weather in New York.
* Retrieves the weather information [API](https://openweathermap.org).
* Shows the following set of data on the weather screen screen:
  - Location (e.g. New York)
  - Temperature
  - Feels like temperature
  - Humidity
  - Wind speed/direction
  - Atmospheric pressure
