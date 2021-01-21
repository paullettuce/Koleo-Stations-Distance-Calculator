package pl.paullettuce.android_astarium_interview_app.presentation.bottom_sheet

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

class BottomSheet(view: View, draggable: Boolean = false) {
    private val behavior = BottomSheetBehavior.from(view)

    init {
        behavior.isDraggable = draggable
        hide()
    }

    fun toggle() {
        if (isHidden()) open()
        else hide()
    }

    fun open() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hide() {
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun isHidden() = behavior.state == BottomSheetBehavior.STATE_HIDDEN
    fun isOpened() = behavior.state == BottomSheetBehavior.STATE_EXPANDED
}