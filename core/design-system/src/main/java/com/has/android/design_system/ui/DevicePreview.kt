package com.has.android.design_system.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480")
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480")
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480")
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480")
@Preview(name = "phone", device = "spec:shape=Normal,width=360,height=640,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "landscape", device = "spec:shape=Normal,width=640,height=360,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "foldable", device = "spec:shape=Normal,width=673,height=841,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "tablet", device = "spec:shape=Normal,width=1280,height=800,unit=dp,dpi=480", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class DevicePreviews

@Preview(name = "Light Mode Preview", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark Mode Preview", uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class LightAndDarkModePreviews
