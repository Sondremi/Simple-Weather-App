# Simple-Weather-App
A user-friendly app that allows you to check the current temperature in any city.

## Features
✅ Enter the name of a city, and the app will retrieve the corresponding latitude and longitude using the Google Maps Geocoding API.  
✅ The app then uses the MET Weather API to fetch and display the current temperature for that location.  
✅ You will see the selected location on a map using MapBox.  

## Setup Instructions

### 1. Configure Google Maps Geocoding API  
- Create an API key for Google Maps Geocoding API.  
- Add the following line to your `local.properties` file:

  ```properties
  MAPS_API_KEY=YOUR_API_KEY
  ```

### 2. Configure MapBox API  
- Create an API key for MapBox.  
- Create a new file `mapbox_access_token.xml` in `res/values/`.  
- Add the following content to `mapbox_access_token.xml`:

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <resources xmlns:tools="http://schemas.android.com/tools">
      <string name="mapbox_access_token" translatable="false" tools:ignore="UnusedResources">
          YOUR_MAPBOX_API_KEY
      </string>
  </resources>
  ```
