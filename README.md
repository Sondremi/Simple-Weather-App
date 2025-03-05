# Simple-Weather-App
A user-friendly app that allows you to check the current temperature in any city.

* Enter the name of a city, and the app will retrieve the corresponding latitude and longitude using the Google Maps Geocoding API.
* The app then uses the MET Weather API to fetch and display the current temperature for that location.
* You will see the selected location in a map from MapBox

# TODO
* Create a api key for Google Maps Geocoding API.
* Add MAPS_API_KEY=YOUR_API_KEY in local.properties to enable location lookup.
* Create a api key for MapBox
* Create a new file mapbox_access_token.xml in res/values
* Add this in mapbox_access_token.xml:
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools">
    <string name="mapbox_access_token" translatable="false" tools:ignore="UnusedResources">YOUR_MAPBOX_API_KEY</string>
</resources>
