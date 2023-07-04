# UberAlles
`UberAlles` the german word for `above all`

is an experimental library for creating on top of everything view
in android with jetpack compose

## Download 
## Usage
full example of usage [here](./app/)

### Add Permission

add code below to `AndroidManifest.xml`
```
    <uses-permission android:name="android.permission.ACTION_MANAGE_OVERLAY_PERMISSION" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />

```

## Credit
original code is from https://github.com/tberghuis/FloatingCountdownTimer
which is then heavily refactored to be used as a library
## Limitation
### - `DropDownMenu` not shown
### - [Alert Dialog](https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#AlertDialog(kotlin.Function0,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Function0,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.window.DialogProperties)) 

will crash, current workaround is to use functions from this library to show Dialog
### - `context`'s type is `Service` 

can't be cast into `Activity`,  

problem is when you want to apply 
a theme to a composable function
in overlay view, using `ui.theme.Theme`

where by default in your compose project you should have something like

```
@Composable
fun ProjectNameTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

current workaround is to just comment/remove the code where it is casting `context`
into `Activity`
```
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
```
then after that you can apply theme to your overlay view
## Useful Reference

### Using Jetpackcompose as overlay
https://www.jetpackcompose.app/snippets/OverlayService
https://gist.github.com/handstandsam/6ecff2f39da72c0b38c07aa80bbb5a2f#file-overlayservice-kt
https://github.com/tberghuis/FloatingCountdownTimer

### Requesting Overlay Permission
https://stackoverflow.com/questions/40437721/how-to-give-screen-overlay-permission-on-my-activity
https://stackoverflow.com/questions/52382710/permission-denial-startforeground-requires-android-permission-foreground-servic



