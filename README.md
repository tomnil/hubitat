# Hubitat

This page contains the documentation for the [https://hubitat.com/](https://hubitat.com/) scripts found here.

## UserTimer1

This piece of code creates a timer for Hubitat. 

:!: Because of limitations in the Rule Machine v4, it's not possible to create a proper timer. Instead, the timer is represented as a switch (with on/off capabilities) and an attribute (timeElapsed).

### Setup

1. Go to Drivers Code => New Driver => Import => https://github.com/tomnil/hubitat/blob/main/UserTimer1.groovy => Save.
2. Create a virtual Switch (yes, a switch) of the type "UserTimer1". Name it as you like.

### Add to Rule Machine 4

#### Triggering

* Add a new rule
* Use any trigger you'd like (Example "Hue Motion Sensor")
* When triggered, execute Switch => On (timer starts)

#### Checking the value

* Add a new rule
* Select Trigger Events
  * Select Capability for new Trigger event (dropdown)
  * Custom Attibute
  * Select Device
  * Choose the Switch
  * Select Attribute (dropdown)
  * Choose "timeElapsed"
  * Set Comparisation (dropdown) to ">"
  * Set numeric value to 60000 (60 seconds)
  * Done with this trigger event.
* Actions to run: execute Switch => Off.
