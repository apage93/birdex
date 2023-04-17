import sys
from geopy.geocoders import Nominatim


def get_location_from_gps_points():
    geolocator = Nominatim(user_agent="my_email@myserver.com")
    point = sys.argv[2] + "," + sys.argv[1]
    location = geolocator.reverse(point)
    print(location)


get_location_from_gps_points()
