# Project Summary:

**Project Title**: LocusReminder

An android application based on reminder system. Users set the reminder to perform tasks by typing a reminder note and selecting a location. “Locus Reminder” automatically notifies when the user is nearby to the location, with a trigger notification and display title message pre-entered in reminder notes.

# Contributors:

Ambadipudi Akhil Teja

> B00825307 \| 902-452-7965 \| ak974116\@dal.ca

Shuo Yang

> B00606093 \| 902-233-5680 \| sh805190\@dal.ca

Jayanthi Ajith

> B00825322 \| 902-448-7956 \| aj788769\@dal.ca

Amirul Sunesara

> B00813456 \| 902-401-3097 \| amirul.sunesara\@dal.ca

Lakshmi Narayana Peniketi

> B00819840 \| 902-399-8905 \| lk720991\@dal.ca

# Installation Notes: 

Checkout master branch and after checkout you will find app-debug.apk on root
directory. Copy this file on phone and install.

# Code Examples: 

**Problem 1: We needed a function to calculate distance from current location to
a specific location inserted with reminders**

Here we are using distanceTo( ) function of Location class which is making
conversion more simpler where we don’t need to consider unit conversion, as this
function provides default conversion in meters.

// Following method is implementing logic for distance calculation and
notifications   
if(rd.getIsDeleted().equals("0")){

Location location2 = new Location("");

location2.setLatitude(Double.parseDouble(rd.getLatitude()));

location2.setLongitude(Double.parseDouble(rd.getLongitude()));

float distance = location1.distanceTo(location2);

SharedPreferences sharedPref = PreferenceManager.getDefaultSharedP references(getApplicationContext());

String range = sharedPref.getString("lstRange","100");

if(distance \<= Integer.parseInt(range) && !isNotifationDispatched(rd)){

createNotification(i,rd);

dispatchNotification(rd);   }}

// Source: Android Developers [6]

**Problem 2: We need a logic for saving default of settings screen when
application is instantiated first time**

During the search we found that a function can be used to set default values for
settings screen. Therefore, we don’t need to write explicit logic to save
default values.

// Following line of code solve this problem

PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

// Source: Program Creek [7]

# Feature Section:

**Database:**

SQLite Database is used in our project to manage the reminder. A build-in
Android Debug bridge tool which is a part of Android SDK is used to test the
basic CRUD functions. The database is built locally, and it stores the
latitudes, longitudes, and the title of each note. When users enter the
database, a log will record all operations they do. One trigger is created to
show a notification on screen to ensure users can validate they have done what
they want in SQLite database.

**Google map:**

The users will specify the address of the location when a new reminder is
created. The location does not contain the GPS Coordinates. The address will be
transferred to coordinates after reminders saved that to the database.
Geo-coding is one kind of integrating API to help obtain GPS coordinates.
Geo-fencing can trigger some actions whenever users input locations. It is like
a perimeter because it can help choose locations. GPS technology is usually
working together with Geo-fencing technology to ensure accuracy. Some other
techniques like Wi-Fi, cellular data transfer can also be bound with
Geo-fencing. Geo-fencing provides permission if the applications want to
retrieve the user locations.

**Reminder Notification:**

Visibility is important in UI design. Users’ needs to get appropriate feedback
at appropriate time. The reminder notification function to provide visible
service to users whenever the service is starting. The vibration will be bound
with the clear alert. The notification and vibration have been added to the
system.

## Device features used:

- GPS

- Wi-Fi

- Cellular Data

- IN-BUILT Haptic Audio Feedback

# Final Project Status:

The minimum and the expected functionalities are completed. As of now
application is ready to use with the expected functionality.

**Minimum functionality:** User should be able to write a note and need to add a
location without any errors.

**Expected functionality:** Application should trigger a notification whenever
the user reaches the specified location.

**Bonus Functionality:** Notes can even be created by integrating features such
as speech to text recognition.

# Sources 

1.  Settings  \|  Android Developers. (n.d.). Retrieved
    from https://developer.android.com/guide/topics/ui/settings

2.  Vujovic, F. (2016, June 17). Android - Get GPS location via service.
    Retrieved from https://www.youtube.com/watch?v=lvcGh2ZgHeA

3.  Fragments  \|  Android Developers. (n.d.). Retrieved
    from https://developer.android.com/guide/components/fragments

4.  Menus  \|  Android Developers. (n.d.). Retrieved
    from https://developer.android.com/guide/topics/ui/menus

5.  Tutorials, J. A. (2016, June 20). Android Studio How to use scroll view
    (ScrollView). Retrieved from https://www.youtube.com/watch?v=_IF1vJF7Xb8

6.  Location \| Android Developers. (n.d.). Retrieved
    from https://developer.android.com/reference/android/location/Location

7.  Java Code Examples
    for android.support.v7.preference.PreferenceManager.setDefaultValues().
    (n.d.). Retrieved
    from https://www.programcreek.com/java-api-examples/?class=android.support.v7.preference.PreferenceManager&method=setDefaultValues
