public static String version()      {  return "v1.0"  }

metadata {
    definition (name: "UserTimer1", namespace: "triplestep", author: "Tomas Nilsson") {
        capability "Switch" // https://docs.hubitat.com/index.php?title=Driver_Capability_List#switch
        attribute  "timeElapsed", "number"
        attribute  "running", "number"
        attribute  "error", "string"
    }

    preferences {
            input name: "iResetOnRestart", type: "bool", title: "Reset timer if (re)started while running", defaultValue: true
            input name: "iDebugLog", type: "bool", title: "Enable debug logging", defaultValue: false
    }
}


// *****************************************************
// * start

def on() {

    // Don't execute if already running
    if (device.currentValue("running") == 1){
        if (iResetOnRestart) {
            if (iDebugLog)
                log.info("UserTimer1: start() event for an already running timer => setting timer to 0");
            state.startTime = now()
        }
        return;
    }

    // Initialize variables
    sendEvent(name: "error", value: "-")
    state.doStop = 0;                        // Set to 1 to gracefully stop the timer
    state.startTime = now()
    sendEvent(name: "running", value: 1)

    if (iDebugLog)
        log.info("UserTimer1: Starting timer..");

    // Ask the UI to update
    runInMillis(50, updateUI)
}

// *****************************************************
// * stop() asks timer to stop
// *****************************************************

def off() {

    if (device.currentValue("running") == 0){
        sendEvent(name: "error", value: "already stopped")
        log.info("UserTimer1: already stopped");
        return;
    }

    if (iDebugLog)
        log.info("UserTimer1: Stopping timer..");

    sendEvent(name: "running", value: 0)
    state.doStop = 1
}

// *****************************************************
// * UpdateUI - Calculates time elapsed and informs UI
// *****************************************************

def updateUI() {

    // Calculate timeElapsed and inform the UI
    state.timeElapsed = now() - state.startTime

    sendEvent(name: "timeElapsed",
              value: state.timeElapsed,
              descriptionText: "${device.displayName} timeElapsed: ${state.timeElapsed} s",
              unit: "s")

    // Stop the timer?
    if (state.doStop==1 || device.currentValue("running") == 0) {

        if (iDebugLog)
            log.info("UserTimer1: Timer stopped.");

        return;
    }
    

    // Keep on updating
    runInMillis(200, updateUI)
}




