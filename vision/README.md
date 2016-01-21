# TexasTorque2016 Vision Module

This directory contains all vision scripts that may or may not be used during competition.

## Editing (Visual Studio)
- Install OpenCV
- Create a new Visual Studio project (Win32 Command Line)
- Import vision script
- Open project properties and navigate to Linker -> General
- Add the <code>lib/</code> folder to the Additional Library Directories
- Navigate to Linker -> Input
- Add <code>opencv_world310d.lib</code> to Additional Dependencies
- Add the <code>include/</code> folder to the Additional Include Directories
- Ctrl+Shift+B to build, Ctrl+F5 to run

## Editing (Linux)
- Edit script
- Run <code>gcc "filename.cpp" -o "exeName" -lopencv_core</code>
- <code>./"exeName"</code> to run

## Notes
<code>opencv_world310d.lib</code> MUST be used in order to <code>findContours(...)</code> to work during runtime. The debug library will also give more detailed output relating to <code>VideoCapture</code> and processing.