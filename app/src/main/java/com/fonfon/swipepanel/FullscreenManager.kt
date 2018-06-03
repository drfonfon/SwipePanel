package com.fonfon.swipepanel

import android.view.View

class FullscreenManager {

  var fullscreen: Boolean? = true
  private var fullscreenView: View? = null
  var listener = { b: Boolean -> }

  fun setFullscreenView(fullscreenView: View) {
    this.fullscreenView = fullscreenView
  }

  fun open() {
    fullscreenView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
        or View.SYSTEM_UI_FLAG_FULLSCREEN
        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    listener(true)
  }

  fun close() {
    fullscreenView!!.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
    listener(false)
  }

  fun toggle() {
    if (fullscreen!!) {
      fullscreen = false
      close()
    } else {
      fullscreen = true
      open()
    }
  }


}
